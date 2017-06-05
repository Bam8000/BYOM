package com.UTS.locaTO;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventCardActivity extends AppCompatActivity {

    public TextView txtTitle, txtAddress, txtTime, txtCost, txtDescription, txtTags;
    public ImageView eventImage;
    public Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_card);

        Intent intent = getIntent();

        String name = intent.getStringExtra("query_name");
        String address = intent.getStringExtra("query_address");
        String time = intent.getStringExtra("query_time");
        String cost = intent.getStringExtra("query_cost");
        String description = intent.getStringExtra("query_description");
        String tags = intent.getStringExtra("query_tags");
        String mapUrl = intent.getStringExtra("query_map");
        String photoUrl = intent.getStringExtra("query_image");

        txtTitle = (TextView) findViewById(R.id.item_txtTitleCard);
        txtAddress = (TextView) findViewById(R.id.item_txtAddressCard);
        txtTime = (TextView) findViewById(R.id.item_txtTimeCard);
        txtCost = (TextView) findViewById(R.id.item_txtCostCard);
        txtDescription = (TextView) findViewById(R.id.item_txtDescriptionCard);
        txtTags = (TextView) findViewById(R.id.item_txtTagsCard);
        eventImage = (ImageView) findViewById(R.id.eventImageCard);
        mapButton = (Button) findViewById(R.id.mapButton);

        txtTitle.setText(name);
        txtAddress.setText(address);
        txtTime.setText(time);
        txtCost.setText(cost);
        txtDescription.setText(description);
        txtTags.setText(tags);

        if(photoUrl != null) {
            Picasso.with(context).load(photoUrl).into(eventImage); //ADD Picasso Class
        }


        /*Uri location = Uri.parse("https://maps.google.com/maps?daddr="+Uri.encode(model.vicinity)+"("+Uri.encode(model.name)+")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }*/

    }
}
