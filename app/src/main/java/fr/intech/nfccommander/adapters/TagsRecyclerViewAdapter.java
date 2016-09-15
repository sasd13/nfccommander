package fr.intech.nfccommander.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.intech.nfccommander.R;

public class TagsRecyclerViewAdapter extends RecyclerView.Adapter {

    private static class TagViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public TagViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.recyclerview_item_tag_textview);
        }
    }

    private List<String> tags;

    public TagsRecyclerViewAdapter(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_tag, parent, false);

        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagViewHolder tagViewHolder = (TagViewHolder) holder;

        tagViewHolder.textView.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }
}
