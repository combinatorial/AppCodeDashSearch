package de.dreamlab.dash;

public class SystemUtil {
    private static boolean isOSMac = false;
    private static boolean isOSWindows = false;
    private static boolean isOSLinux = false;

    static {
        try {
            String osName = System.getProperty("os.name").toLowerCase();

            if ( osName.startsWith("mac") ) {
                isOSMac = true;
            } else if ( osName.startsWith("windows") ) {
                isOSWindows = true;
            } else if ( osName.startsWith("linux") ) {
                isOSLinux = true;
            }
        }
        catch ( Throwable e ) {
        }
    }

    public static boolean isIsOSMac() {
        return isOSMac;
    }

    public static boolean isIsOSWindows() {
        return isOSWindows;
    }

    public static boolean isIsOSLinux() {
        return isOSLinux;
    }
}
