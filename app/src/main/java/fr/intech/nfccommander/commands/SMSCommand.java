package fr.intech.nfccommander.commands;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import fr.intech.nfccommander.activities.MainActivity;

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
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber);
        builder.append(SEPARATOR);
        builder.append(smsMessage);

        return builder.toString();
    }

    @Override
    public void read(String message, MainActivity mainActivity) {
        int indexOfSeparator =  message.indexOf(SEPARATOR);
        String phoneNumber = message.substring(0, indexOfSeparator);
        String smsMessage = message.substring(indexOfSeparator + 1);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", smsMessage);

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mainActivity.startActivity(intent);
    }
}
