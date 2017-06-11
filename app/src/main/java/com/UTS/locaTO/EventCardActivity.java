package com.UTS.locaTO;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity for Event
 */

public class EventCardActivity extends AppCompatActivity {

    public TextView txtTitle, txtAddress, txtTime, txtCost, txtDescription, txtTags;
    public Button mapButton;
    public Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_card);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            Log.w(this.getLocalClassName(), "toolbar is null");
        }

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String time = intent.getStringExtra("time");
        String cost = intent.getStringExtra("cost");
        String description = intent.getStringExtra("description");
        String tags = intent.getStringExtra("tags");

        uri = Uri.parse(intent.getStringExtra("map"));

        txtTitle = (TextView) findViewById(R.id.item_txtTitleCard);
        txtAddress = (TextView) findViewById(R.id.item_txtAddressCard);
        txtTime = (TextView) findViewById(R.id.item_txtTimeCard);
        txtCost = (TextView) findViewById(R.id.item_txtCostCard);
        txtDescription = (TextView) findViewById(R.id.item_txtDescriptionCard);
        txtTags = (TextView) findViewById(R.id.item_txtTagsCard);
        mapButton = (Button) findViewById(R.id.mapButton);

        txtTitle.setText(name);
        txtAddress.setText(address);
        txtTime.setText(time);
        txtCost.setText(cost);
        txtDescription.setText(description);
        txtTags.setText(tags);
        mapButton.setOnClickListener(mGoogleMaps);
    }

    /**
     * Callback for when the user requests directions
     */
    private View.OnClickListener mGoogleMaps = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }
    };

}
