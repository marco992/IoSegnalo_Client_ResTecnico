package com.example.iosegnalo.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.iosegnalo.Presenter.SegnalaActivityPresenter;
import com.example.iosegnalo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SegnalaActivity extends AppCompatActivity implements SegnalaView {
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

    SegnalaActivityPresenter Presenter;
    LatLng posizione;

    public void setIndirizzo(String Indirizzo)
    {
        indirizzoLbl.setText(Indirizzo);
    }

    public void inizializzaMappa()
    {
        mappa.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap=googleMap;
                MarkerOptions m;
                m = new MarkerOptions();
                m.position(new LatLng(40.856033,14.248337)).title("Posizione segnalata");
                m.draggable(true);
                marker = googleMap.addMarker(m);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.856033,14.248337), 15));
                mappa.onResume();
                Presenter.aggiornaIndirizzo(getApplicationContext(),marker);

                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        Presenter.aggiornaIndirizzo(getApplicationContext(),marker);
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        // TODO Auto-generated method stub
                        //LatLng temp = marker.getPosition();
                        posizione=marker.getPosition();

                    }

                });

            }
        });
    }


    public void inserisciTipologie(ArrayList Tipologie)
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Tipologie);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipologiaLista.setAdapter(arrayAdapter);
    }

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
        tipologiaLista = findViewById(R.id.tipologiaLst);




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





        mappa = findViewById(R.id.mapView);
        mappa.onCreate(savedInstanceState);

        Presenter = new SegnalaActivityPresenter(this);

        //codice per rilevare la posizione GPS del dispositivo
        LocationManager locationmanager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationlistener = new MyLocationListener();

        Presenter.controlloPermessi(getApplicationContext(), locationmanager, locationlistener);

        segnalaButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Presenter.clickInvio(descrizioneText.getText().toString(),recapitoText.getText().toString(),tipologiaLista.getSelectedItemPosition(),posizione.latitude,posizione.longitude);
                                             }
                                         }
        );


        }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            posizione = new LatLng(loc.getLatitude(),loc.getLongitude());
            marker.setPosition(posizione);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 15));
            Presenter.aggiornaIndirizzo(getApplicationContext(),marker);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    @Override
    public void mostraMessaggio(String Messaggio)
    {
        Toast toast = Toast.makeText(getApplicationContext(), Messaggio, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



}

