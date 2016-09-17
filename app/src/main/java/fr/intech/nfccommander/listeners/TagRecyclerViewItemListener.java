package fr.intech.nfccommander.listeners;

import android.view.View;

import fr.intech.nfccommander.activities.MainActivity;

/**
 * Listenr for tags RecyclerView item
 */
public class TagRecyclerViewItemListener implements View.OnClickListener {

    /**
     * Main activity
     */
    private MainActivity mainActivity;

    /**
     * Tag ID associated with RecyclerView item
     */
    private String tagID;

    public TagRecyclerViewItemListener(MainActivity mainActivity, String tagID) {
        this.mainActivity = mainActivity;
        this.tagID = tagID;
    }

    @Override
    public void onClick(View view) {
        mainActivity.tryToStartCommanderFragment(tagID);
    }
}
