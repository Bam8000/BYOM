package com.UTS.locaTO;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import com.UTS.locaTO.APIs.Reddit;
import com.UTS.locaTO.APIs.Toronto;
import com.UTS.locaTO.Adapters.EventsAdapter;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;

/**
 * Main Activity
 */

public class MainActivity extends AppCompatActivity {

    private Database database;
    private OkHttpClient client;

    private double lat;
    private double lng;
    private ArrayList<String> categories;

    private RecyclerView mEventList;
    private EventsAdapter mEventsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Menu mCategoryMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.database = new Database();
        this.client = new OkHttpClient();

        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Swipe Refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        // List
        mEventList = (RecyclerView) findViewById(R.id.content_main_lstSearch);
        if (mEventList != null) {
            mEventList.setHasFixedSize(true);
        }

        // Layout Manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mEventList.setLayoutManager(mLayoutManager);

        // Events Adapter
        mEventsAdapter = new EventsAdapter(database.getEvents(), this, lat, lng);
        mEventsAdapter.setEventClickListener(onEventClick);
        mEventList.setAdapter(mEventsAdapter);

        // Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        // Navigation View (Pretty Drawer)
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(onNavigationItemSelected);
            categories = new ArrayList<>();
            mCategoryMenu = navigationView.getMenu();
        }

        this.loadItems();

        mEventsAdapter.notifyDataSetChanged();
    }

    /**
     * OkHttp client for API calls
     * @return HTTP Client
     */
    public OkHttpClient getClient() {
        return this.client;
    }

    /**
     * Load events
     */
    private void loadItems() {
        Reddit reddit = new Reddit(this);
        Toronto toronto = new Toronto(this);
        reddit.execute();
        toronto.execute();
        getLocation();
    }

    /**
     * Get current location from Android API
     */
    private void getLocation() {
        // Check for permissions
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
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

    /**
     * Callback for new events
     * @param events Array of events to add to database
     */
    public void onNewEvents(ArrayList<Event> events) {
        if (events != null) {
            for (Event event : events) {
                this.database.addEvent(event);
                Log.i("Events", "added a new event: " + event.getName());
                for (String category : event.getCategories()) {
                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }
        }
        Collections.sort(categories);
        for (String category : categories) {
            mCategoryMenu.add(category);
        }
        mEventsAdapter = new EventsAdapter(database.getEvents(), this, lat, lng);
        mEventList.setAdapter(mEventsAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Callback for categorized events
     * @param category Category to display
     */
    public void onCategorizedEvents(String category) {
        if (category.equals("All")) {
            mEventsAdapter = new EventsAdapter(database.getEvents(), this, lat, lng);
        } else {
            mEventsAdapter = new EventsAdapter(database.getCategorizedEvents(category), this, lat, lng);
        }
        mEventList.setAdapter(mEventsAdapter);
    }

    /**
     * Callback for a category is selected
     */
    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            onCategorizedEvents(item.getTitle().toString());
            return true;
        }
    };

    /**
     * Callback for when an event is selected
     */
    private EventsAdapter.onEventClickListener onEventClick = new EventsAdapter.onEventClickListener() {
        @Override
        public void onEventClick(Event model) {
            Intent myIntent = new Intent(MainActivity.this, EventCardActivity.class);
            myIntent.putExtra("name", model.getName());
            myIntent.putExtra("address", model.getAddress());
            myIntent.putExtra("time", model.getTime().toString());
            myIntent.putExtra("cost", "Price: " + model.getCost());
            myIntent.putExtra("description", model.getDescription());
            myIntent.putExtra("url", model.getUrl());
            myIntent.putExtra("tags", model.getCategories());
            myIntent.putExtra("map", model.getMapUri());
            MainActivity.this.startActivity(myIntent);
        }
    };

    /**
     * Callback for when the location is updated
     * @param location Location of the users phone
     */
    private void onLocationChanged(Location location) {
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
        Log.i("Location", "Lat: " + this.lat + ", Lng: " + this.lng);
        // this.eventbrite.execute(lat, lng);
    }

}
