
public class PowerCalculator
{
    /**
     * Calculate the non-negative power of an integer number. If a negative power is input, the method returns 1.
     * 
     * @param number The number to take power.
     * @param power The power factor to be taken to.
     * @return The calculation result after taking power of the integer number.
     */
    public static long powerN(int number, int power) {
        
        // write your code after this line
       
         if (power > 0)
         {
              int pwd = 1;
              int nr = number;
         
             while( pwd != power)
             {
                 nr *= number;
                 pwd++;
             }
             return nr;
         }
        
        
        return 1; // remove this line after completing your code
    }
}
