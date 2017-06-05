package com.UTS.locaTO.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    private ArrayList<Event> eventList;
    private IZoneClick callback;
    private Context context;
    private Double lat;
    private Double lng;

    public EventsAdapter(ArrayList<Event> eventList, IZoneClick callback, Context context, Double lat, Double lng) {
        this.eventList = eventList;
        this.callback = callback;
        this.context = context;
        this.lat = lat;
        this.lng = lng;
    }

    public interface IZoneClick {
        void zoneClick(Event model);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private TextView txtTitle, txtTime, txtDist;
        private ImageView eventImage;

        private ViewHolder(View itemView) {
            super(itemView);

            root = itemView;
            txtTitle = (TextView) itemView.findViewById(R.id.item_txtTitle);
            txtDist = (TextView) itemView.findViewById(R.id.item_txtDistance);
            txtTime = (TextView) itemView.findViewById(R.id.item_txtTime);
            eventImage = (ImageView) itemView.findViewById(R.id.locationImage);
        }
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_outline, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Event item = eventList.get(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.zoneClick(item);
            }
        });

        holder.txtTitle.setText(item.getEventName());
        holder.txtTime.setText(item.getTime().toString());
        holder.txtDist.setText(item.getDistance(lat, lng));

        if (!item.getPhotoUrl().isEmpty()) {
            Picasso.with(context).load(item.getPhotoUrl()).into(holder.eventImage);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
