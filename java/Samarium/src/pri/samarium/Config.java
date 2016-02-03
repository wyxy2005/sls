package pri.samarium;

public class Config {
    private static boolean sPrintStackTrace = false;

    public static boolean isPrintStackTraceEnalbed() {
        return sPrintStackTrace;
    }

    public static void enablePrintStackTrace() {
        sPrintStackTrace = true;
    }

    public static void disablePrintStackTrace() {
        sPrintStackTrace = false;
    }

}
