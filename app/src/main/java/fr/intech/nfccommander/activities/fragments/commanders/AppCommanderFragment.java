package fr.intech.nfccommander.activities.fragments.commanders;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.command.ICommander;

public class AppCommanderFragment extends Fragment implements ICommander {

    public static AppCommanderFragment newInstance() {
        return new AppCommanderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commander_app, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {

    }

    @Override
    public void command(Tag tag) {

    }
}
