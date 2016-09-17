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

public class MainActivity extends AppCompatActivity {

    private static final String SEPARATOR = "#";

    private List<Tag> linkedTags;
    private Tag chosenTag;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkedTags = new ArrayList<>();
        parentView = findViewById(android.R.id.content);

        startTagsListFragment();
    }

    private void startTagsListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, TagsListFragment.newInstance(linkedTags))
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, intent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (NfcAdapter.getDefaultAdapter(this) != null) {
            NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        processIntent(getIntent());
    }

    private void processIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            chosenTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            saveTagIfNotListed();
            readTag();
        }
    }

    private void saveTagIfNotListed() {
        if (findTagByID(TagIDHandler.getStringID(chosenTag.getId())) == null) {
            linkedTags.add(chosenTag);

            if (TagPreferencesHandler.saveTagID(this, chosenTag)) {
                displaySnackbar(R.string.tag_saved);
            }
        }
    }

    private void displaySnackbar(@StringRes int message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
    }

    private Tag findTagByID(String tagID) {
        for (Tag tag : linkedTags) {
            if (TagIDHandler.getStringID(tag.getId()).equals(tagID)) {
                return tag;
            }
        }

        return null;
    }

    public void readTag() {
        new TagTaskReader(this, chosenTag).execute();
    }

    public void onReadTagSucceeded(String text) {
        if (text == null) {
            displaySnackbar(R.string.error_tag_no_message);
            return;
        }

        int indexOfSeparator = text.indexOf(SEPARATOR);

        if (indexOfSeparator >= 0) {
            EnumCommandType type = findCommandTypeInMessage(text, indexOfSeparator);

            if (type == null) {
                displaySnackbar(R.string.error_tag_command_unknown);
                return;
            }

            launchCommand(text, indexOfSeparator, type);
        }
    }

    private EnumCommandType findCommandTypeInMessage(String text, int indexOfSeparator) {
        String typeCode = text.substring(0, indexOfSeparator);

        return EnumCommandType.find(typeCode);
    }

    private void launchCommand(String text, int indexOfSeparator, EnumCommandType type) {
        String message = text.substring(indexOfSeparator + 1);
        Intent intent = CommandFactory.make(type).read(this, message);

        startActivity(intent);
    }

    public void onError(@StringRes int message) {
        displaySnackbar(message);
    }

    public void tryToStartCommanderFragment(String tagID) {
        chosenTag = findTagByID(tagID);

        if (chosenTag != null) {
            showCommanderChoiceDialog();
        } else {
            displaySnackbar(R.string.error_tag_not_linked);
        }
    }

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

    private void startCommanderFragment(EnumCommandType type) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, (Fragment) CommanderFactory.make(type))
                .addToBackStack(null)
                .commit();
    }

    public void writeTag(EnumCommandType type, ICommand command) {
        new TagTaskWriter(this, chosenTag).execute(type.getCode() + SEPARATOR + command.create());
    }

    public void onWriteTagSucceeded() {
        displaySnackbar(R.string.tag_written);
    }
}
