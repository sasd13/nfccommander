package fr.intech.nfccommander.activities.fragments.commanders;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.intech.nfccommander.EnumCommandType;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.adapters.AppsRecyclerViewAdapter;
import fr.intech.nfccommander.command.AppCommand;

public class AppCommanderFragment extends Fragment implements ICommander {

    private MainActivity mainActivity;
    private List<ApplicationInfo> apps;
    private ApplicationInfo chosenApp;

    public static AppCommanderFragment newInstance() {
        return new AppCommanderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        apps = new ArrayList<>();

        findAppsWithLauncher();
    }

    private void findAppsWithLauncher() {
        PackageManager packageManager = getContext().getPackageManager();
        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : list) {
            if (packageManager.getLaunchIntentForPackage(app.packageName) != null) {
                apps.add(app);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commander_app, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_commander_app_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new AppsRecyclerViewAdapter(this, apps));
    }

    public void setChosenApp(ApplicationInfo chosenApp) {
        this.chosenApp = chosenApp;
    }

    @Override
    public void saveCommand() {
        mainActivity.writeTag(EnumCommandType.APP, new AppCommand(chosenApp.packageName));
    }
}
