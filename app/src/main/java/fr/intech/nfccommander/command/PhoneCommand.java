package fr.intech.nfccommander.command;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneCommand implements ICommand {

    private static final String SEPARATOR = "#";

    private String phoneNumber;

    public PhoneCommand() {}

    public PhoneCommand(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String create() {
        return phoneNumber;
    }

    @Override
    public Intent read(Context context, String message) {
        return new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + message));
    }
}
