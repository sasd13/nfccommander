package fr.intech.nfccommander.adapters;

import android.view.View;

import fr.intech.nfccommander.activities.MainActivity;

public class TagRecyclerViewItemListener implements View.OnClickListener {

    private MainActivity mainActivity;
    private String tagId;

    public TagRecyclerViewItemListener(MainActivity mainActivity, String tagId) {
        this.mainActivity = mainActivity;
        this.tagId = tagId;
    }

    @Override
    public void onClick(View view) {
        mainActivity.showCommandersDialog(tagId);
    }
}
