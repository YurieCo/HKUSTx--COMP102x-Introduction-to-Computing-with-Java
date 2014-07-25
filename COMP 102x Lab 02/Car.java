import comp102x.IO;
/**
 * Write a description of class Car here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Car
{
    private  int odometrer = 0;
    private String owner;
    
    public Car(){}
    
    public Car(String owner){
    this.owner = owner;
    }
    
    public void moveCar(int d){
        odometrer = odometrer + Math.abs(d);
        IO.output(owner + "'s car has moved " + d+ " unints");
    }
    
    public void turnCar(double angle){
        IO.output(owner + "'s car has turned "+angle + " degree");
    }
    
    public int getOdometer(){
        return odometrer;
    }
}
