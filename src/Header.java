
import java.text.SimpleDateFormat;

public class Header {

    private String method = null;
    private String path = null;
    private String version = null;
    private String status = null;
    private String from = null;
    //public String userAgent = null;
    private String server = "rserve";
    private String lastModified = null;
    private String contentType = "text/html";
    private String contentLength = null;
    private byte[] body = null;
    private String eol;
    
    Header(){
         eol = rserver.eol;
         version = "HTTP/1.0";
    }
    
    Header(int code){
        this();
        status = String.valueOf(code);
    }

    public void parseRequest(byte[] request) {
        String header = new String(request);
        
        String[] lines = header.split(eol);
        
        String[] firstLine = lines[0].split(" ");
        
        method = firstLine[0];
        path = firstLine[1];
        version = firstLine[2];
        
        //todo: parse the other lines
        
        return;
    }

    public byte[] getResponse()  {
        String response = version + " " + status + eol;
        if(contentType != null && body != null){
            response += "Content-Type: " + contentType + eol;
        }
        if(lastModified != null && body != null){
            response += "Last-Modified: " + lastModified + eol;
        }
        if(contentLength != null && body != null){
            response += "Content-Length: " + contentLength + eol;
        }
        response += "Server: " + server + eol + eol;
        if(body != null) {
            response += body;
        }
    
        return response.getBytes();
    }

    public void setBody(byte[] bytes) {
        body = new byte[bytes.length];
    }
    
    public String getPath(){
        return path;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setLastModified(long modified){
        SimpleDateFormat formatter = new SimpleDateFormat("E, d M yyyy H:m:s z");
        lastModified = formatter.format(modified);
    }
    
    public void setContentLength(int length){
        contentLength = String.valueOf(length);
    }
}