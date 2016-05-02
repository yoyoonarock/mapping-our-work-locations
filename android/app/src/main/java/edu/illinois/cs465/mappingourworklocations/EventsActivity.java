package edu.illinois.cs465.mappingourworklocations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jason on 4/30/2016.
 */
public class EventsActivity extends AppCompatActivity
{
    /**
     *  Similar format for all activities with a navigation drawer.
     */

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_EVENTS = "events";
    private static final String TAG_EVENTNAME = "eventname";
    private static final String TAG_BUILDING = "building";
    private static final String TAG_ROOMNUMBER = "roomnumber";
    private static final String TAG_EVENTDATE = "eventdate";
    private static final String TAG_STARTTIME = "starttime";
    private static final String TAG_ENDTIME = "endtime";
    private static final String TAG_AVAILABILITY = "availability";
    private static final String TAG_DESCRIPTION = "description";

    Event [] events;

    JSONArray jsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        new LoadFavorites().execute();
    }

    private void addDrawerItems() {
        String[] osArray = { "Home", "Favorites", "Search", "Map", "Events" };
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
                        i = new Intent(EventsActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        // Switch to FavoritesActivity.
                        i = new Intent(EventsActivity.this, FavoritesActivity.class);
                        startActivity(i);
                        break;
                    case 2:
                        // Switch to SearchActivity.
                        i = new Intent(EventsActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                    case 3:
                        // Switch to MapActivity.
                        i = new Intent(EventsActivity.this, MapActivity.class);
                        startActivity(i);
                        break;
                    case 4:
                        // Already in EventsActivity - don't switch to a new activity.
                        break;
                    default:
                        break;
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
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadFavorites extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EventsActivity.this);
            pDialog.setMessage("Loading events. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args)
        {
//            android.os.Debug.waitForDebugger();
            String link = "http://cs465mowl.web.engr.illinois.edu/events.php";
            JSONObject jObj = null;
            String json = "";
            try
            {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);

                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));

                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = br.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                br.close();
                is.close();
                json = sb.toString();

                conn.disconnect();
            }
            catch (Exception e)
            {

            }

            try
            {
                jObj = new JSONObject(json);
            }
            catch(Exception e)
            {

            }

            // Check your log cat for JSON reponse
            Log.d("All Products: ", jObj.toString());

            try {
                // Checking for SUCCESS TAG
                int success = jObj.getInt(TAG_SUCCESS);

                if (success == 1)
                {
                    // Events were found.
                    jsonArray = jObj.getJSONArray(TAG_EVENTS);

                    if (jsonArray.length() > 0)
                    {
                        events = new Event[jsonArray.length()];
                    }

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject c = jsonArray.getJSONObject(i);

                        String eventname = c.getString(TAG_EVENTNAME);
                        String building = c.getString(TAG_BUILDING);
                        String roomNumber = c.getString(TAG_ROOMNUMBER);
                        String eventdate = c.getString(TAG_EVENTDATE);
                        String starttime = c.getString(TAG_STARTTIME);
                        String endtime = c.getString(TAG_ENDTIME);
                        String availability = c.getString(TAG_AVAILABILITY);
                        String description = c.getString(TAG_DESCRIPTION);

                        Event event = new Event(eventname, building, roomNumber, eventdate, starttime, endtime, availability, description);
                        events[i] = event;
                    }
                }
                else
                {
                    // No events were found.
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListView lv = (ListView) findViewById(R.id.listAllEvents);
                    if (events.length == 0)
                    {
                        return;
                    }
                    EventListAdapter adapter = new EventListAdapter(EventsActivity.this, events);

                    lv.setAdapter(adapter);
                }
            });

        }

    }
}
