package fr.intech.nfccommander.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;

public class TagsRecyclerViewAdapter extends RecyclerView.Adapter {

    private static class TagViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public TagViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.recyclerview_item_tag_textview);
        }
    }

    private List<String> tagsIds;
    private MainActivity mainActivity;

    public TagsRecyclerViewAdapter(List<String> tagsIds, MainActivity mainActivity) {
        this.tagsIds = tagsIds;
        this.mainActivity = mainActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_tag, parent, false);

        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagViewHolder tagViewHolder = (TagViewHolder) holder;

        String tagId = tagsIds.get(position);

        tagViewHolder.textView.setText(new String(tagId));
        tagViewHolder.textView.setOnClickListener(new TagRecyclerViewItemListener(tagId, mainActivity));
    }

    @Override
    public int getItemCount() {
        return tagsIds.size();
    }
}
