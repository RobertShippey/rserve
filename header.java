import java.net.*;
import java.io.*;

public class header
{
    public String method = null;
    public String path = null;
    public String version = null;
    public String response = null;
    public String reason = null;
    public String from = null;
    public String userAgent = null;
    public String server = null;
    public String lastModified = null;
    public String contentType = null;
    public String contentLength = null;
      
    public void parseRequest(Socket client) throws Exception
    {
        BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
        byte[] clientBytes = new byte [client.getReceiveBufferSize()];
        clientInput.read(clientBytes,0,clientBytes.length);
        String header = new String(clientBytes);
        
//        if(header.charAt(5).equals('G'))
//        {
//            method = new String("GET");
//        }
        //code
        return;
    }
    
    public byte[] getResponse() throws Exception
    {
        byte [] response = new byte [1];
         //code
        return response;
    }
    
}