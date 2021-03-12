package com.company;

public class Settings {

    private static final int UPS = 60;

    private static final boolean applyOffset = true;
    private static final boolean drawDistLines = true;
    private static final boolean drawNames = false;
    private static final String bodyCenter = "Earth";

    public static int GetUpdatesPerSecond() {
        return UPS;
    }
    public static boolean GetApplyOffset() {
        return applyOffset;
    }
    public static boolean GetDrawDistLines() {
        return drawDistLines;
    }
    public static boolean GetDrawNames() {
        return drawNames;
    }
    public static String GetBodyCenter() {
        return bodyCenter;
    }

}
