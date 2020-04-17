package tes;

public class savedLine extends shape {
    private double endX;
    private double endY;
    public savedLine(double x, double y, String fill,String strokec,double strokeWidth,double endX,double endY){
        super(x,y,fill,strokec,strokeWidth);
        this.endX=endX;
        this.endY=endY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }
}
