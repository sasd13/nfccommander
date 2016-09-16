package fr.intech.nfccommander.command;

import android.content.Context;
import android.content.Intent;

public interface ICommand {

    String create();

    Intent read(Context context, String message);
}
