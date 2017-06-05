package com.UTS.locaTO;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import com.UTS.locaTO.APIs.Eventbrite;
import com.UTS.locaTO.APIs.Reddit;
import com.UTS.locaTO.APIs.Toronto;
import com.UTS.locaTO.Adapters.EventsAdapter;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements EventsAdapter.IZoneClick {

    private Database database;
    private OkHttpClient client;

    private double lat;
    private double lng;

    private RecyclerView mLstSearch;
    private EventsAdapter mEventsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.database = new Database();
        this.client = new OkHttpClient();

        this.loadItems();

        this.getUI();
    }

    /*@Override
    protected void onResume() {
        super.onResume();

        this.reddit.execute();
        this.toronto.execute();
        this.getLocation();
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public OkHttpClient getClient() {
        return this.client;
    }

    public void onNewEvents(ArrayList<Event> events) {
        if (events != null) {
            for (Event event : events) {
                this.database.addEvent(event);
                Log.i("events", "added a new event: " + event.getEventName());
            }
        }
        mEventsAdapter = new EventsAdapter(database.getEvents(), this, this);
        mLstSearch.setAdapter(mEventsAdapter);
    }

    //github.com/marceloneil/MinoTour
    public void getUI() {
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Log.i("refresh", "start");
                loadItems();
                Log.i("refresh", "stop");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mLstSearch = (RecyclerView) findViewById(R.id.content_main_lstSearch);
        if (mLstSearch != null) {
            mLstSearch.setHasFixedSize(true);
        } else {
            Log.i("mLstSearch", "mLstSearch is null");
        }

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLstSearch.setLayoutManager(mLayoutManager);
        mEventsAdapter = new EventsAdapter(database.getEvents(), this, this);
        mLstSearch.setAdapter(mEventsAdapter);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_menu);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    //Open sidebar
                }
            });
        } else {
            Log.i("toolbar", "toolbar is null");
        }

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if(drawer != null) {
            drawer.addDrawerListener(toggle);
        } else {
            Log.i("drawer", "drawer is null");
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        } else {
            Log.i("navigationView", "navigationView is null");
        }*/

        //updates the thing
        mEventsAdapter.notifyDataSetChanged();
    }

    public void loadItems() {
        // this.eventbrite.execute(this.lat, this.lng);
        //Eventbrite eventbrite = new Eventbrite(this);
        Reddit reddit = new Reddit(this);
        Toronto toronto = new Toronto(this);
        //eventbrite.execute(lat, lng);
        reddit.execute();
        toronto.execute();
    }

    //github.com/marceloneil/MinoTour
    public void zoneClick(Event model) {
        /*Uri location = Uri.parse("https://maps.google.com/maps?daddr=" + Uri.encode(model.getEventLocation()) + "(" + Uri.encode(model.getEventName()) + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }*/

        Intent myIntent = new Intent(MainActivity.this, EventCardActivity.class);
        myIntent.putExtra("query_name", model.getEventName() + " (asdfasdfas)");
        myIntent.putExtra("query_address", model.getEventLocation());
        myIntent.putExtra("query_time", model.getTime().toString());
        myIntent.putExtra("query_cost", "Price: " + model.getCost());
        myIntent.putExtra("query_description",
                model.getDescription() + "\n\nFor more information please visit " + model.getUrl() + ".");
        myIntent.putExtra("query_tags", model.getCategories());
        myIntent.putExtra("query_map", model.getMapUrl());
        if (model.getPhotoUrl() != null) {
            myIntent.putExtra("query_image", model.getPhotoUrl().replaceAll("\\\\u0026", "&").replaceAll("\\\\u003d", "="));
        }
        MainActivity.this.startActivity(myIntent);
    }

    public void getLocation() {
        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Find best provider
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider == null) {
            provider = "gps";
        }

        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            Log.i("Location", "Provider " + provider + " has been selected.");
            this.onLocationChanged(location);
        } else {
            if (provider.equals("gps")) {
                provider = "network";
            } else {
                provider = "gps";
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                this.onLocationChanged(location);
            } else {
                Log.i("Location", "Location not available");
            }
        }
    }

    public void onLocationChanged(Location location) {
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
        Log.i("Location", "Lat: " + this.lat + ", Lng: " + this.lng);
        // this.eventbrite.execute(lat, lng);
    }

}
