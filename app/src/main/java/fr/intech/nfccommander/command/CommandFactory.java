package fr.intech.nfccommander.command;

import fr.intech.nfccommander.EnumCommandType;

public class CommandFactory {

    private CommandFactory() {}

    public static ICommand make(EnumCommandType type) {
        switch (type) {
            case PHONE: return new PhoneCommand();
            case SMS: return new SMSCommand();
            case APP: return new AppCommand();
            default: return null;
        }
    }
}
