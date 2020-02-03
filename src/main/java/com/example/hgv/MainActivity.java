package com.example.hgv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener{


        LocationManager loc;
        protected Location lastLocation;
     //   private AddressResultReceiver resultReceiver;

        FloatingActionButton viu;
        Location mLastLocation;
        Marker mCurrLocationMarker;
        GoogleApiClient mGoogleApiClient;
        LocationRequest mLocationRequest;
        int MY_LOCATION_REQUEST_CODE = 1;
        private static final LatLng BOUND_CORNER_NW = new LatLng(31.399892, 75.528188);
        private static final LatLng BOUND_CORNER_SE = new LatLng(31.392927, 75.543178);
        private static final LatLngBounds RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
                .include(BOUND_CORNER_NW)
                .include(BOUND_CORNER_SE)
                .build();
        GoogleMap map;
        GoogleApiClient client;
        int i = 0;
        int j = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            viu = findViewById(R.id.viewchenge);


        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;


//        if(android.os.Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
//        {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            buildGoogleApiClient();
//            map.setMyLocationEnabled(true);
//        }
//        }
//        else
//            {
//        buildGoogleApiClient();
//        map.setMyLocationEnabled(true);
//        }
            map.setBuildingsEnabled(true);
            map.setIndoorEnabled(true);

            loc = (LocationManager) this.

                    getSystemService(Context.LOCATION_SERVICE);

            final LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    LatLng lng = new LatLng(location.getLatitude(), location.getLongitude());

                    Log.i("kash", "onLocationChanged: " + lng.toString());
                    map.animateCamera(CameraUpdateFactory.newLatLng(lng));
                    map.addMarker(new MarkerOptions().position(lng).title("wooho"));


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
                map.setOnMyLocationButtonClickListener(this);
                map.setOnMyLocationClickListener(this);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
                // Show rationale and request permission.
            }
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            final LatLng latLng = new LatLng(31.394308, 75.533227);

            googleMap.addMarker(new

                    MarkerOptions()
                    .

                            position(latLng)).

                    setTitle("nitj");

            float d = (float) 60.0;
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(RESTRICTED_BOUNDS_AREA,60));

              map.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA);

            map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().

                    zoom(18).

                    bearing(60).

                    target(latLng).

                    build()));


            viu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (j == 0) {
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().zoom(18).bearing(60).target(latLng).tilt(45).build()));
                        j = 1;
                    } else {
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().zoom(18).bearing(60).target(latLng).build()));
                        j = 0;
                    }
                }
            });
        }



        public void hello(View view) {
            if (i == 0) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                i = 1;
            } else {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                i = 0;
            }

        }




//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == MY_LOCATION_REQUEST_CODE) {
//            if (permissions.length == 1 &&
//                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                map.setMyLocationEnabled(true);
//                map.setOnMyLocationButtonClickListener(this);
//                map.setOnMyLocationClickListener(this);;
//            } else {
//                // Permission was denied. Display an error message.
//            }
//        }

//}

//public void changeView(View view)
//{
//
//    map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().zoom(18).bearing(60).build()));
//}

        @Override
        public boolean onMyLocationButtonClick() {
            Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
            // Return false so that we don't consume the event and the default behavior still occurs
            // (the camera animates to the user's current position).
            Log.i("kash", "onMyLocationButtonClick: hjhfbdnc");
            try {
                getCurrentLocation();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void onMyLocationClick(@NonNull Location location) {
            Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();


        }

        private void getCurrentLocation() {
            try {
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                String locationProvider = LocationManager.NETWORK_PROVIDER;
                // I suppressed the missing-permission warning because this wouldn't be executed in my
                // case without location services being enabled
                @SuppressLint("MissingPermission") android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                double userLat = lastKnownLocation.getLatitude();
                double userLong = lastKnownLocation.getLongitude();
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(userLat, userLong));

                map.moveCamera(center);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }




}

