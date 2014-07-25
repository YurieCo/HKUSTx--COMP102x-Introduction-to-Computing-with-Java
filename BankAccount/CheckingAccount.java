/**
 * CheckingAccount is a subclass of BankAccount. 
 * A fee is charged for each withdrawal from a CheckingAccount
 */
public class CheckingAccount extends BankAccount {
    // instance variables perChequeFee is the fee charged per cheque
    private double perChequeFee;

    /**
     * Constructor for objects of class CheckingAccount
     */
    public CheckingAccount(double initialBalance, String name, double fee)
    {
        super(initialBalance, name); // constructor from the superclass is called
        perChequeFee = fee;
    }

    /**
     * The method withdraw withdraws with wAmount plue a fee from CheckingAcount
     * 
     * @param  wAmount   the amount to be withdraw from the account
     */
    public void withdraw(double wAmount) {
        // a fee is added to each withdrawal
        super.withdraw(wAmount + perChequeFee); 
    }
}
