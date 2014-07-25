import comp102x.IO;

public class Lab04GradedTask3
{
   public static void testCase1()
   {    
       Computer computer = new Computer();
       computer.makeChoice();
       int result = computer.getChoice().getType();
       IO.outputln(result);
   }
}
