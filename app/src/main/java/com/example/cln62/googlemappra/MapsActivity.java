package com.example.cln62.googlemappra;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double mLatitude, mLongitude;
    Geocoder geocoder;
    String mLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mLatitude = getIntent().getExtras().getDouble("lat");
        mLongitude = getIntent().getExtras().getDouble("lon");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                geocoder = new Geocoder(MapsActivity.this,
                        Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(
                            mLatitude,
                            mLongitude,
                            // In this sample, we get just a single address.
                            1);
                    Address address = addresses.get(0);
                    ArrayList<String> addressParts = new ArrayList<>();
                    // Fetch the address lines using getAddressLine,
                    // join them, and send them to the thread
                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        addressParts.add(address.getAddressLine(i));
                    }
                    mLocation = TextUtils.join("\n", addressParts);

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 3000);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
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

        LatLng sydney = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("My last location is " + mLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
