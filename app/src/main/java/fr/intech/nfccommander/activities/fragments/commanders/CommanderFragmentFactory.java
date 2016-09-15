package fr.intech.nfccommander.activities.fragments.commanders;

import fr.intech.nfccommander.command.EnumCommanderType;
import fr.intech.nfccommander.command.ICommander;

public class CommanderFragmentFactory {

    private CommanderFragmentFactory() {}

    public static ICommander make(EnumCommanderType type) {
        switch (type) {
            case PHONE: return PhoneCommanderFragment.newInstance();
            case SMS: return SMSCommanderFragment.newInstance();
            case APP: return AppCommanderFragment.newInstance();
            default: return null;
        }
    }
}
