
public class Scope1 {

    private int c =1;
    private int a = 2;
    
    public int one (int a, int b) {
    
        c = a * 2;
        a = 3 + b;
        b = b + 1;

        IO.outputln("One: " + a + "," + b + "," + c);
        return b;
    }

    public int two (int a, int b) {
    
        c = one (b, a);
        this.a = a + 1;
        IO.outputln("Two: " + a + "," + b + "," + c);
        return a;
   }
   
    public static void main(String[ ] args) {
    
        Scope1 s = new Scope1();
        int a = 4; 
        int b = 5;

        b = s.two (a, b);
        IO.outputln("Main: " + s.a + "," + b + "," + s.c);
   }
}