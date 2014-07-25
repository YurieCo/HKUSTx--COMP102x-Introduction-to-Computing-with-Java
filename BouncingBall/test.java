
/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class test
     */
    public test(int x, int y, int z)
    {
        // initialise instance variables
        if(z < y)
            if(x > y)
                x = 10;
        else
            x = 3; 
            
            System.out.print("x: " + x + " y: " + y +" z: "+z);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
