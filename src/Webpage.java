
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robert
 */
public class Webpage extends File{
    private static final long serialVersionUID = 1L;
    private String path = null;
    
    Webpage(String filename){
        super(filename);
        path = filename;
    }
    
}
