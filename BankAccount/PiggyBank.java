/**
 * A bank account has a balance and an owner who can make
 * deposits to and withdrawals from the account.
 */
public class PiggyBank
{
    // instance variables
    private double balance = 0.0;
    private String owner = "NoName";

    /**
     * Constructor for objects of class BankAccount
     */
    public PiggyBank() {}
    
    public PiggyBank(double initialBalance, String name)
    {
        balance = initialBalance;
        owner = name;
    }
    
    public void depositToAcct(double dAmount) 
    {
        balance = balance + dAmount;
    }
    
    public void withdrawFromAcct(double balance) 
    {
        this.balance = this.balance - balance;
    }
    
    public double getBalance()
    {
        return balance;
    }
}
