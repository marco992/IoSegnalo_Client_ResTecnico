package com.example.iosegnalo.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;
import com.example.iosegnalo.View.SegnalaView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SegnalaActivityPresenter {
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    SegnalaView View;
    Utente Cittadino;

    public SegnalaActivityPresenter(SegnalaView view)
    {
        View = view;
        Sistema sys = Sistema.getIstance();
        Cittadino = sys.getUtente();
        ArrayList ListaTipologie = new ArrayList();
        ListaTipologie.add("Illuminazione pubblica");
        ListaTipologie.add("Dissesto su strada");
        ListaTipologie.add("Verde pubblico");
        ListaTipologie.add("Igiene/Decoro");
        ListaTipologie.add("Fognature");
        ListaTipologie.add("Dissesto su marciapiede");
        view.inserisciTipologie(ListaTipologie);
        view.inizializzaMappa();
    }

        public boolean controlloErroriInput(String Descrizione, String Recapito)
        {
            boolean errore=false;
            //controllo che la descrizione sia diversa da quella di default e diversa da una stringa vuota
            if(Descrizione.equals("") || Descrizione.equals("Si descriva qui il problema...") ) {
                View.mostraMessaggio("Si inserisca una descrizione alla segnalazione");
                errore=true;
            }
            //controllo inoltre che la dimensione della descrizione rientri nel 250 caratteri stabiliti in fase di progettazione
            if(Descrizione.length()>250) {
                View.mostraMessaggio("La descrizione può contentere al più 250 caratteri");
                errore=true;
            }

            //controllo che il recapito sia diverso dalla stringa di default, diverso da una stringa vuota
            if(Recapito.length()<10 || Recapito.equals("Tel/Cell") ) {
                View.mostraMessaggio("Si inserisca un recapito telefonico");
                errore=true;
            }
            //controllo che il recapito non contenga più di 10 caratteri
            if(Recapito.length()>11 ) {
                View.mostraMessaggio("Il recapito può contentere al più 11 caratteri");
                errore=true;
            }
            //mi assicuro che il campo recapito contenga solo valori numerici
            if (Pattern.matches("[a-zA-Z]+", Recapito)==true) {
                View.mostraMessaggio("Il recapito può contenere solo valori numerici");
                errore=true;
            }
            return errore;
        }

    public void clickInvio(String Descrizione, String Recapito, Integer Tipologia, Double Latitudine, Double Longitudine)
    {
        boolean Errore;
        Errore=controlloErroriInput(Descrizione, Recapito);
        if(Errore==false) {
            Sistema sys = Sistema.getIstance();
            int risposta;
            risposta = sys.inserisciSegnalazione(Tipologia, Descrizione, Cittadino.getId(), Latitudine, Longitudine, Recapito);
            if (risposta == 1) {
                View.mostraMessaggio("Segnalazione inviata!");
            } else {
                View.mostraMessaggio("Si è verificato un errore nell'invio della segnalazione!");
            }
        }
    }

    public void aggiornaIndirizzo(Context contesto, Marker marcatore)
    {
        RequestQueue request;
        request = Volley.newRequestQueue(contesto);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, "https://nominatim.openstreetmap.org/reverse?format=json&lat="+marcatore.getPosition().latitude+"&lon="+marcatore.getPosition().longitude, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    View.setIndirizzo("Indirizzo: \n" + response.getString("display_name"));
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

    public void controlloPermessi(Context contesto, LocationManager locationmanager, LocationListener locationlistener)
    {
        if (ActivityCompat.checkSelfPermission(contesto, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationmanager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationlistener);
            Log.d("myapp", "Eseguito");
        }
    }

}
