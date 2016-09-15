package fr.intech.nfccommander.activities.fragments.commander;

import fr.intech.nfccommander.EnumCommanderType;
import fr.intech.nfccommander.ICommander;

/**
 * Created by ssaidali2 on 15/09/2016.
 */
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
