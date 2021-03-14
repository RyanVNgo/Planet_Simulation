package com.company;

import java.util.ArrayList;

public class FunctionList {

    public static int MouseXOffset = 0;
    public static int MouseYOffset = 0;

    public static int FindXCamOffset(ArrayList<CelestialBody> CBArray) {
        int xCamOffset = 0;
        if (Settings.GetApplyOffset()) {
            if (Settings.GetFollowBody()) {
                for (CelestialBody body : CBArray) {
                    if (body.name.equals(Settings.GetBodyCenter())) {
                        xCamOffset = (int)body.xPos;
                    }
                }
            } else {
                xCamOffset = FindCenterX(CBArray);
            }
        }

        return xCamOffset + MouseXOffset;
    }
    public static int FindYCamOffset(ArrayList<CelestialBody> CBArray) {
        int yCamOffset = 0;
        if (Settings.GetApplyOffset()) {
            if (Settings.GetFollowBody()) {
                for (CelestialBody body : CBArray) {
                    if (body.name.equals(Settings.GetBodyCenter())) {
                        yCamOffset = (int)body.yPos;
                    }
                }
            } else {
                yCamOffset = FindCenterY(CBArray);
            }
        }

        return yCamOffset - MouseYOffset;
    }

    public static int FindCenterX(ArrayList<CelestialBody> CBArray) {
        int centerX = 0;
        int totalX = 0;

        for (CelestialBody body : CBArray) {
            totalX += body.xPos;
        }

        centerX = totalX/CBArray.size();

        return centerX;
    }
    public static int FindCenterY(ArrayList<CelestialBody> CBArray) {
        int centerY = 0;
        int totalY = 0;

        for (CelestialBody body : CBArray) {
            totalY += body.yPos;
        }

        centerY = totalY/CBArray.size();

        return centerY;
    }

    public static void ChangeMouseXOffset(int xMouseChange) {
        MouseXOffset += xMouseChange;
    }
    public static void ChangeMouseYOffset(int yMouseChange) {
        MouseYOffset += yMouseChange;
    }

    public static double FindDistanceBetweenTwoBodies(CelestialBody body1, CelestialBody body2) {
        double xDist, yDist, pDist;

        xDist = body1.xPos - body2.xPos;
        yDist = body1.yPos - body2.yPos;
        pDist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

        return pDist;
    }

    public static double FindGravitationalForceBetweenTwoBodies(double mass1, double mass2, double pDist) {
        double GC = 6.6743;
        double gForce;
        gForce = (GC * mass1 * mass2) /  Math.pow(pDist, 2);
        return gForce;
    }

    public static double FindXForce(CelestialBody body1, CelestialBody body2) {
        double xDist, pDist;
        double xForce, gForce;

        xDist = body1.xPos - body2.xPos;
        pDist = FindDistanceBetweenTwoBodies(body1, body2);
        gForce = FindGravitationalForceBetweenTwoBodies(body1.mass, body2.mass, pDist);
        xForce = (gForce * xDist) / pDist;

        return xForce;
    }
    public static double FindYForce(CelestialBody body1, CelestialBody body2) {
        double yDist, pDist;
        double yForce, gForce;

        yDist = body1.yPos - body2.yPos;
        pDist = FindDistanceBetweenTwoBodies(body1, body2);
        gForce = FindGravitationalForceBetweenTwoBodies(body1.mass, body2.mass, pDist);
        yForce = (gForce * yDist) / pDist;

        return yForce;
    }

}
