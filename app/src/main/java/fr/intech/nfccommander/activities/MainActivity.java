package fr.intech.nfccommander.activities;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.intech.nfccommander.EnumCommandType;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.fragments.TagsListFragment;
import fr.intech.nfccommander.activities.fragments.commanders.CommanderFactory;
import fr.intech.nfccommander.command.CommandFactory;
import fr.intech.nfccommander.command.ICommand;
import fr.intech.nfccommander.handlers.TagIDHandler;
import fr.intech.nfccommander.handlers.TagPreferencesHandler;
import fr.intech.nfccommander.tasks.TagTaskReader;
import fr.intech.nfccommander.tasks.TagTaskWriter;

/**
 * Application main activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Separator used in the tag message to separe
     * the command type code and the command command message
     */
    private static final String SEPARATOR = "#";

    /**
     * History of associated tags since Application start
     */
    private List<Tag> linkedTags;

    /**
     * Current associated tag
     */
    private Tag chosenTag;

    /**
     * Activity content view, used to display Snackbar
     */
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkedTags = new ArrayList<>();
        contentView = findViewById(android.R.id.content);

        startTagsListFragment();
    }

    /**
     * Start the fragment for list of tags
     */
    private void startTagsListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, TagsListFragment.newInstance(linkedTags))
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatch();
    }

    /**
     * Enable tag foreground dispatch when activity is resumed
     */
    private void enableForegroundDispatch() {
        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, intent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatch();
    }

    /**
     * Disable tag foreground dispatch when activity is paused
     */
    private void disableForegroundDispatch() {
        if (NfcAdapter.getDefaultAdapter(this) != null) {
            NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        processIntent(getIntent());
    }

    /**
     * Process new intents
     * @param intent    the intent to proceed
     */
    private void processIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            chosenTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            saveTagIfNotListed();
            readTag();
        }
    }

    /**
     * Save new tags in preferences if they had never been associated
     */
    private void saveTagIfNotListed() {
        if (findTagByID(TagIDHandler.getStringID(chosenTag.getId())) == null) {
            linkedTags.add(chosenTag);

            if (TagPreferencesHandler.saveTagID(this, chosenTag)) {
                displaySnackbar(R.string.tag_saved);
            }
        }
    }

    /**
     * Display messages in Snackbar
     * @param message   the message ResID to display
     */
    private void displaySnackbar(@StringRes int message) {
        Snackbar.make(contentView, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Find tag in associated tags history
     * @param tagID     the ID of the tag
     * @return          the tag if listed or null if not
     */
    private Tag findTagByID(String tagID) {
        for (Tag tag : linkedTags) {
            if (TagIDHandler.getStringID(tag.getId()).equals(tagID)) {
                return tag;
            }
        }

        return null;
    }

    /**
     * Read message from the current associated tag
     */
    public void readTag() {
        new TagTaskReader(this, chosenTag).execute();
    }

    /**
     * Called by the AsyncTask for reading tag on its onPostExecute method
     * when the reading is succeeded
     * @param tagMessage    the text message readead from the tag
     */
    public void onReadTagSucceeded(String tagMessage) {
        if (tagMessage == null) {
            displaySnackbar(R.string.error_tag_no_message);
            return;
        }

        int indexOfSeparator = tagMessage.indexOf(SEPARATOR);

        if (indexOfSeparator >= 0) {
            EnumCommandType commandType = findCommandTypeInMessage(tagMessage, indexOfSeparator);

            if (commandType == null) {
                displaySnackbar(R.string.error_tag_command_unknown);
                return;
            }

            String commandMessage = tagMessage.substring(indexOfSeparator + 1);

            launchCommand(commandType, commandMessage);
        }
    }

    /**
     * Find command type in tag readed message
     * @param tagMessage        the tag message
     * @param indexOfSeparator  the index of the separator
     * @return                  the command type if found or null if not
     */
    private EnumCommandType findCommandTypeInMessage(String tagMessage, int indexOfSeparator) {
        String typeCode = tagMessage.substring(0, indexOfSeparator);

        return EnumCommandType.find(typeCode);
    }

    /**
     * Execute the command in the tag message
     * @param type      the command type
     * @param message   the command message
     */
    private void launchCommand(EnumCommandType type, String message) {
        Intent intent = CommandFactory.make(type).read(this, message);

        startActivity(intent);
    }

    /**
     * Called by classes out the MainActivity scope to display error messages
     * @param message   the error message to display
     */
    public void onError(@StringRes int message) {
        displaySnackbar(message);
    }

    /**
     * Called when user click on tag item in the tags list fromTagsListFragment
     * @param tagID     the ID of the clicked tag item
     */
    public void tryToStartCommanderFragment(String tagID) {
        chosenTag = findTagByID(tagID);

        if (chosenTag != null) {
            showCommanderChoiceDialog();
        } else {
            displaySnackbar(R.string.error_tag_not_linked);
        }
    }

    /**
     * Show dialog box to chose a commander (Phone, SMS, App...)
     */
    private void showCommanderChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_commanders);
        builder.setItems(getResources().getStringArray(R.array.commanders), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startCommanderFragment(EnumCommandType.values()[i]);
            }
        });
        builder.create().show();
    }

    /**
     * Start the chosen commander fragment
     * @param type  the type of the chosen commander
     */
    private void startCommanderFragment(EnumCommandType type) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, (Fragment) CommanderFactory.make(type))
                .addToBackStack(null)
                .commit();
    }

    /**
     * Called by the command fragment to write message in the current associated tag
     * @param type      the type of the command
     * @param command   the command of the commander
     */
    public void writeTag(EnumCommandType type, ICommand command) {
        String tagMessage = type.getCode() + SEPARATOR + command.create();

        new TagTaskWriter(this, chosenTag).execute(tagMessage);
    }

    /**
     * Called by the AsyncTask for writing tag on its onPostExecute method
     * when the writing is succeeded
     */
    public void onWriteTagSucceeded() {
        displaySnackbar(R.string.tag_written);
    }
}
