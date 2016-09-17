package fr.intech.nfccommander.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.listeners.TagRecyclerViewItemListener;

/**
 * Adapter for tags RecyclerView
 */
public class TagsRecyclerViewAdapter extends RecyclerView.Adapter {

    private static class TagViewHolder extends RecyclerView.ViewHolder {

        private View itemViewRoot;
        private TextView itemTextView;

        public TagViewHolder(View view) {
            super(view);

            itemViewRoot = view;
            itemTextView = (TextView) view.findViewById(R.id.recyclerview_item_textview);
        }
    }

    /**
     * Main activity
     */
    private MainActivity mainActivity;

    /**
     * The tags IDs to list in the RecyclerView
     */
    private List<String> tagsIDs;

    public TagsRecyclerViewAdapter(MainActivity mainActivity, List<String> tagsIDs) {
        this.mainActivity = mainActivity;
        this.tagsIDs = tagsIDs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagViewHolder tagViewHolder = (TagViewHolder) holder;

        tagViewHolder.itemTextView.setText(tagsIDs.get(position));
        tagViewHolder.itemViewRoot.setOnClickListener(new TagRecyclerViewItemListener(mainActivity, tagsIDs.get(position)));
    }

    @Override
    public int getItemCount() {
        return tagsIDs.size();
    }
}
