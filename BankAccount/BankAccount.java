/**
 * A bank account has a balance and an owner who can make
 * deposits to and withdrawals from the account.
 */
public class BankAccount
{
    // instance variables
    private double balance = 0.0;
    private String owner = "NoName";

    /**
     *   Default constructor for a bank account with zero balance
     */
    public BankAccount() { }
    /**
     *   Construct a balance account with a given initial balance and owner’s name
     *   @param   initialBalance     the initial balance
     *   @param   name                   name of owner
     */
    public BankAccount(double initialBalance, String name)
    {
        balance = initialBalance;
        owner = name;
    }
    /**
     *   Method for depositing money to the bank account
     *   @param   dAmount     the amount to be deposited
     */
    public void deposit(double dAmount) 
    {
        balance = balance + dAmount;
    }
    /**
     *   Method for withdrawing money from the bank account
     *   @param   wAmount     the amount to be withdrawn
     */
    public void withdraw(double wAmount) 
    {
        balance = balance - wAmount;
    }
    /**
     *   Method for getting the current balance of the bank account
     *   @return   the current balance
     */
    public double getBalance()
    {
        return balance;
    }
    
    
    
    
    
//     /**
//      *   Main method for testing the bank account
//      */
//     public static void main(String[] args) {
//         BankAccount testAccount = new BankAccount();
//         testAccount.deposit(100);
//         testAccount.withdraw(50);
//         IO.outputln(testAccount.owner + "'s account has a balance of $" 
//                     + testAccount.balance);
//         
//         BankAccount myAccount = new BankAccount(100, "TC");
//         myAccount.deposit(100);
//         myAccount.withdraw(50);
//         IO.outputln(myAccount.owner + "'s account has a balance of $" 
//                     + myAccount.balance);
//                     
//         SavingsAccount saveAcct = new SavingsAccount(100, "John", 0.1);
//         //SavingAccount saveAcct = new SavingAccount();
// 
//         saveAcct.compoundInterest(10);        
//         //IO.outputln( saveAcct.getBalance());
//         
//       CheckingAccount chequeAcct = new CheckingAccount(1000, "John", 2.0);
//         double dAmount;
//         double wAmount;
//         char option;
//         do {
//             IO.outputln("Do you want to make another transaction?");
//             IO.output("Enter 'D' for deposit, 'W' for withdrawal, 'B' to check balance and 'E' to exit: ");
//             option = IO.inputCharacter();
//             
//             switch (option) {
//                 case 'D': IO.output("Enter the deposit amount: ");
//                           dAmount = IO.inputDouble();
//                           chequeAcct.deposit(dAmount);
//                           break;
//                 case 'W': IO.output("Enter the withdrawal amount: ");
//                           wAmount = IO.inputDouble();
//                           chequeAcct.withdraw(wAmount);
//                           break;
//                 case 'B': IO.outputln("The current balance in your account is " + chequeAcct.getBalance());
//                           break;
//                 default:
//             }
//         } while ( option == 'D' || option == 'W' || option == 'B');
//         
//         IO.outputln("Thank you!");
//     }
}