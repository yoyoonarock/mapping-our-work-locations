package edu.illinois.cs465.mappingourworklocations;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


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
        mMap.addMarker(new MarkerOptions().position(siebel).title("Marker in Siebel"));
        mMap.addMarker(new MarkerOptions().position(grainger).title("Marker in Grainger"));
        mMap.addMarker(new MarkerOptions().position(dcl).title("Marker in DCL"));
        mMap.addMarker(new MarkerOptions().position(eceb).title("Marker in ECEB"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(siebel));
        LatLng center = new LatLng(40.1140, -88.2265);
        moveToCurrentLocation(center);
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
    }
}
