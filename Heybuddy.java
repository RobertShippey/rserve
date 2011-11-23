import java.io.*;
public class Heybuddy
{
    public static void main (String[] args)
    {
        Boolean old = null;
        old = IsSwitchValued(args, "-o");
        try
        {
            if(old)
            {
                System.out.println("Hello World!");
            }
            else
            {
                System.out.println("Hey Buddy.");
            }
        }
        catch (Exception e)
        {
            System.err.println("Something's wrong.");
            System.err.println(e.getMessage());
        }
    }
    
    public static boolean IsSwitchValued(String[] args, String strSwitch)
	{
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].equals(strSwitch))
				return true;
		}
		return false;
	}
    
}

