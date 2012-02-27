import java.io.*;

public class header
{
    public String method = null;
    public String path = null;
    public String version = null;
    public String status = null;
    public String from = null;
    public String server = null;
    public String lastModified = null;
    public String contentType = null;
    public int contentLength = -1;
    //private byte[] _body = null;
    //public String userAgent = null;
    
    private final String eol = System.getProperty("line.separator");
    
    public void parseRequest(byte[] r) throws IOException
    {
        String header = new String(r);
        
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
        path = header.substring(0,header.indexOf(' '));
        //System.out.println(path + "@");
        
        //remove the path from header
        header = header.substring(header.indexOf(' '));
        header = header.trim();
        //System.out.println(header + "@");
        
        version = header;
        //System.out.println(version + "@");
        
        //code
        return;
    }
  
    
//    public byte[] getBody() throws Exception
//    {
//        return _body;
//    }
//    
//    public void setBody(byte[] body) throws Exception
//    {
//        _body = new byte[body.length];
//        _body = body;
//        return;
//    }
    
    public byte [] getResponse()
    {
        String header = "HTTP/1.0 ";
        header+=status;
        header+= eol;
        
        header+="Content-Type: text/html";
        header+=eol;
        
        if(lastModified!=null)
        {
            header+="Last-Modified: ";
            header+=lastModified;
            header+=eol;
        }
        
        if(contentLength>0)
        {
            header+="Content-Length: ";
            header+=contentLength;
            header+=eol;
        }
        
        header+="Server: rserver:";
        header+=eol;
        header+=eol;

        return header.getBytes();
    }
    
}