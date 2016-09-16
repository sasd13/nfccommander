package fr.intech.nfccommander.command;

import android.content.Context;
import android.content.Intent;

public class AppCommand implements ICommand {

    private String appPackage;

    public AppCommand() {}

    public AppCommand(String appPackage) {
        this.appPackage = appPackage;
    }

    @Override
    public String create() {
        return appPackage;
    }

    @Override
    public Intent read(Context context, String message) {
        return context.getPackageManager().getLaunchIntentForPackage(message);
    }
}
