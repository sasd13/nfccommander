package fr.intech.nfccommander.activities.fragments.commander;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.intech.nfccommander.R;

public class TagsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {

    }
}
