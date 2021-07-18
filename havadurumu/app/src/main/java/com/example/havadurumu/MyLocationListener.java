package com.example.havadurumu;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyLocationListener {
    LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    protected Context context;
    public Double paralel;
    public Double meridyen;
    Location lastLocation;
    LocationListener locationListener;
    public interface loc {

        void onError(String Error);

        void onSuccess(Double paralel, Double meridyen,Boolean b);
    }

    public void startLocationUpdates(Context context,Activity activity, loc lc) {


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {


                if (lastLocation == null) {
                    lastLocation = location;
                }


            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            BigDecimal bd = new BigDecimal(location.getLatitude()).setScale(2, RoundingMode.HALF_UP);
                            BigDecimal bd1 = new BigDecimal(location.getLongitude()).setScale(2, RoundingMode.HALF_UP);

                            paralel=bd.doubleValue();
                            meridyen=bd1.doubleValue();
                            lc.onSuccess(paralel,meridyen,true);

                        }
                        else {
                            Toast.makeText(context.getApplicationContext(), "GPS signal not found Please Try again",Toast.LENGTH_SHORT).show();
                                /*Intent intent =new Intent(context, VoiceListener.class);
                                activity.startActivity(intent);*/
                        }
                    }
                });


    }



}

