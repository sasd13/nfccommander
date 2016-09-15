package fr.intech.nfccommander.command.launcher;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

public class PhoneCommandLauncher implements ICommandLauncher {

    @Override
    public void launch(String text, Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + text));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        context.startActivity(intent);
    }
}
