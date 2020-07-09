package com.example.iosegnalo.boundary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iosegnalo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.android.volley.RequestQueue;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SegnalaActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    MapView mappa;
    private GoogleMap mMap;
    Marker marker;
    Spinner tipologiaLista;
    TextView indirizzoLbl;
    TextView coordinateLabel;
    Location devicePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnala);
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        getSupportActionBar().hide();

        indirizzoLbl = findViewById(R.id.indirizzoLabel);

        //codice per rilevare la posizione GPS del dispositivo
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            Log.d("myapp", "Eseguito");
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_FINE_LOCATION);

            Log.d("myapp", "Permission Not Granted");
        }


        mappa = findViewById(R.id.mapView);
        coordinateLabel = findViewById(R.id.posizioneLbl);
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
                    MarkerOptions m;
                    m = new MarkerOptions();
                    LatLng napoli = new LatLng(40.856033,14.248337);
                    m.position(napoli).title("Posizione segnalata");
                    m.draggable(true);
                    mMap=googleMap;
                    googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        LatLng temp = null;
                        @Override
                        public void onMarkerDragStart(Marker marker) {
                            // TODO Auto-generated method stub
                            //temp=marker.getPosition();
                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            AggiornaIndirizzo();
                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {
                            // TODO Auto-generated method stub
                            //LatLng temp = marker.getPosition();
                            coordinateLabel.setText("Coordinate:" + marker.getPosition().toString());



                        }
                    });
                    LatLng coordinates = new LatLng(40.856033,14.248337);
                    coordinateLabel.setText("Coordinate:" + m.getPosition().toString());
                    marker = googleMap.addMarker(m);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                    mappa.onResume();
                    AggiornaIndirizzo();
                    }
            });
        }

        public void AggiornaIndirizzo(){
            RequestQueue request;
            request = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, "https://nominatim.openstreetmap.org/reverse?format=json&lat="+marker.getPosition().latitude+"&lon="+marker.getPosition().longitude, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Log.d("myapp","risposta: "+response.getString("name"));
                        indirizzoLbl.setText("Indirizzo: \n" + response.getString("display_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.d("mypp","ERRORE!");
                }
            });
            request.add(jsonObject);
        }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            LatLng posizione = new LatLng(loc.getLatitude(),loc.getLongitude());
            marker.setPosition(posizione);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 15));
            AggiornaIndirizzo();
            //devicePosition.
        }
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

}

