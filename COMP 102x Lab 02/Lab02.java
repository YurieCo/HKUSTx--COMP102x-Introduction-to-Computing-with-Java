import comp102x.IO;


public class Lab02
{
    
    public static void multiply()
    {
        // Please write your code after this line
        

        
        
        
    }
    
    public static void calculateTriangleArea()
    {
        // Please write your code after this line
        
        
        
        
        
    }
    
    public static void solveQuadraticEquation()
    {
        // Please write your code after this line
        int a;
        int b;
        int c;
        
        System.out.print("Enter a: ");
        a = IO.inputInteger();
        System.out.print("Enter b: ");
        b = IO.inputInteger();
        System.out.print("Enter c: ");
        c = IO.inputInteger();
        
        double delta =(double) Math.pow(b,2)-4*a*c;
        
        double x1 = (-b + Math.sqrt(delta))/(2.0*a);
        double x2 = (-b - Math.sqrt(delta))/(2.0*a);
        
        IO.output("First solution for x = " + x1);
        IO.output("\nSecond solution for x = "+ x2);
        
                
        
        
        
        
    }
}
