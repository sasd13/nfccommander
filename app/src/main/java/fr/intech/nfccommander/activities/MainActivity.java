package fr.intech.nfccommander.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.intech.nfccommander.EnumCommanderType;
import fr.intech.nfccommander.ICommander;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.fragments.TagsFragment;
import fr.intech.nfccommander.activities.fragments.commander.CommanderFragmentFactory;

public class MainActivity extends AppCompatActivity {

    private List<Tag> tags;
    private Tag linkedTag;
    private ICommander commander;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tags = new ArrayList<>();

        listTagsFragment();
    }

    private void listTagsFragment() {
        if (menuItem != null) {
            menuItem.setVisible(false);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, TagsFragment.newInstance())
                .commit();
    }

    public void startCommanderFragment(EnumCommanderType type, String tagId) {
        if (menuItem != null) {
            menuItem.setVisible(true);
        }

        commander = CommanderFragmentFactory.make(type);
        linkedTag = findTagById(tagId.getBytes());

        if (linkedTag == null) {
            Toast.makeText(this, R.string.fragment_tags_error_tag_not_connected, Toast.LENGTH_SHORT).show();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_fragment_container, (Fragment) commander)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private Tag findTagById(byte[] tagId) {
        for (Tag tag : tags) {
            if (Arrays.equals(tag.getId(), tagId)) {
                return tag;
            }
        }

        return null;
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
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            if (findTagById(tag.getId()) == null) {
                tags.add(tag);
                //TODO : save tagId to SharedPreferences
            }
        }
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
        commander.command(linkedTag);
    }
}
