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
        String eol = System.getProperty("line.separator");
        BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
        byte[] clientBytes = new byte [client.getReceiveBufferSize()];
        clientInput.read(clientBytes,0,clientBytes.length);
        String header = new String(clientBytes);
        
        method = new String(header.substring(0,header.indexOf(' ')));
        header = header.substring(header.indexOf(' '));
        header.trim();
        
        path = new String(header.substring(0,header.indexOf(' ')));
        header = header.substring(3);
        header.trim();
        
        File dirTestFile = new File(path);
        if(dirTestFile.isDirectory())
            path = new String(path + "index.html");
        
        File fileTestFile = new File(path);
        if(!fileTestFile.isFile())
            path = new String("/404.html");
        
        version = new String(header.substring(0,header.indexOf(eol.charAt(0))));
        
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