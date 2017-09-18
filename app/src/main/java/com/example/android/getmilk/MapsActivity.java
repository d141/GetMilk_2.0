package com.example.android.getmilk;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.lang.Object.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        LatLng start = new LatLng(39,-98);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,3));
        //Take input of zipcode from textbox
        //Convert zipcode to latlng and zoom camera there
        //TODO erase the first location marker if a second is created

        EditText mZip = findViewById(R.id.zipCode);
        mZip.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    EditText mZip = findViewById(R.id.zipCode);
                    String zip = mZip.getText().toString();
                    Locale locale = new Locale("ENGLISH","US");
                    Geocoder gc = new Geocoder(getApplicationContext(),locale);
                    List<Address> result = null;

                    try {
                        result = gc.getFromLocationName(zip,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = result.get(0);
                    double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    LatLng latlng = new LatLng(lat,lng);
                    mMap.addMarker(new MarkerOptions().position(latlng).title("You are Here").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_here)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
                    return true;
                }
                return false;
            }
        });

  //Read in locations from assets.locations.csv
        try {
            mMap = googleMap;
            InputStreamReader stream = new InputStreamReader(getAssets().open("locations.csv"));
            BufferedReader reader = new BufferedReader(stream);
            String info;
            while ((info = reader.readLine()) != null) {
                String[] line = info.split(",");

                Double latitude = Double.parseDouble(line[1]);
                Double longitude = Double.parseDouble(line[2]);
                String siteName = String.valueOf(line[0]);
                LatLng location = new LatLng(latitude, longitude);
                String snippet = String.valueOf(line[3]);

                mMap.addMarker(new MarkerOptions().position(location).title(siteName).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cow)));
            }

        }
        catch (IOException e){
            System.out.println("IOEXCEPTION");
        }
    }

}
