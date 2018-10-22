package com.example.cln62.googlemappra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    Button buttonSaveLocation, buttonSeeMap;
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;
    int REQUEST_LOCATION_PERMISSION = 1;
    double mLatitude, mLongitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        buttonSaveLocation = findViewById(R.id.buttonSave);
        buttonSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        buttonSeeMap = findViewById(R.id.buttonMap);
        buttonSeeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation == null) {
                    Toast.makeText(MainActivity.this, "location has not been saved!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("lat", mLatitude);
                i.putExtra("lon", mLongitude);
                startActivity(i);
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;
                                mLatitude = mLastLocation.getLatitude();
                                mLongitude = mLastLocation.getLongitude();
                            }
                        }
                    });
        }
    }
}
