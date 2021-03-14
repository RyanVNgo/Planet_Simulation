package com.company;

public class Settings {

    private static final int UPS = 600;

    private static final boolean applyOffset = true;
    private static final boolean followBody = false;
    private static final String bodyCenter = "Pluto";
    private static final boolean drawDistLines = false;
    private static final boolean drawNames = false;

    public static int GetUpdatesPerSecond() {
        return UPS;
    }
    public static boolean GetApplyOffset() {
        return applyOffset;
    }
    public static boolean GetFollowBody() {
        return followBody;
    }
    public static String GetBodyCenter() {
        return bodyCenter;
    }
    public static boolean GetDrawDistLines() {
        return drawDistLines;
    }
    public static boolean GetDrawNames() {
        return drawNames;
    }


}
