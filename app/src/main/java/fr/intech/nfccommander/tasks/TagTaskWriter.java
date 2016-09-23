package fr.intech.nfccommander.tasks;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.Tag;
import android.os.AsyncTask;

import java.io.IOException;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.handlers.TagIOHandler;

/**
 * Tag writing AsyncTask
 */
public class TagTaskWriter extends AsyncTask<String, Intent, Void> {

    private static final int ERROR_FORMAT = 1;
    private static final int ERROR_IO = 2;

    /**
     * The tag to write
     */
    private Tag tag;

    /**
     * Main activity
     */
    private MainActivity mainActivity;

    /**
     * The flag if the writing is performed
     */
    private boolean written;

    /**
     * The flag of error type
     */
    private int typeError;

    public TagTaskWriter(Tag tag, MainActivity mainActivity) {
        this.tag = tag;
        this.mainActivity = mainActivity;
    }

    @Override
    protected Void doInBackground(String... texts) {
        if (!isCancelled()) {
            try {
                TagIOHandler.write(tag, texts[0]);
                written = true;
            } catch (FormatException e) {
                typeError = ERROR_FORMAT;
                e.printStackTrace();
            } catch (IOException e) {
                typeError = ERROR_IO;
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (written) {
            mainActivity.onWriteTagSucceeded();
        } else {
            switch (typeError) {
                case ERROR_FORMAT:
                    mainActivity.onError(R.string.error_tag_writing);
                    break;
                case ERROR_IO:
                    mainActivity.onError(R.string.error_tag_not_connected);
                    break;
            }
        }
    }
}
