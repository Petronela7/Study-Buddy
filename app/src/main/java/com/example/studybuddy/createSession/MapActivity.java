package com.example.studybuddy.createSession;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.studybuddy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
   GoogleMap map;
   SupportMapFragment mapFragment;
   SearchView searchView;
   FloatingActionButton choose;
    public static  double LATITUDE = 0 ;
    public static  String latitude = "latitude" ;
    public static  double LONGITUDE = 0;
    public static  String longitude = "longitude";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        choose = findViewById(R.id.choose);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String location = searchView.getQuery()
                        .toString();
                List<Address> addressList = null;
                if(location!= null || location.equals("")){
                    Geocoder geocoder = new Geocoder(MapActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    LATITUDE = address.getLatitude();
                    LONGITUDE = address.getLongitude();


                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        choose.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), CreateSession.class);
            // Sending Email to Dashboard Activity using intent.
            if(!searchView.getQuery().toString().equals("") ) {
                Bundle extras = new Bundle();
                extras.putDouble(latitude, LATITUDE);
                extras.putDouble(longitude, LONGITUDE);
                intent.putExtras(extras);
                startActivity(intent);
            }
            else{
                Toast.makeText(getBaseContext(),"Choose Location",Toast.LENGTH_LONG).show();
            }
        });

        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
    }
}