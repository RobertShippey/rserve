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
        ServerSocket server;
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
    
    public void run()  {
        try{
            request = new header();
            request.parseRequest(client);
            //String clientHeader = new String(getHeader(client));
            
            //System.out.println(clientHeader);
            
            System.out.println(request.path);
            //File myFile = new File ("index.html");
            byte [] fileByes;
            BufferedInputStream inputStream;
            File page;
            
            try{
                page = new File (request.path);
                inputStream = new BufferedInputStream(new FileInputStream(page));
                fileByes  = new byte [(int)page.length()];
            } catch (IOException e)
            {
                fileByes = null;
                inputStream = null;
                page = null;
            }
            
            
            inputStream.read(fileByes,0,fileByes.length);
            
            OutputStream clientOutput = client.getOutputStream();
            System.out.println("Sending...");
            
            Date mod = new Date (page.lastModified());
            SimpleDateFormat formatter = new SimpleDateFormat("E, d M yyyy H:m:s z");
            String lastModified = new String(formatter.format(mod));
            
            String strHeader = new String("HTTP/1.0 " + request.status + eol +
                                          "Content-Type: text/html" + eol +
                                          "Last-Modified: " + lastModified + eol +
                                          "Content-Length: " + fileByes.length + eol +
                                          "Server: rserver" + eol + eol);
            
            byte[] header = strHeader.getBytes();
            clientOutput.write(header,0,header.length);
            if(fileByes!=null)
            clientOutput.write(fileByes,0,fileByes.length);
            
            clientOutput.flush();
            System.out.println("Done!");
            client.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
        
    }
}