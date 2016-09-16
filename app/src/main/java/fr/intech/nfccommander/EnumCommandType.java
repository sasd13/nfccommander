package fr.intech.nfccommander;

public enum EnumCommandType {
    PHONE("PHONE"),
    SMS("SMS"),
    APP("APP"),
    ;

    private String code;

    private EnumCommandType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static EnumCommandType find(String code) {
        for (EnumCommandType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }

        return null;
    }
}
