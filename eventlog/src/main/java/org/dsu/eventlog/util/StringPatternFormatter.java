package org.dsu.eventlog.util;

public final class StringPatternFormatter {
    
    private StringPatternFormatter() {
        
    }
    
    public static String argNotBeNull(String argName) {
        return String.format("The argument '%s' must not be null.", argName);
    }

}
