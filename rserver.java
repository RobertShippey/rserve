import java.net.*;
import java.io.*;

public class rserver
{
    private static Socket client = null;
    private static ServerSocket server = null;
    private static PrintWriter outStream = null;
    
    public static void main (String args[]) throws Exception
    {
        System.out.println("Server is running...");
        server = new ServerSocket(8081);
        
        while(true)
        {
            System.out.println("Waiting for client...");
            client = server.accept(); 
            System.out.println("Got one!");
            BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
            byte[] clientBytes = new byte [200];
            clientInput.read(clientBytes,0,100);
            for(int x=0;x<100;x++)
            {
                System.out.print(clientBytes[x]);
            }
            String clientHeader = new String(getHeader(client));
            
            System.out.println(clientHeader);
            
            
            File myFile = new File ("index.html");
            
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(myFile));
            
            byte [] fileByes  = new byte [(int)myFile.length()];
            
            inputStream.read(fileByes,0,fileByes.length);
            
            OutputStream clientOutput = client.getOutputStream();
            System.out.println("Sending...");
            
            //String strHeader = new String("HTTP/1.0 200 OK\nContent-Type: text/java\n");
            //char[] header = strHeader.toCharArray();
            //clientOutput.write(header,0,strHeader.length());
            clientOutput.write(fileByes,0,fileByes.length);
            
            clientOutput.flush();
            System.out.println("Done!");
            client.close();
        }
    }
    
    private static String getHeader (Socket client) throws Exception
    {
        BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
        byte[] clientBytes = new byte [client.getReceiveBufferSize()];
        clientInput.read(clientBytes,0,clientBytes.length);
        String header = new String(clientBytes);
        return header;
    }
}