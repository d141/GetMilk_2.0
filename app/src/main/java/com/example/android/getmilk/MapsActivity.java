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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
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

        // Add a marker in Sydney and move the camera

      //  LatLng sydney = new LatLng(-34, 151);

        EditText mZip = (EditText) findViewById(R.id.zipCode);
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
                    mMap.addMarker(new MarkerOptions().position(latlng).title("Marker in Sydney"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                    return true;
                }
                return false;
            }
        });


        //   Log.d("myTag", String.valueOf(lng));

    }

 //   AIzaSyBttzXL1JHacxSitcN38zMN8neKhVXR044
}
