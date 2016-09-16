package fr.intech.nfccommander.tasks;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.handlers.TagIOHandler;

public class TagTaskReader extends AsyncTask<Tag, Intent, String> {

    private MainActivity mainActivity;

    public TagTaskReader(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Tag... tags) {
        String text = null;

        if (!isCancelled()) {
            try {
                text = TagIOHandler.read(tags[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                mainActivity.onError(R.string.error_tag_reading);
            }
        }

        return text;
    }

    @Override
    protected void onPostExecute(String text) {
        super.onPostExecute(text);

        mainActivity.onReadTagSucceeded(text);
    }
}
