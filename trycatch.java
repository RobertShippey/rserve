
public class trycatch
{


 public static void main (String[] args)
 {
    int input = 0;
    try
    {
        input = Integer.parseInt(args[0]);
    }
    catch (ArrayIndexOutOfBoundsException ex)
    {
        System.err.println ("Please enter a param.");
        System.exit(0);
    }
    catch(NumberFormatException ex)
    {
       System.err.println("Error parsing your value:");
       System.err.println(ex.getMessage());
       System.exit(0);
    }

    System.out.printf("%d is a valid int\n", input);
 }



}
