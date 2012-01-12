import java.net.*;
import java.io.*;

public class rserver
{
    private static Socket client = null;
    private static ServerSocket server = null;
    private static PrintWriter outStream = null;
    private static header request = null;
    private static header response = null;
    
    public static void main (String args[]) throws Exception
    {
        String eol = System.getProperty("line.separator");
        
        System.out.println("Server is running...");
        server = new ServerSocket(8080);
        
        while(true)
        {
            System.out.println("Waiting for client...");
            client = server.accept(); 
            System.out.println("Got one!");
            request = new header();
            request.parseRequest(client);
            //String clientHeader = new String(getHeader(client));
            
            //System.out.println(clientHeader);
            
            
            File myFile = new File ("index.html");
            //File page = new File (request.path);
            
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(myFile));
            
            byte [] fileByes  = new byte [(int)myFile.length()];
            
            inputStream.read(fileByes,0,fileByes.length);
            
            OutputStream clientOutput = client.getOutputStream();
            System.out.println("Sending...");
            
            String strHeader = new String("HTTP/1.0 201 OK" + eol +
                                          "Content-Type: text/plain" + eol +
                                          "Last-Modified: " + myFile.lastModified() + eol +
                                          "Content-Length: " + fileByes.length + eol +
                                          "Server: rserver" + eol + eol);
            byte[] header = strHeader.getBytes();
            //clientOutput.write(header,0,header.length);
            clientOutput.write(fileByes,0,fileByes.length);
            
            clientOutput.flush();
            System.out.println("Done!");
            client.close();
        }
    }
}