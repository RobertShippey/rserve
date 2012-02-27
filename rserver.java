import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class rserver extends Thread 
{
    private static ServerSocket server = null;
    
    private Socket client = null;
    private PrintWriter outStream = null;
    private header request = null;
    private header response = null;
    private final String eol = "\r\n";
    
    public static void main (String args[]) throws Exception
    {
        int port = 0;
        try { port = Integer.parseInt(args[0]); }
        catch(Exception e){ 
            System.err.println("You did not set a valid port, the first available will be used.");
        }
        try {
            server = new ServerSocket(port);
            System.out.println("Server is running on " 
                               + server.getInetAddress().getHostName() 
                               + ":" 
                               + String.valueOf(server.getLocalPort()));
        }
        catch(IOException e) {
            System.err.println("Error when opening socket");
            System.err.println(e.getMessage());
            return; 
        }
        catch(SecurityException e) {
            System.err.println("A security error occured");
            System.err.println(e.getMessage());
            return;
        }
        int x =0;
        while(true)
        {
            System.out.println(++x);
            System.out.println("Waiting for client...");
            try{
                rserver rservThread = new rserver(server.accept()); 
                System.out.println("Got one!");
            }
            catch(IOException e) {
                System.err.println("Error when opening socket");
                System.err.println(e.getMessage());
                continue; 
            }
            catch(SecurityException e) {
                System.err.println("A security error occured");
                System.err.println(e.getMessage());
                continue;
            }
            
        }
    }
    
    
    
    rserver(Socket c) {
        client = c;
        start();
    }

    @Override
    public void run() {

        request = new header();
        response = new header ();
        try {
            BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
            byte[] clientBytes = new byte[client.getReceiveBufferSize()];
            clientInput.read(clientBytes, 0, clientBytes.length);
            request.parseRequest(clientBytes);
        } catch (IOException e) {
            System.err.append("Could not understand header");
            return;
        }


        byte[] fileBytes = null;
        File page = new File(System.getProperty("user.dir") + request.path);
        if (page.isDirectory())
        {
            request.path += "/index.html";
            page = new File(request.path);
        }
        if (page.isFile())
        {
            try
            {
                BufferedInputStream inputStream1 = new BufferedInputStream(new FileInputStream(page));
                fileBytes = new byte[(int) page.length()];
                inputStream1.read(fileBytes, 0, fileBytes.length);

                
                Date mod = new Date(page.lastModified());
            SimpleDateFormat formatter = new SimpleDateFormat("E, d M yyyy H:m:s z");
                response.lastModified = formatter.format(mod); 
                response.status = "200 OK";
                response.contentLength = fileBytes.length;
            }
            catch (IOException e)
            {
            }
        } 
        else
        {
            File fnf = new File(System.getProperty("user.dir") + "/404.html");
            response.status = "400 Not Found";
            if (fnf.isFile())
            {
                try
                {
                    BufferedInputStream inputStream2 = new BufferedInputStream(new FileInputStream(fnf));
                    fileBytes = new byte[(int) fnf.length()];
                    inputStream2.read(fileBytes, 0, fileBytes.length);
                    
                    response.contentLength = fileBytes.length;
                }
                catch (IOException e)
                {
                }
                
            }
            else {
                fileBytes = null;
            }
        }
 
        System.out.println("Sending...");
        try{
            OutputStream clientOutput = client.getOutputStream();

            byte[] header = response.getResponse();
            clientOutput.write(header, 0, header.length);
            if (fileBytes != null) {
                clientOutput.write(fileBytes, 0, fileBytes.length);
            }

            clientOutput.flush();
            client.close();
        } catch (IOException e) {}
            System.out.println("Done!");
        }
    
}