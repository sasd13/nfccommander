package fr.intech.nfccommander.adapters;

import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.fragments.commanders.AppCommanderFragment;
import fr.intech.nfccommander.listeners.AppRecyclerViewItemListener;

public class AppsRecyclerViewAdapter extends RecyclerView.Adapter {

    private static class AppViewHolder extends RecyclerView.ViewHolder {

        private View itemViewRoot;
        private TextView itemTextView;

        public AppViewHolder(View view) {
            super(view);

            itemViewRoot = view;
            itemTextView = (TextView) view.findViewById(R.id.recyclerview_item_textview);
        }
    }

    private AppCommanderFragment appCommanderFragment;
    private List<ApplicationInfo> apps;

    public AppsRecyclerViewAdapter(AppCommanderFragment appCommanderFragment, List<ApplicationInfo> apps) {
        this.appCommanderFragment = appCommanderFragment;
        this.apps = apps;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppViewHolder appViewHolder = (AppViewHolder) holder;

        ApplicationInfo app = apps.get(position);

        appViewHolder.itemTextView.setText(appCommanderFragment.getContext().getPackageManager().getApplicationLabel(app));
        appViewHolder.itemViewRoot.setOnClickListener(new AppRecyclerViewItemListener(appCommanderFragment, app));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }
}
