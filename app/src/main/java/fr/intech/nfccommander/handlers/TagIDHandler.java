package fr.intech.nfccommander.handlers;

import java.math.BigInteger;

/**
 * Handler for tag ID conversion
 */
public class TagIDHandler {

    /**
     * Get the string representation of the native tag ID in bytes
     * @param tagID     the native tag ID in bytes
     * @return          the string tag ID
     */
    public static String getStringID(byte[] tagID) {
        return String.format("%0" + (tagID.length * 2) + "X", new BigInteger(1, tagID));
    }
}
