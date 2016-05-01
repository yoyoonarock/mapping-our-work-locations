package edu.illinois.cs465.mappingourworklocations;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.nio.MappedByteBuffer;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener
{
    /**
     *  Similar format for all activities with a navigation drawer.
     */

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void addDrawerItems() {
        String[] osArray = { "Home", "Favorites", "Search", "Map", "Add Event" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i;
                switch (position){
                    case 0:
                        // Switch to MainActivity.
                        i = new Intent(MapActivity.this, MainActivity.class);
                        startActivity(i);
                        return;
                    case 1:
                        // Switch to FavoritesActivity.
                        i = new Intent(MapActivity.this, FavoritesActivity.class);
                        startActivity(i);
                    case 2:
                        // Switch to SearchActivity.
                        i = new Intent(MapActivity.this, SearchActivity.class);
                        startActivity(i);
                        return;
                    case 3:
                        // Already in MapActivity - don't switch to a new activity.
                        return;
                    case 4:
                        // Switch to AddEventActivity.
                        // TODO: ADDEVENT
                    default:
                        return;
                }
            }
        });
    }

    private void setupDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *  Activity-specific functions begin here.
     */

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for all four buildings.
        LatLng siebel = new LatLng(40.1138, -88.2250);
        LatLng grainger = new LatLng(40.1125, -88.2269);
        LatLng dcl = new LatLng(40.1131, -88.2264);
        LatLng eceb = new LatLng(40.1149, -88.2280);
        mMap.addMarker(new MarkerOptions().position(siebel).title("Siebel Center"));
        mMap.addMarker(new MarkerOptions().position(grainger).title("Grainger Library"));
        mMap.addMarker(new MarkerOptions().position(dcl).title("Digital Computer Laboratory"));
        mMap.addMarker(new MarkerOptions().position(eceb).title("Electrical and Computer Engineering Building"));

        LatLng center = new LatLng(40.1140, -88.2265);
        moveToCurrentLocation(center);

        mMap.setOnInfoWindowClickListener(this);
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show();

        //TODO: create intent
    }
}



















