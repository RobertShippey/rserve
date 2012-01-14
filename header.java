import java.net.*;
import java.io.*;

public class header
{
    public String method = null;
    public String path = null;
    public String version = null;
    public String status = null;
    public String from = null;
    //public String userAgent = null;
    public String server = null;
    public String lastModified = null;
    public String contentType = null;
    public String contentLength = null;
    private byte[] _body = null;
    public void parseRequest(Socket client) throws Exception
    {
        String eol = System.getProperty("line.separator");
        BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
        byte[] clientBytes = new byte [client.getReceiveBufferSize()];
        clientInput.read(clientBytes,0,clientBytes.length);
        String header = new String(clientBytes);
        
        //get first line of header
        header = header.substring(0,header.indexOf((eol.charAt(0))));
        //System.out.println(header + "@");
        //remove space
        header = header.trim();
        //System.out.println(header + "@");
        
        //get the method
        method = new String(header.substring(0,header.indexOf(' ')));
        //System.out.println(method + "@");
        
        //remove method from header
        header = header.substring(header.indexOf(' '));
        header = header.trim();
        //System.out.println(header + "@");
        
        //get the path
        String currentDir = System.getProperty("user.dir");
        path = new String(currentDir + header.substring(0,header.indexOf(' ')));
        //System.out.println(path + "@");
        
        //remove the path from header
        header = header.substring(header.indexOf(' '));
        header = header.trim();
        //System.out.println(header + "@");
        
        //test if path is directory
        File dirTestFile = new File(path);
        if(dirTestFile.isDirectory()){
            //if so, concat index.html
            path = path + "index.html";
            //System.out.println(path + "@dir");
        }
        
        //test if file exists
        File fileTestFile = new File(path);
        if(fileTestFile.isFile()){
            //System.out.println(path + "@file");
        } else {
            path = currentDir + "/404.html";
            //System.out.println(path + "@nofile");
        }
        
        version = new String(header);
        //System.out.println(version + "@");
        
        //code
        return;
    }
    
    public byte[] getResponse() throws Exception
    {
        byte [] response = new byte [1];
         //code
        return response;
    }
    
    public byte[] getBody() throws Exception
    {
        return _body;
    }
    
    public void setBody(byte[] body) throws Exception
    {
        _body = new byte[body.length];
        _body = body;
        return;
    }
    
}