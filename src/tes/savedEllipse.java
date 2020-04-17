package tes;

public class savedEllipse extends shape {

	private double radiusX;
	private double radiusY;
	public savedEllipse(double x, double y, String fill,String strokec,double strokeWidth,double radiusX,double radiusY) {
		super(x, y, fill, strokec, strokeWidth);
		this.radiusX = radiusY;
		this.radiusY = radiusX;
	}


	public double getRadiusX() {
		return radiusX;
	}

	public double getRadiusY() {
		return radiusY;
	}
}
