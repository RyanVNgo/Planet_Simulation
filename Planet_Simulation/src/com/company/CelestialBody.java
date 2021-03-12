package com.company;

import java.awt.*;

public class CelestialBody {

    public final String name;
    public final double mass;
    public final int diam;
    public double xVel;
    public double yVel;
    public double xPos;
    public double yPos;
    public Color color;

    public double speed;
    public double xAcc;
    public double yAcc;
    public boolean dead = false;

    CelestialBody(String name, double mass, int diam, double xVel, double yVel, double xPos, double yPos, Color color) {
        this.name = name;
        this.mass = mass;
        this.diam = diam;
        this.xVel = xVel;
        this.yVel = yVel;
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
    }

    public void UpdateSpeed() {
        speed = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
    }

}
