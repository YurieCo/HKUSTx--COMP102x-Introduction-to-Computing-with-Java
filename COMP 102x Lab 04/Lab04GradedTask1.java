import comp102x.IO;

public class Lab04GradedTask1
{
   public static void testCase1()
   {
       Choice choice1 = new Choice(0); // Rock
       Choice choice2 = new Choice(1); // Paper
       int result = choice1.compareWith(choice2); // -1
       IO.outputln(result);
   }
   
   
}
