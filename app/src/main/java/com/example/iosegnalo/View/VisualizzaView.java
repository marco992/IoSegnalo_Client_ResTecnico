package com.example.iosegnalo.View;

import android.widget.TableRow;
import com.google.android.gms.maps.model.LatLng;

public interface VisualizzaView {
    void aggiungiRiga(TableRow TR);
    void apriMappa(LatLng Coordinate);
    public void mostraFinestraConferma(int IDSegnalazione);
}
