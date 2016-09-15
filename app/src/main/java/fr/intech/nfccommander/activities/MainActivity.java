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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.fragments.TagsFragment;
import fr.intech.nfccommander.activities.fragments.commanders.CommanderFragmentFactory;
import fr.intech.nfccommander.command.EnumCommanderType;
import fr.intech.nfccommander.command.ICommander;
import fr.intech.nfccommander.handlers.TagCommandHandler;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_KEY_TAGS = "tags_ids";

    private List<String> linkedTags;
    private Tag chosenTag;
    private ICommander commander;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkedTags = new ArrayList<>();

        Set<String> savedTags = PreferenceManager.getDefaultSharedPreferences(this).getStringSet(PREFERENCES_KEY_TAGS, null);
        if (savedTags != null) {
            linkedTags.addAll(Arrays.asList(savedTags.toArray(new String[savedTags.size()])));
        }

        listTagsFragment();
    }

    private void listTagsFragment() {
        if (menuItem != null) {
            menuItem.setVisible(false);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, TagsFragment.newInstance(linkedTags))
                .commit();
    }

    public void startCommanderFragment(EnumCommanderType type) {
        if (menuItem != null) {
            menuItem.setVisible(true);
        }

        commander = CommanderFragmentFactory.make(type);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, (Fragment) commander)
                .addToBackStack(null)
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
            launchCommand();
        }
    }

    private void saveTagIfNotListed() {
        String id = new String(chosenTag.getId(), StandardCharsets.UTF_8);

        if (!linkedTags.contains(id)) {
            linkedTags.add(id);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putStringSet(PREFERENCES_KEY_TAGS, new HashSet<>(linkedTags));
            editor.apply();

            Toast.makeText(this, R.string.tag_saved, Toast.LENGTH_SHORT).show();
        }
    }

    private void launchCommand() {
        TagCommandHandler.launchCommandOrShowCommanders(chosenTag, this);
    }

    public void showCommandersDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.fragment_tags_dialog_commander);
        builder.setItems(getResources().getStringArray(R.array.commanders), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startCommanderFragment(EnumCommanderType.values()[i]);
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean prepared = super.onPrepareOptionsMenu(menu);

        menuItem = menu.findItem(R.id.activity_main_menu_done);

        return prepared;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.activity_main_menu_done:
                saveCommand();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void saveCommand() {
        commander.command(chosenTag);
    }

    @Override
    public void onBackPressed() {
        if (commander != null) {
            commander = null;

            if (menuItem != null) {
                menuItem.setVisible(false);
            }
        }

        super.onBackPressed();
    }
}
