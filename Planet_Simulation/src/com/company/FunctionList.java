package com.company;

import java.util.ArrayList;

public class FunctionList {

    public static int FindXCamOffset(ArrayList<CelestialBody> CBArray) {
        int xCamOffset = 0;
        for (CelestialBody body : CBArray) {
            if (body.name.equals(Settings.GetBodyCenter())) {
                if (Settings.GetApplyOffset()) {
                    xCamOffset = (int)body.xPos;
                }
            }
        }
        return xCamOffset;
    }
    public static int FindYCamOffset(ArrayList<CelestialBody> CBArray) {
        int yCamOffset = 0;
        for (CelestialBody body : CBArray) {
            if (body.name.equals(Settings.GetBodyCenter())) {
                if (Settings.GetApplyOffset()) {
                    yCamOffset = (int)body.yPos;
                }
            }
        }

        return yCamOffset;
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
