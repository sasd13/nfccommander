package fr.intech.nfccommander.commands;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import fr.intech.nfccommander.activities.MainActivity;

/**
 * Phone command
 */
public class PhoneCommand implements ICommand {

    /**
     * The phone number
     */
    private String phoneNumber;

    public PhoneCommand() {
    }

    public PhoneCommand(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String create() {
        return phoneNumber;
    }

    @Override
    public void read(String message, MainActivity mainActivity) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + message));

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
