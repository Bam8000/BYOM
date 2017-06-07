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
 * Created by Marcel O'Neil on 2017-05-29.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final ArrayList<Event> eventList;
    private final Context context;
    private final Double lat, lng;

    private onEventClickListener mListener;

    public EventsAdapter(ArrayList<Event> eventList, Context context, Double lat, Double lng) {
        this.eventList = eventList;
        this.context = context;
        this.lat = lat;
        this.lng = lng;
    }

    public interface onEventClickListener {
        void onEventClick(Event model);
    }

    /**
     * Set a listener that will be notified when an Event is selected.
     *
     * @param listener The listener to notify
     */
    public void setEventClickListener(onEventClickListener listener) {
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final View root;
        private final TextView txtTitle;
        private final TextView txtTime;
        private final TextView txtDist;
        private final ImageView eventImage;

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
                mListener.onEventClick(item);
            }
        });

        holder.txtTitle.setText(item.getName());
        holder.txtTime.setText(item.getTime().toString());
        holder.txtDist.setText(item.getDistance(lat, lng));

        String photo = item.getPhoto();
        if (photo.isEmpty()) {
            // For some odd reason this is needed or else the default placeholder images get
            // replaced by other loaded images
            Picasso.with(context).load(R.drawable.toronto).into(holder.eventImage);
        } else {
            Picasso.with(context).load(photo).into(holder.eventImage);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
