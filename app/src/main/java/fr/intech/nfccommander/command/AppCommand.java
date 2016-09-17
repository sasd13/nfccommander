package fr.intech.nfccommander.command;

import android.content.Context;
import android.content.Intent;

/**
 * App command
 */
public class AppCommand implements ICommand {

    /**
     * The app package name
     */
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
