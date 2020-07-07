package com.example.iosegnalo.boundary;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.iosegnalo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CittadinoActivity extends AppCompatActivity {
    MapView mappa;
    private GoogleMap mMap;
    Spinner tipologiaLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cittadino);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mappa = findViewById(R.id.mapView);

        //aggiungo tutte le tipologie di problemi
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Illuminazione pubblica");
        arrayList.add("Dissesto su strada");
        arrayList.add("Verde pubblico");
        arrayList.add("Igiene/Decoro");
        arrayList.add("Fognature");
        arrayList.add("Dissesto su marciapiede");

        tipologiaLista = findViewById(R.id.tipologiaLst);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipologiaLista.setAdapter(arrayAdapter);

        /*tipologiaLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        tipologiaLista.add
*/

            mappa.onCreate(savedInstanceState);
            mappa.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng coordinates = new LatLng(40.856033,14.248337);
                    googleMap.addMarker(new MarkerOptions().position(coordinates).title("ciao"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                    mappa.onResume();
                    }
            });
        }

}
