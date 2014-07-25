import comp102x.IO;

public class Lab04GradedTask4
{
    public static void testCase1()
    {
        Player player = new Player();
        boolean result = player.playAgain();
        IO.outputln(result);
    }
}
