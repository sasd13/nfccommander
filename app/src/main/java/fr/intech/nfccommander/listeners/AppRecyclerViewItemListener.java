package fr.intech.nfccommander.listeners;

import android.content.pm.ApplicationInfo;
import android.view.View;

import fr.intech.nfccommander.activities.fragments.commanders.AppCommanderFragment;

public class AppRecyclerViewItemListener implements View.OnClickListener {

    private AppCommanderFragment appCommanderFragment;
    private ApplicationInfo app;

    public AppRecyclerViewItemListener(AppCommanderFragment appCommanderFragment, ApplicationInfo app) {
        this.appCommanderFragment = appCommanderFragment;
        this.app = app;
    }

    @Override
    public void onClick(View view) {
        appCommanderFragment.setChosenApp(app);
        appCommanderFragment.saveCommand();
    }
}
