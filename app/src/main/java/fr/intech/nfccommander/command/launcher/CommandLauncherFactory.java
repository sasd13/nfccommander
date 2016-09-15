package fr.intech.nfccommander.command.launcher;

import fr.intech.nfccommander.command.EnumCommanderType;

public class CommandLauncherFactory {

    private CommandLauncherFactory() {}

    public static ICommandLauncher make(EnumCommanderType type) {
        switch (type) {
            case PHONE: return new PhoneCommandLauncher();
            case SMS: return new SMSCommandLauncher();
            case APP: return new AppCommandLauncher();
            default: return null;
        }
    }
}
