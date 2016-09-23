package fr.intech.nfccommander.commands;

import android.content.Intent;

import fr.intech.nfccommander.activities.MainActivity;

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
    public void read(String message, MainActivity mainActivity) {
        Intent intent = mainActivity.getPackageManager().getLaunchIntentForPackage(message);

        mainActivity.startActivity(intent);
    }
}
