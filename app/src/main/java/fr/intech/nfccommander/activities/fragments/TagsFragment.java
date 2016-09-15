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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.adapters.TagsRecyclerViewAdapter;

public class TagsFragment extends Fragment {

    private static final String KEY_TAGSIDS = "tags_ids";

    private List<String> tagsIds;

    public static TagsFragment newInstance() {
        return new TagsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Set<String> ids = PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet(KEY_TAGSIDS, null);
        tagsIds.addAll(Arrays.asList(ids.toArray(new String[ids.size()])));
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new TagsRecyclerViewAdapter(tagsIds, (MainActivity) getActivity()));
    }
}
