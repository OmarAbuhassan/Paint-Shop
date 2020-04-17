package tes;

public class savedTriangle extends shape{

	private double x1;
	private double x2;
	private double y1;
	private double y2;
	private double x3;
	private double y3;



	public savedTriangle(double x1, double y1, double x2, double y2, double x3, double y3, String fill, String strokec, double strokeWidth){

		super(x1,y1,fill,strokec,strokeWidth);

		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
	}

	public double getX1() {
		return x1;
	}

	public double getX2() {
		return x2;
	}

	public double getY1() {
		return y1;
	}

	public double getY2() {
		return y2;
	}

	public double getX3() {
		return x3;
	}

	public double getY3() {
		return y3;
	}
}
