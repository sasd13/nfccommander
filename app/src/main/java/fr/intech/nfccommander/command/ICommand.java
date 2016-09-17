package fr.intech.nfccommander.command;

import android.content.Context;
import android.content.Intent;

/**
 * Command interface
 */
public interface ICommand {

    /**
     * Create the command message
     * @return      the command message
     */
    String create();

    /**
     * Create an intent from the command message
     * @param context   the application context
     * @param message   the command message
     * @return          the created intent
     */
    Intent read(Context context, String message);
}
