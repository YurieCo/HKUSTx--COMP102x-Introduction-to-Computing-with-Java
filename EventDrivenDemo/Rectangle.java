public class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double w, double h) {
        width = w;
        height = h;
    }
    
    public double area() {
        return width*height;
    }
    public double perimeter() {
        return (width+height) * 2;
    }
}
