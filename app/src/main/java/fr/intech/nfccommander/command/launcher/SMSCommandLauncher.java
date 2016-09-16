package fr.intech.nfccommander.command.launcher;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import fr.intech.nfccommander.handlers.TagIOHandler;

public class SMSCommandLauncher implements ICommandLauncher {

    @Override
    public void launch(Context context, String text) {
        String phone = text.substring(0, text.indexOf(TagIOHandler.SEPARATOR));
        String message = text.substring(text.indexOf(TagIOHandler.SEPARATOR) + 1);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
        intent.putExtra("sms_body", message);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        context.startActivity(intent);
    }
}
