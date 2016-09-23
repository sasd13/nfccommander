package fr.intech.nfccommander.commands;

import fr.intech.nfccommander.activities.MainActivity;

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
     * Read the command message created
     * @param message       the command message to read
     * @param mainActivity  the main activity
     */
    void read(String message, MainActivity mainActivity);
}
