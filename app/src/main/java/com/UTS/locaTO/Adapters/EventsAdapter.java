package com.UTS.locaTO.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.UTS.locaTO.Event;
import com.UTS.locaTO.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Benn on 2017-05-29.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private ArrayList<Event> dataset;


    public interface IZoneClick {
        void zoneClick(Event model);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View root;
        public TextView txtTitle, txtTime, txtDist;
        public ImageView eventImage;

        public ViewHolder(View itemView) {
            super(itemView);

            root = itemView;
            txtTitle = (TextView) itemView.findViewById(R.id.item_txtTitle);
            txtDist = (TextView) itemView.findViewById(R.id.item_txtDistance);
            txtTime = (TextView) itemView.findViewById(R.id.item_txtTime);
            eventImage = (ImageView) itemView.findViewById(R.id.locationImage);
        }
    }

    IZoneClick callback;
    public Context context;

    public EventsAdapter(ArrayList<Event> dataset, IZoneClick callback, Context contextInner) {
        this.dataset = dataset;
        context = contextInner;
        this.callback = callback;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_outline, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Event item = dataset.get(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.zoneClick(item); //HOW DOES THIS WORK?
            }
        });
        holder.txtTitle.setText(item.getEventName());
        holder.txtTime.setText(item.getTime().toString());
        holder.txtDist.setText("12 km");

        if (item.getPhotoUrl() != null) {
            if (!item.getPhotoUrl().isEmpty()) {
                Picasso.with(context).load(item.getPhotoUrl()).into(holder.eventImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
