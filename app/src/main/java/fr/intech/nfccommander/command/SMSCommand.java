package fr.intech.nfccommander.command;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * SMS command
 */
public class SMSCommand implements ICommand {

    private static final String SEPARATOR = "#";

    /**
     * The phone number
     */
    private String phoneNumber;

    /**
     * The SMS message
     */
    private String smsMessage;

    public SMSCommand() {}

    public SMSCommand(String phoneNumber, String smsMessage) {
        this.phoneNumber = phoneNumber;
        this.smsMessage = smsMessage;
    }

    @Override
    public String create() {
        return phoneNumber + SEPARATOR + smsMessage;
    }

    @Override
    public Intent read(Context context, String text) {
        String phoneNumber = text.substring(0, text.indexOf(SEPARATOR));
        String smsMessage = text.substring(text.indexOf(SEPARATOR) + 1);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", smsMessage);

        return intent;
    }
}
