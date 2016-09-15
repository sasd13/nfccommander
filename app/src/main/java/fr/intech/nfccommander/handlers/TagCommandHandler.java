package fr.intech.nfccommander.handlers;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.widget.Toast;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.command.EnumCommanderType;
import fr.intech.nfccommander.command.launcher.CommandLauncherFactory;
import fr.intech.nfccommander.command.launcher.ICommandLauncher;

public class TagCommandHandler {

    public static void launchCommandOrShowCommanders(Tag tag, final MainActivity mainActivity) {
        new AsyncTask<Tag, Integer, String>() {

            @Override
            protected String doInBackground(Tag... tags) {
                return TagIOHandler.read(tags[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                String code = s.substring(0, s.indexOf(TagIOHandler.SEPARATOR));

                if (!code.isEmpty()) {
                    launchCommand(EnumCommanderType.find(code), s.substring(s.indexOf(TagIOHandler.SEPARATOR)), mainActivity);
                } else {
                    mainActivity.showCommandersDialog();
                }
            }
        }.execute(tag);
    }

    private static void launchCommand(EnumCommanderType type, String text, Context context) {
        ICommandLauncher commandLauncher = CommandLauncherFactory.make(type);

        if (commandLauncher != null) {
            commandLauncher.launch(text, context);
        } else {
            Toast.makeText(context, R.string.error_tag_launch_command, Toast.LENGTH_SHORT).show();
        }
    }
}
