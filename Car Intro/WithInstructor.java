import comp102x.IO;
import comp102x.Canvas;
import comp102x.ColorImage;
import java.lang.String;
/**
 * Write a description of class WithInstructor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WithInstructor
{
    
    private String ownerr = "NoOwner";
    private ColorImage carImage;
    private double cost;
    private double gasMileage;
    private double gasinTank;
    
    public void makeTurn(int degree){
        IO.output(this.ownerr +"'s car has turned "+degree+" degree");
        carImage.setRotation(carImage.getRotation()-degree);
        


    }
    
    public void movedForward(int dist){
        IO.output(this.ownerr+"'s car has moved forward \n");
        carImage.setX(carImage.getX()+dist);
        
        double gasUsed = dist / 100 * this.gasMileage ;
        gasinTank = gasinTank - gasUsed;
        IO.output("AMount of gas used: " + gasUsed + ", gas remained: "+gasinTank);
    }
    
    WithInstructor(){}
    WithInstructor(String owner, String image, double cost, double gasMileage, double gasinTank ){
        this.ownerr = owner;
        this.carImage = new ColorImage (image);
        this.cost = cost;
        this.gasMileage = gasMileage;
        this.gasinTank = gasinTank;
    }
    
    
    	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getcost(){
	    return this.cost;
	   }
	
	public double getGasMileage() {
		return gasMileage;
	}
	public void setGasMileage(double gasMileage) {
		this.gasMileage = gasMileage;
	}
	public double getGasinTank() {
		return gasinTank;
	}
	/**
	 *this methods adds gas in tanks of the ca 
	 *
	 */
	public void setGasinTank(double gasinTank) {
		this.gasinTank += gasinTank;
	}
	 

    
    void getInfo(){
     IO.output(this.ownerr + "'s car cost "+getcost()+ " \n"+ getGasMileage()+" per mille" + "with "+getGasinTank()+" in tank");
     IO.output("\n current position is " + carImage.getX()+ ", "+carImage.getY());
    }
    
}
