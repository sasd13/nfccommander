package fr.intech.nfccommander.activities.fragments.commanders;

import fr.intech.nfccommander.EnumCommandType;

public class CommanderFactory {

    private CommanderFactory() {}

    public static ICommander make(EnumCommandType type) {
        switch (type) {
            case PHONE: return PhoneCommanderFragment.newInstance();
            case SMS: return SMSCommanderFragment.newInstance();
            case APP: return AppCommanderFragment.newInstance();
            default: return null;
        }
    }
}
