import comp102x.IO;  
  
public class Quiz1 {
  
        private int x;

        public Quiz1(int x) {
  
                x = x;
        }

        public static void main(String[] args) {
  
                Quiz1 q1 = new Quiz1(10);
                IO.outputln(q1.x);
        }
}