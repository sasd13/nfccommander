package fr.intech.nfccommander;

/**
 * Enum for the types of command
 */
public enum EnumCommandType {
    PHONE("PHONE"),
    SMS("SMS"),
    APP("APP"),
    ;

    /**
     * Code of the command type, added in the tag message
     */
    private String code;

    private EnumCommandType(String code) {
        this.code = code;
    }

    /**
     * The code of the command type
     * @return      the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Find the command type by code
     * @param code      the code of the command type to find
     * @return          the command type found or null if not
     */
    public static EnumCommandType find(String code) {
        for (EnumCommandType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }

        return null;
    }
}
