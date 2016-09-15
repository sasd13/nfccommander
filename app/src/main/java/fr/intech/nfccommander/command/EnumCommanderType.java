package fr.intech.nfccommander.command;

public enum EnumCommanderType {
    PHONE("PHONE"),
    SMS("SMS"),
    APP("APP"),
    ;

    private String code;

    private EnumCommanderType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static EnumCommanderType find(String code) {
        for (EnumCommanderType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }

        return null;
    }
}
