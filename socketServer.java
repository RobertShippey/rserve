/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
/**
 *
 * @author Robert
 */

public class socketServer {

    /**
     * @param args the command line arguments
     */
    
    public int _port = -1;
    boolean _isRunning = false;
    private ServerSocket server = null;
    private Socket client = null;
    private PrintWriter outStream = null;
    private BufferedReader inStream = null;

    public socketServer() {}
    public socketServer(int port) {_port = port;}
    public int getPort () {return _port;}
    public void setPort(int port) {_port = port;}
    public boolean isRunning () { return _isRunning;}
    
    

    final public void run()
    {
        try { server = new ServerSocket(getPort()); }
        catch (IOException ex) { System.err.println(ex.getMessage()); return;}
        while(isRunning())
        {
             try {client = server.accept(); }
             catch (IOException ex) {System.err.println(ex.getMessage()); continue;}
             try {
             outStream = new PrintWriter(client.getOutputStream(), true);
             inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
             outStream.println(processMessage(inStream.readLine()));
             }
             catch (IOException ex) {System.err.println(ex.getMessage()); continue;}
          }
             
    }
    
    public String processMessage(String message)
    {
        return message;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
    }
}
