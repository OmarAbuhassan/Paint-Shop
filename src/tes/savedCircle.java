package tes;

public class savedCircle extends shape {

	private double radius;

	public savedCircle(double x, double y, String fill,String strokec,double strokeWidth,double radius){
		super(x,y,fill,strokec,strokeWidth);
		this.radius=radius;

	}

    public double getRadius() {
        return radius;
    }
}
