import comp102x.IO;

/**
 * A class of Car objects that can move forward, backward and turn
 */
public class Car
{
    private int odometer = 0;           // An odometer reading initialized to 0
    private String owner = "NoName";    // Name of owner
    
    /**
     *   Default constructor for a Car object
     */
    public Car () {}  
    
    /**
     *   Constructor for a Car object with a new owner’s name
     *   @param   name                   name of owner
     */
    public Car(String name) {   // Constructor takes a name as argument
        owner = name;
    }
    
    /**
    *   moveCar moves a car forward or backward by dist units
    *   @param   dist     Moving distance
    */
    public void moveCar(int dist) { 
        odometer = odometer + Math.abs(dist);
        IO.outputln(owner + "'s car has moved " + dist + " units."); 
    }
    
    /**
    *   turnCar turns a car by a given degree
    *   @param   angle     Turn angle in degrees
    */
    public void turnCar(double angle) { 
        IO.outputln(owner + "'s car has turned " + angle + " degrees."); 
    }
    
    /**
    *   getOdometer gets the odometer reading of a car
    *   @return     The value of odometer
    */
    public int getOdometer() {
        return odometer;
    }
    
}