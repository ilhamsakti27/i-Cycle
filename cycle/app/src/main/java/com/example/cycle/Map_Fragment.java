package com.example.cycle;

import android.content.Context;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Map_Fragment extends Fragment {
    private GoogleMap mMap;

//    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_,container,false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(@NonNull LatLng latLng) {
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(latLng);
//                        markerOptions.title((latLng.latitude+" KG "+ latLng.longitude));
//                        googleMap.clear();
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
//                        googleMap.addMarker((markerOptions));
//                    }

                    mMap = googleMap;

                    // Add a marker in Sydney and move the camera
                    LatLng DTIits = new LatLng(-7.281731914518696, 112.795520693715);
                    // float zoom = 16f;
                mMap.addMarker(new MarkerOptions().position(DTIits).title("Marker in DTI ITS"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DTIits, 16));


//                });
            }
        });

        return view;
    }
}