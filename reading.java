import java.io.*;

class readtest
{
    private static String _text;
    private static BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
           
    public static String getText() {return _text;}
    public static void setText (String text) { _text = text;}
    
    public static void main(String[] args)
    {
        while(true)
        {
            try{ setText(user.readLine()); }
            catch (IOException e) {System.err.println(e.getMessage());}
            //display '==='
            System.out.println("=====");
            //write line
            System.out.println(getText());
        }
    }
}
