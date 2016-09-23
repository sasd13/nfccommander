package fr.intech.nfccommander.tasks;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.handlers.TagIOHandler;

/**
 * Tag reading AsyncTask
 */
public class TagTaskReader extends AsyncTask<Void, Intent, String> {

    /**
     * The tag to read
     */
    private Tag tag;

    /**
     * Main activity
     */
    private MainActivity mainActivity;

    /**
     * The flag if the reading is performed
     */
    private boolean readed;

    public TagTaskReader(Tag tag, MainActivity mainActivity) {
        this.tag = tag;
        this.mainActivity = mainActivity;
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
