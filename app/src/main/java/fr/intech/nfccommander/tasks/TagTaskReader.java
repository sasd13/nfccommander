package fr.intech.nfccommander.tasks;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.handlers.TagIOHandler;

public class TagTaskReader extends AsyncTask<Void, Intent, String> {

    private MainActivity mainActivity;
    private Tag tag;
    private boolean readed;

    public TagTaskReader(MainActivity mainActivity, Tag tag) {
        this.mainActivity = mainActivity;
        this.tag = tag;
    }

    @Override
    protected String doInBackground(Void... aVoids) {
        String text = null;

        if (!isCancelled()) {
            try {
                text = TagIOHandler.read(tag);
                readed = true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    @Override
    protected void onPostExecute(String text) {
        super.onPostExecute(text);

        if (readed) {
            mainActivity.onReadTagSucceeded(text);
        } else {
            mainActivity.onError(R.string.error_tag_reading);
        }
    }
}
