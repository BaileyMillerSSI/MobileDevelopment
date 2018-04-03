package com.example.mapwithmarker;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final String MA = "MainActivity";
    private final static int REQUEST_CODE = 100;
    private GoogleApiClient gac;
    private TravelManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new TravelManager();

        gac = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    public void onLocationChanged(Location location) {
        float accuracy = location.getAccuracy();
        long nanos = location.getElapsedRealtimeNanos();
        Log.w("MainActivity", "accuracy " + accuracy + ", nanos = " + nanos);
        updateTrip(null);
    }

    public void updateTrip(View v)
    {
        //manager.setDestination(destinationLocation);

        if ( CheckPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) && CheckPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION))
        {
            RequestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }else
        {

        }
    }

    private void RequestPermissions(String[] perms, int reqCode)
    {
        ActivityCompat.requestPermissions(this, perms, reqCode);
    }

    private void RequestPermission(String perm)
    {
        ActivityCompat.shouldShowRequestPermissionRationale(this, perm);
    }

    private boolean CheckPermission(String perm)
    {
        return ActivityCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_DENIED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:

                break;
        }
    }
    protected void onPause() {
        super.onPause();
        FusedLocationProviderApi flpa = LocationServices.FusedLocationApi;
        flpa.removeLocationUpdates(gac, this);
    }

    public void onConnected(Bundle hint) {
        FusedLocationProviderApi flpa = LocationServices.FusedLocationApi;
        LocationRequest request = new LocationRequest();
        request.setInterval(30000);
        request.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        request.setSmallestDisplacement( 100 );
        if (gac.isConnected())
        {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            flpa.requestLocationUpdates(gac, request, this);
        }
        else
        {
            gac.connect( );
        }

    }

    public void onConnectionSuspended( int cause ) {
    }

    public void onConnectionFailed( ConnectionResult result ) {
        // test result here
        if( result.hasResolution( ) ) { // a resolution can be started
            try {
                result.startResolutionForResult( this, REQUEST_CODE );
            } catch( IntentSender.SendIntentException sie ) {
                // Intent has been cancelled or cannot execute, exit app
                Toast.makeText( this, "Google Play services problem, exiting",
                        Toast.LENGTH_LONG ).show( );
                finish( );
            }
        }
    }

    public void onActivityResult( int requestCode,
                                  int resultCode, Intent data ) {
        if( requestCode == REQUEST_CODE && resultCode == RESULT_OK ) {
            // problem solved, try to connect again
            gac.connect( );
        }
    }

    protected void onStart( ) {
        super.onStart( );
        if( gac != null )
            gac.connect( );
    }
}
