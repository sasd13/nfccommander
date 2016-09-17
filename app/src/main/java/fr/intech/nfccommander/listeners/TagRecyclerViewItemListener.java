package fr.intech.nfccommander.listeners;

import android.view.View;

import fr.intech.nfccommander.activities.MainActivity;

public class TagRecyclerViewItemListener implements View.OnClickListener {

    private MainActivity mainActivity;
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
