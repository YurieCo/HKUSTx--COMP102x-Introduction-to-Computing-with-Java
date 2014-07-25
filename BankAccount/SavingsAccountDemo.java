import comp102x.IO;

/**
 * SavingsAccountDemo gives a demo for the SavingsAccount class
 */
public class SavingsAccountDemo
{
    public static void main(String[] args) {    
      SavingsAccount sAcct = new SavingsAccount(1000, "John", 0.1);
      double fBalance = sAcct.getBalance();
      
      sAcct.compoundInterest(10);        
      IO.outputln("Compound interest computed by loop: " + sAcct.getBalance());
      
      // compute compound interest using the formula P*(1+r)^n
      fBalance = fBalance * Math.pow((1 + sAcct.interestRate), 10);
      IO.outputln("Compound interest computed by formula: " + fBalance);
    }
}
