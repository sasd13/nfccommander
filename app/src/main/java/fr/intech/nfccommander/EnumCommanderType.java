package fr.intech.nfccommander;

/**
 * Created by ssaidali2 on 15/09/2016.
 */
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
