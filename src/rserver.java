
import java.net.*;
import java.io.*;

public class rserver extends Thread {

    private static ServerSocket server = null;
    private static int port = 0;
    
    public static final String eol = "\r\n";

    public static void main(String args[]) {
        try {
            port = Integer.parseInt(args[0]);
            server = new ServerSocket(port);
            System.out.println("Server is running on "
                    + server.getInetAddress().getHostName() + ":"
                    + String.valueOf(server.getLocalPort()));
        } catch (NumberFormatException nfe) {
            System.err.println("You did not set a valid port, the first available will be used.");
            System.err.println(nfe.getMessage());
            return;
        } catch (IOException ioe) {
            System.err.println("Error when opening socket");
            System.err.println(ioe.getMessage());
            return;
        } catch (SecurityException se) {
            System.err.println("A security error occured. Try sudo.");
            System.err.println(se.getMessage());
            return;
        }

        int x = 0;
        while (true) {
            System.out.println(++x);
            System.out.println("Waiting for client...");
            try {
                Request serverRequest = new Request(server.accept());
                serverRequest.start();
                System.out.println("Got one!");
            } catch (IOException e) {
                System.err.println("Error when opening socket");
                System.err.println(e.getMessage());
                continue;
            } catch (SecurityException e) {
                System.err.println("A security error occured");
                System.err.println(e.getMessage());
                continue;
            }

        }
    }
}