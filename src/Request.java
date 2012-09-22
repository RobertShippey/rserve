
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robert
 */
public class Request extends Thread {

    private final Socket client;
    private Header request = null;
    private final String eol = "\r\n";
    private Header response;
    private Webpage page;

    Request(Socket c) {
        client = c;
        request = new Header();
    }

    @Override
    public void run() {
        try {
            BufferedInputStream clientInput = new BufferedInputStream(client.getInputStream());
            byte[] requestBytes = new byte[client.getReceiveBufferSize()];
            clientInput.read(requestBytes, 0, requestBytes.length);

            request.parseRequest(requestBytes);
        } catch (IOException ioe) {
            System.err.println("Couldn't get request from client!");
            return;
        }


        page = new Webpage(request.getPath());

        if (page.isDirectory()) {
            page = new Webpage(request.getPath() + "index.html");
        }

        if (!page.isFile()) {
            response = new Header(404);
            page = new Webpage("404.html");
            if (!page.isFile()) {
                page = null;
            }
        } else {
            response = new Header(200);
        }



        //System.out.println(page.getPath());

        try {
            if (page != null) {
                byte[] fileBytes;
                BufferedInputStream inputStream;
                inputStream = new BufferedInputStream(new FileInputStream(page));
                fileBytes = new byte[(int) page.length()];
                inputStream.read(fileBytes, 0, fileBytes.length);
                response.setBody(fileBytes);
                response.setLastModified(page.lastModified());
                response.setContentLength(fileBytes.length);
            }


            OutputStream clientOutput = client.getOutputStream();
            System.out.println("Sending...");

            byte[] headerBytes = response.getResponse();
            clientOutput.write(headerBytes, 0, headerBytes.length);

            clientOutput.flush();
            System.out.println("Done!");

            client.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println("Internal Error Occurred");
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

    }
}
