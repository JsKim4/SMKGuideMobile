package com.example.smkguide.ui.smokearea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smkguide.R;

public class SmokeAreaFragment extends Fragment {

    public static SmokeAreaFragment newInstance(){
        return  new SmokeAreaFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_smokearea, container, false);




<<<<<<< Updated upstream
        return root;
=======
        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.584678, 126.925189);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);

        markerOptions.title("명지전문대");

        markerOptions.snippet("학교");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL,13));

>>>>>>> Stashed changes
    }


}