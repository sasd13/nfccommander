package fr.intech.nfccommander.tasks;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.Tag;
import android.os.AsyncTask;

import java.io.IOException;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.handlers.TagIOHandler;

public class TagTaskWriter extends AsyncTask<String, Intent, Void> {

    private MainActivity mainActivity;
    private Tag tag;

    public TagTaskWriter(MainActivity mainActivity, Tag tag) {
        this.mainActivity = mainActivity;
        this.tag = tag;
    }

    @Override
    protected Void doInBackground(String... texts) {
        if (!isCancelled()) {
            try {
                TagIOHandler.write(tag, texts[0]);
            } catch (FormatException | IOException e) {
                e.printStackTrace();
                mainActivity.displayToast(R.string.error_tag_writing);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mainActivity.onWriteTagSucceeded();
    }
}
