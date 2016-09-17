package fr.intech.nfccommander.handlers;

import java.math.BigInteger;

public class TagIDHandler {

    public static String getStringID(byte[] tagID) {
        return String.format("%0" + (tagID.length * 2) + "X", new BigInteger(1, tagID));
    }
}
