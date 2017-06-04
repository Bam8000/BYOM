package com.UTS.locaTO.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.UTS.locaTO.Database;
import com.UTS.locaTO.Event;
import com.UTS.locaTO.MainActivity;
import com.UTS.locaTO.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Benn on 2017-05-29.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private Database dataset;

    public interface IZoneClick{
        void zoneClick(Event model);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View root;
        public TextView txtTitle, txtAddr, txtTags, txtTime;
        public ImageView eventImage;

        public ViewHolder(View itemView) {
            super(itemView);

            root = itemView;
            txtTitle = (TextView) itemView.findViewById(R.id.item_txtTitle); //PLACEHOLDERS TO FIX
            txtAddr = (TextView) itemView.findViewById(R.id.item_txtAddress);
            txtTags = (TextView) itemView.findViewById(R.id.item_txtTags);
            txtTime = (TextView) itemView.findViewById(R.id.item_txtTime);
            eventImage = (ImageView) itemView.findViewById(R.id.eventImage);
        }
    }

    IZoneClick callback;
    public MainActivity context; //Don't really know what this is... but it doesn't give any errors
    public EventsAdapter(Database dataset, IZoneClick callback, MainActivity contextInner) {
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
        final Event item = dataset.getEvents().get(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.zoneClick(item); //HOW DOES THIS WORK?
            }
        });
        holder.txtTitle.setText(item.getEventName() + " (" + item.getDistance() + ")");
        holder.txtAddr.setText(item.getLocation());
        holder.txtTime.setText(item.getTime().toString());
        holder.txtTags.setText("Tags: " + item.getCategories());

        //NEED TO CUSTOMIZE THIS FOR OUR PLACEHOLDER PHOTO
        if(item.getPhotoUrl() != null) {
            String stringPhoto = item.getPhotoUrl().replaceAll("\\\\u0026", "&").replaceAll("\\\\u003d", "=");

            Picasso.with(context).load(stringPhoto).into(holder.locationImage); //ADD Picasso Class
        }
    }

    @Override
    public int getItemCount() {
        return dataset.getEvents().size();
    }


}
