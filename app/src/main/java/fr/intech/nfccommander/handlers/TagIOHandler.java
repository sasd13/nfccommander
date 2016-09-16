package fr.intech.nfccommander.handlers;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class TagIOHandler {

    public static String read(Tag tag) throws UnsupportedEncodingException {
        Ndef ndef = Ndef.get(tag);
        if (ndef != null) {
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
            NdefRecord[] ndefRecords = ndefMessage.getRecords();

            for (NdefRecord ndefRecord : ndefRecords) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    return readRecord(ndefRecord);
                }
            }
        }

        return null;
    }

    private static String readRecord(NdefRecord ndefRecord) throws UnsupportedEncodingException {
        byte[] payload = ndefRecord.getPayload();
        String textEnconding = (payload[0] & 128) == 0 ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEnconding);
    }

    public static void write(Tag tag, String text) throws UnsupportedEncodingException, FormatException, IOException {
        NdefRecord[] records = new NdefRecord[]{ createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    private static NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        payload[0] = (byte) langLength;
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
    }
}
