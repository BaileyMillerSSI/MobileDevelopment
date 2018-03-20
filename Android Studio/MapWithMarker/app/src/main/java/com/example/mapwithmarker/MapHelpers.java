package com.example.mapwithmarker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;

public class MapHelpers {


    static final int LOCATION_REQUEST = 1;

    Activity Parent;

    public MapHelpers(Activity act)
    {
        Parent = act;
    }

    public void GetUserLocation()
    {
        FusedLocationProviderApi locationApi = LocationServices.FusedLocationApi;

        if (ActivityCompat.checkSelfPermission(Parent.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Parent.getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }

    public void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(Parent.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            boolean mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(Parent, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        }
    }


    private void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST:
                DisplayErrorToast("Location Permission Granted!");
                break;
        }
    }

    private void DisplayErrorToast(String error) {
        Toast.makeText(Parent, error, Toast.LENGTH_LONG).show();
    }

}
