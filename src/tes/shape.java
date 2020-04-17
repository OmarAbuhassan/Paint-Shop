package tes;

import java.awt.*;
import java.io.Serializable;

public class shape implements Serializable {
    private double x;
    private double y;
    private String fillColor;
    private String strokeColor;
    private double strokeWidth;

    public shape(double x, double y, String fillColor, String strokeColor, double strokeWidth) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getFillColor() {
        return fillColor;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }
}