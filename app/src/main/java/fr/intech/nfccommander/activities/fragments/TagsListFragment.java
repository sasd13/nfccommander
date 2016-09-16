package fr.intech.nfccommander.activities.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.adapters.TagsRecyclerViewAdapter;

public class TagsListFragment extends Fragment {



    private List<String> tagsIds;

    public static TagsListFragment newInstance() {
        return new TagsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tagsIds = new ArrayList<>();

        Set<String> savedTags = PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet(PREFERENCES_KEY_TAGS, null);
        if (savedTags != null) {
            tagsIds.addAll(Arrays.asList(savedTags.toArray(new String[savedTags.size()])));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_tags_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TagsRecyclerViewAdapter((MainActivity) getActivity(), tagsIds));
    }
}
