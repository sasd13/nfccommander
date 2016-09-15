package fr.intech.nfccommander.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import fr.intech.nfccommander.EnumCommanderType;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;

public class TagRecyclerViewItemListener implements View.OnClickListener {

    private String tagId;
    private MainActivity mainActivity;

    public TagRecyclerViewItemListener(String tagId, MainActivity mainActivity) {
        this.tagId = tagId;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.fragment_tags_dialog_commander);
        builder.setItems(view.getResources().getStringArray(R.array.commanders), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainActivity.startCommanderFragment(EnumCommanderType.values()[i], tagId);
            }
        });
        builder.create().show();
    }
}
