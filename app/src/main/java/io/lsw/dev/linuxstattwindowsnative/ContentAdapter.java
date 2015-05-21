package io.lsw.dev.linuxstattwindowsnative;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView name;
        TextView description;
        ImageView icon;

        ContentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            icon = (ImageView)itemView.findViewById(R.id.icon);
        }
    }

    List<Content> items;

    public ContentAdapter(List<Content> content){
        this.items = content;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ContentViewHolder pvh = new ContentViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder personViewHolder, int i) {
        personViewHolder.name.setText(items.get(i).name);
        personViewHolder.description.setText(items.get(i).age);
        personViewHolder.icon.setImageResource(items.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
