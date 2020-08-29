package com.example.iosegnalo.View;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface SegnalaView {
    void setIndirizzo(String Indirizzo);
    void inserisciTipologie(ArrayList Tipologie);
    void mostraMessaggio(String Messaggio);
    void inizializzaMappa();
}
