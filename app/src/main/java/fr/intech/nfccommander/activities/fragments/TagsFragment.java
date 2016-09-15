package fr.intech.nfccommander.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.adapters.TagsRecyclerViewAdapter;

public class TagsFragment extends Fragment {

    private List<String> tags;

    public static TagsFragment newInstance(List<String> tags) {
        TagsFragment tagsFragment = new TagsFragment();
        tagsFragment.tags = tags;

        return tagsFragment;
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
        recyclerView.setAdapter(new TagsRecyclerViewAdapter(tags));
    }
}
