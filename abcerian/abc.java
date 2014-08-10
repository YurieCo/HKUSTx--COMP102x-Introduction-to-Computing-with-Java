import java.lang.String;
/**
 * Write a description of class abc here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class abc
{
    // instance variables - replace the example below with your own
    
    /***
     * Author: Cojocari Iurie
     * 
     */
    boolean abecedarian(String s){
        
        for(int i = 0; i < s.length()-1;i++)
        if (s[i] > s[i+1]) return false;
        return true;
    }
}
