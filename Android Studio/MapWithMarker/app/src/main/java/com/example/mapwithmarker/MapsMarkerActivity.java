package com.example.mapwithmarker;


import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    MainActivity obj = new MainActivity();
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    LatLng treasureLocation;
    Marker mCurrLocationMarker;
    Marker mTreasureLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    TextView Distance;
    TextView Direction;
    int gameTracker =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);


        Distance = (TextView) findViewById(R.id.distance);
        Direction = (TextView)findViewById(R.id.direction);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000); // 1 seconds
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted

                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback(){
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {

                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.smiley_launcher));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                mGoogleMap.setMyLocationEnabled(false);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                mGoogleMap.setBuildingsEnabled(false);

                if(treasureLocation == null)
                {
                    treasureLocation = getNextLocation(latLng, 15);
                }

                Location treasure = new Location("");
                treasure.setLatitude(treasureLocation.latitude);
                treasure.setLongitude(treasureLocation.longitude);
                int distance = Math.round(location.distanceTo(treasure));


                if(distance <= 1)
                {
                    MarkerOptions finalPoint = new MarkerOptions();
                    finalPoint.position(treasureLocation);
                    finalPoint.title("Treasure Position");
                    finalPoint.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                    mTreasureLocationMarker = mGoogleMap.addMarker(finalPoint);
                    Direction.setText("You found the treasure!");
                    Distance.setText("");
                    obj.updateGameTracker();
                    gameTracker++;
                    treasureLocation = null;
                    Intent gameActivity = new Intent(getApplicationContext(),GameActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("game",gameTracker);
                    gameActivity.putExtras(bundle);
                    startActivity(gameActivity);



                }else
                {
                    Distance.setText("Distance: " + String.valueOf(distance) + "m");
                    Direction.setText(GetDirection(location, treasure));
                }
            }
        };

    };

    public String GetDirection(Location curPos, Location destination)
    {

        float bearing = curPos.bearingTo(destination);
        if(bearing < 0)
        {
            bearing = bearing+360;
        }

        if(bearing <= 45 || (bearing > 315 && bearing <= 360))
        {
            return "Go North";
        }else if(bearing > 45 && bearing <= 135)
        {
            return "Go East";
        }else if(bearing > 135 && bearing <= 225)
        {
            return  "Go South";
        }else if(bearing > 225 && bearing <= 315)
        {
            return  "Go West";
        }

        return "";
    }

    public LatLng getNextLocation(LatLng point, int radius) {
        LatLng randomLatLng;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            gameTracker = extras.getInt("gameTracker");
        }
       // gameTracker= obj.GameTracker();
        if(gameTracker==0) {
            randomLatLng = new LatLng(41.234728, -77.022374);
        }else if(gameTracker==1){
          randomLatLng = new LatLng(41.237786, -77.025787);
        }else if(gameTracker==2){
           randomLatLng = new LatLng(41.238575, -77.027556);
        }else{
            randomLatLng = new LatLng(41.234728, -77.022374);
            gameTracker =0;
        }
        return randomLatLng;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                final Activity Owner = this;
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // 1. Create Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getBaseContext());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Owner, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        });

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();

                dialog.show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }
}
