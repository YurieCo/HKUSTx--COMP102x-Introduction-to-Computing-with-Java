public class Circle implements Shape {
    private double radius;
    private final double PI = 3.1416;
    
    public Circle (double r) {
        radius = r;
    }
    
    public double area() {
        return PI * radius * radius;
    }
    public double perimeter() {
        return 2 * PI * radius;
    }
}