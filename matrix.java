import java.io.*;
import java.util.*;

public class matrix {

    public static void main(String args[]) throws Exception {
        BufferedReader letter = new BufferedReader(new InputStreamReader(System.in));
        int width = Integer.parseInt(args[0]);
        char line[] = new char[width];
        Random r = new Random();
        while (true) {
            for(int x=0;x<line.length;x++)
            {
                if(r.nextBoolean()){
                    line[x] = (char)(r.nextInt(26) + 'a'); //got this line from Stack Overflow
                } 
                else{
                    line[x] = ' ';
                }
            }
            System.out.println(line);
            Thread.sleep(50);
        }
    }
}