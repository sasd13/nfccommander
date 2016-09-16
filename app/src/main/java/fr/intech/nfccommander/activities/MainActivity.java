package fr.intech.nfccommander.activities;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.intech.nfccommander.EnumCommandType;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.fragments.TagsListFragment;
import fr.intech.nfccommander.activities.fragments.commanders.CommanderFactory;
import fr.intech.nfccommander.command.CommandFactory;
import fr.intech.nfccommander.command.ICommand;
import fr.intech.nfccommander.tasks.TagTaskReader;
import fr.intech.nfccommander.tasks.TagTaskWriter;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_KEY_TAGS = "tags_ids";
    private static final String SEPARATOR = "#";

    private List<String> linkedTags;
    private Tag chosenTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkedTags = new ArrayList<>();

        loadSavedTags();
        startTagsListFragment();
    }

    private void loadSavedTags() {
        Set<String> savedTags = PreferenceManager.getDefaultSharedPreferences(this).getStringSet(PREFERENCES_KEY_TAGS, null);

        if (savedTags != null) {
            linkedTags.addAll(Arrays.asList(savedTags.toArray(new String[savedTags.size()])));
        }
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
        String id = new String(chosenTag.getId(), StandardCharsets.UTF_8);

        if (!linkedTags.contains(id)) {
            linkedTags.add(id);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putStringSet(PREFERENCES_KEY_TAGS, new HashSet<>(linkedTags));
            editor.apply();

            displayToast(R.string.tag_saved);
        }
    }

    public void showCommandersDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.fragment_tags_dialog_commander);
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

    public void readTag() {
        new TagTaskReader(this, chosenTag).execute();
    }

    public void writeTag(EnumCommandType type, ICommand command) {
        new TagTaskWriter(this, chosenTag).execute(type.getCode() + SEPARATOR + command.create());
    }

    public void onReadTagSucceeded(String text) {
        if (text == null) {
            displayToast(R.string.error_tag_reading);
            return;
        }

        int indexOfSeparator = text.indexOf(SEPARATOR);

        if (indexOfSeparator >= 0) {
            String code = text.substring(0, indexOfSeparator);
            EnumCommandType type = EnumCommandType.find(code);

            if (type == null) {
                displayToast(R.string.error_tag_reading);
                return;
            }

            String message = text.substring(indexOfSeparator + 1);
            CommandFactory.make(type).read(this, message);
        }
    }

    public void onWriteTagSucceeded() {
        displayToast(R.string.tag_writed);
    }

    public void displayToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
