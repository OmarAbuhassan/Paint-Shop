package tes;

public class savedRectangle extends shape{

	private double length;
	private double width;
	public savedRectangle(double x, double y, String fill,String strokec,double strokeWidth,double length, double width){
		super(x,y,fill,strokec,strokeWidth);
		this.length=length;
		this.width=width;
	}
	public double getLength(){
		return length;

	}
	public double getWidth() {
		return width;
	}
}
