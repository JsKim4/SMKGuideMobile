package com.example.smkguide.ui.smokearea;

import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smkguide.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class SmokeAreaFragment extends Fragment implements OnMapReadyCallback{

    private MapView mapView = null;
    SupportMapFragment mapFragment;
    SearchView searchView;
    GoogleMap map;


    public static SmokeAreaFragment newInstance(){
        return  new SmokeAreaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_smokearea, container, false);

        searchView = (SearchView)root.findViewById(R.id.sv_location);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);







        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> adressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getActivity(),Locale.getDefault());
                    try {
                        adressList = geocoder.getFromLocationName(location,1);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = adressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);




        return root;

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng SEOUL = new LatLng(37.584678, 126.925189);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);

        markerOptions.title("명지전문대");

        markerOptions.snippet("학교");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));


    }


}