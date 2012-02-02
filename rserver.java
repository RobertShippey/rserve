import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class rserver
{
    private static Socket client = null;
    private static ServerSocket server = null;
    private static PrintWriter outStream = null;
    private static header request = null;
    private static header response = null;
    
    public static void main (String args[]) throws Exception
    {
        String eol = new String("\r\n");
        int port = 0;
        try { port = Integer.parseInt(args[0]); }
        catch(Exception e) { System.err.println("You did not set a valid port, the first available will be used.");}
        try {
            server = new ServerSocket(port);
            System.out.println("Server is running on " + server.getInetAddress().getHostName() + ":" + String.valueOf(server.getLocalPort()));
        }
        catch(IOException e) {
            System.err.println("Error when opening socket");
            System.err.println(e.getMessage());
            return; }
        catch(SecurityException e) {
            System.err.println("A security error occured");
            System.err.println(e.getMessage());
            return;
        }
        
        while(true)
        {
            System.out.println("Waiting for client...");
            request = new header();
            try{ client = server.accept(); 
                System.out.println("Got one!");}
            catch(IOException e) {
                System.err.println("Error when opening socket");
                System.err.println(e.getMessage());
                return; }
            catch(SecurityException e) {
                System.err.println("A security error occured");
                System.err.println(e.getMessage());
                return;
            }
            
            request.parseRequest(client);
            //String clientHeader = new String(getHeader(client));
            
            //System.out.println(clientHeader);
            
            System.out.println(request.path);
            //File myFile = new File ("index.html");
            File page = new File (request.path);
            
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(page));
            
            byte [] fileByes  = new byte [(int)page.length()];
            
            inputStream.read(fileByes,0,fileByes.length);
            page = null;
            inputStream = null;
            
            OutputStream clientOutput = client.getOutputStream();
            System.out.println("Sending...");
            
            Date mod = new Date (page.lastModified());
            SimpleDateFormat formatter = new SimpleDateFormat("E, d M yyyy H:m:s z");
            String lastModified = new String(formatter.format(mod));
            mod = null;
            formatter = null;
            
            String strHeader = new String("HTTP/1.0 200 OK" + eol +
                                          "Content-Type: text/html" + eol +
                                          "Last-Modified: " + lastModified + eol +
                                          "Content-Length: " + fileByes.length + eol +
                                          "Server: rserver" + eol + eol);
            lastModified = null;
            byte[] header = strHeader.getBytes();
            strHeader = null;
            clientOutput.write(header,0,header.length);
            header = null;
            clientOutput.write(fileByes,0,fileByes.length);
            
            clientOutput.flush();
            System.out.println("Done!");
            client.close();
            client = null;
        }
    }
}