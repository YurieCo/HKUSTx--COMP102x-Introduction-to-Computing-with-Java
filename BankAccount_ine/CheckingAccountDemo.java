import comp102x.IO;

/**
 * CheckingAccountDemo gives a demo for the CheckingAccount class
 */
public class CheckingAccountDemo
{
    public static void main(String[] args) {    
      CheckingAccount chequeAcct = new CheckingAccount(1000, "John", 2.0);
      double dAmount;
      double wAmount;
      char option;
      
      do {
         IO.outputln("Do you want to make another transaction?");
         IO.output("Enter 'D' for deposit, 'W' for withdrawal, 'B' to check balance and 'E' to exit: ");
         option = IO.inputCharacter();
            
         switch (option) {
             case 'D': IO.output("Enter the deposit amount: ");
                       dAmount = IO.inputDouble();
                       chequeAcct.deposit(dAmount);
                       break;
             case 'W': IO.output("Enter the withdrawal amount: ");
                       wAmount = IO.inputDouble();
                       chequeAcct.withdraw(wAmount);
                       break;
             case 'B': IO.outputln("The current balance in your account is " + chequeAcct.getBalance() + ".");
                       break;
             default:
            }
      } while ( option == 'D' || option == 'W' || option == 'B');
        
        IO.outputln("Thank you!");
    }
}
