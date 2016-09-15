package fr.intech.nfccommander.adapters;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ssaidali2 on 15/09/2016.
 */
public class TagsRecyclerViewAdapter extends RecyclerView.Adapter {

    private static class TagViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public TagViewHolder(View view) {
            super(view);
        }
    }

    private List<Tag> tags;

    public TagsRecyclerViewAdapter(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(0, parent, false);

        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagViewHolder tagViewHolder = (TagViewHolder) holder;

        Tag tag = tags.get(position);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }
}
