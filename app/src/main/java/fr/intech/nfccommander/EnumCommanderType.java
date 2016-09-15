package fr.intech.nfccommander;

public enum  EnumCommanderType {
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
}
