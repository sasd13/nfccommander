package fr.intech.nfccommander.activities.fragments;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.adapters.TagsRecyclerViewAdapter;
import fr.intech.nfccommander.handlers.TagIDHandler;
import fr.intech.nfccommander.handlers.TagPreferencesHandler;

/**
 * Tags list fragment
 */
public class TagsListFragment extends Fragment {

    /**
     * The history of associated tags
     */
    private List<Tag> linkedTags;

    /**
     * The tags IDs displayed in the RecyclerView
     */
    private List<String> tagsIDs;

    /**
     * The adapter of the recyclerView
     */
    private TagsRecyclerViewAdapter adapter;

    public static TagsListFragment newInstance(List<Tag> linkedTags) {
        TagsListFragment tagsListFragment = new TagsListFragment();
        tagsListFragment.linkedTags = linkedTags;

        return tagsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tagsIDs = TagPreferencesHandler.loadSavedTagsIDs(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags_list, container, false);

        buildView(view);

        return view;
    }

    /**
     * Build the fragment view
     * @param view      The content view of the fragment
     */
    private void buildView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_tags_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TagsRecyclerViewAdapter((MainActivity) getActivity(), tagsIDs);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        addLinkedTagsIDsToList();
    }

    /**
     * Called by onResume to refresh the listed tags IDs when new tag was associated
     */
    private void addLinkedTagsIDsToList() {
        String tagID;

        for (Tag tag : linkedTags) {
            tagID = TagIDHandler.getStringID(tag.getId());

            if (!tagsIDs.contains(tagID)) {
                tagsIDs.add(tagID);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
