package com.example.iosegnalo.Controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.R;
import com.example.iosegnalo.Model.Comunicazione;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.android.volley.RequestQueue;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SegnalaActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    MapView mappa;
    private GoogleMap mMap;
    Marker marker;
    Spinner tipologiaLista;
    TextView indirizzoLbl;
    TextView coordinateLabel;
    Location devicePosition;
    EditText recapitoText;
    EditText descrizioneText;
    Button segnalaButton;
    ArrayList messaggio;

    LatLng posizione;
    int IDUtente;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnala);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().hide();

        recapitoText = findViewById(R.id.recapitoTxt);
        indirizzoLbl = findViewById(R.id.indirizzoLabel);
        descrizioneText= findViewById(R.id.descrizioneTxt);
        segnalaButton = findViewById(R.id.inviaBtn);

        Intent intent = getIntent();

        IDUtente = Integer.parseInt(intent.getStringExtra("id"));


        recapitoText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                recapitoText.setText("");
                return false;
            }
        });
        descrizioneText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                descrizioneText.setText("");
                return false;
            }
        });

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

        /*
        tipologiaLista.add
*/

            mappa.onCreate(savedInstanceState);
            mappa.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    MarkerOptions m;
                    m = new MarkerOptions();
                    LatLng napoli = new LatLng(40.856033,14.248337);
                    posizione=napoli;
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
                            posizione=marker.getPosition();



                        }
                    });
                    LatLng coordinates = new LatLng(40.856033,14.248337);
                    coordinateLabel.setText("Coordinate:" + m.getPosition().toString());
                    marker = googleMap.addMarker(m);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posizione, 15));
                    mappa.onResume();
                    AggiornaIndirizzo();
                    }
            });

        segnalaButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 int ControlloErrori=-1;
                                                 ControlloErrori=controlloErrori(descrizioneText.getText().toString(),recapitoText.getText().toString());
                                                 if(ControlloErrori==1) {
                                                     Sistema sys = Sistema.getIstance();
                                                     int risposta;
                                                     risposta = sys.inserisciSegnalazione(tipologiaLista.getSelectedItemPosition(), descrizioneText.getText().toString(), IDUtente, posizione.latitude, posizione.longitude, recapitoText.getText().toString());
                                                     if (risposta == 1) {
                                                         Toast toast = Toast.makeText(getApplicationContext(), "Segnalazione inviata!", Toast.LENGTH_SHORT);
                                                         toast.setGravity(Gravity.CENTER, 0, 0);
                                                         toast.show();
                                                     } else {
                                                         Toast toast = Toast.makeText(getApplicationContext(), "Si è verificato un errore nell'invio della segnalazione!", Toast.LENGTH_SHORT);
                                                         toast.setGravity(Gravity.CENTER, 0, 0);
                                                         toast.show();
                                                     }
                                                 }
                                             }
                                         }
        );


        }

        public void AggiornaIndirizzo(){
            RequestQueue request;
            request = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, "https://nominatim.openstreetmap.org/reverse?format=json&lat="+marker.getPosition().latitude+"&lon="+marker.getPosition().longitude, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
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
            posizione = new LatLng(loc.getLatitude(),loc.getLongitude());
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

    public int controlloErrori(String Descrizione, String Recapito)
    {
        //controllo che la descrizione sia diversa da quella di default e diversa da una stringa vuota
        if(Descrizione.equals("") || Descrizione.equals("Si descriva qui il problema...") ) {
            Toast toast = Toast.makeText(getApplicationContext(), "Si inserisca una descrizione alla segnalazione", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return -1;
        }
        //controllo inoltre che la dimensione della descrizione rientri nel 250 caratteri stabiliti in fase di progettazione
        if(Descrizione.length()>250) {
            Toast toast = Toast.makeText(getApplicationContext(), "La descrizione può contentere al più 250 caratteri", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return -1;
        }

        //controllo che il recapito sia diverso dalla stringa di default, diverso da una stringa vuota
        if(recapitoText.length()<10 || recapitoText.equals("Tel/Cell") ) {
            Toast toast = Toast.makeText(getApplicationContext(), "Si inserisca un recapito telefonico", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return -1;
        }
        //controllo che il recapito non contenga più di 10 caratteri
        if(recapitoText.length()>11 ) {
            Toast toast = Toast.makeText(getApplicationContext(), "Il recapito può contentere al più 11 caratteri", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return -1;
        }
        //mi assicuro che il campo recapito contenga solo valori numerici
        if (Pattern.matches("[a-zA-Z]+", recapitoText.getText()) == true) {
            Toast toast = Toast.makeText(getApplicationContext(), "Il recapito può contenere solo valori numerici", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return -1;
        }
        //se tutti i controlli vengono superati, verrà restituito il valore intero 1
        return 1;
    }

}

