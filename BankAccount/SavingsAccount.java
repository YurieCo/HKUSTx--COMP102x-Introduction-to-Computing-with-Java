import comp102x.IO;

/**
 * SavingsAccount is a subclass of BankAccount. 
 * Savings account earns compound interest from account balance
 */
public class SavingsAccount extends BankAccount {
    double interestRate;  // Instance variable for storing the interest rate
    
    // Declaration of constructors for SavingsAccount
    // public SavingsAccount() {}
    
    public SavingsAccount(double initialBalance, String name, double rate){
        super(initialBalance, name);
        interestRate = rate;
    }  
    
    /**
     * The method compoundInterest computes compound interest for a given duration
     * 
     * @param  duration   The number of time the interest is to be compounded
     */
    public void compoundInterest(int duration){
       
       for (int i =1; i <= duration; i++) {
           double currentBalance = getBalance();
           deposit(currentBalance * interestRate);
           IO.outputln("New balance for the " + i + "th period" + " is " + getBalance());
        }
    }
       
    /**
     * setInterestRate set the interestRate to a new value
     * 
     * @param  rate   New interest rate
     */       
    public void setInterestRate(double rate) {
        interestRate = rate;
    }
}

