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

        private View itemView;
        private TextView textView;

        public TagViewHolder(View view) {
            super(view);

            itemView = view;
            textView = (TextView) view.findViewById(R.id.recyclerview_item_tag_textview);
        }
    }

    private List<String> tagsIds;
    private MainActivity mainActivity;

    public TagsRecyclerViewAdapter(MainActivity mainActivity, List<String> tagsIds) {
        this.mainActivity = mainActivity;
        this.tagsIds = tagsIds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_tag, parent, false);

        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagViewHolder tagViewHolder = (TagViewHolder) holder;

        tagViewHolder.textView.setText(tagsIds.get(position));
        tagViewHolder.itemView.setOnClickListener(new TagRecyclerViewItemListener(mainActivity, tagsIds.get(position)));
    }

    @Override
    public int getItemCount() {
        return tagsIds.size();
    }
}
