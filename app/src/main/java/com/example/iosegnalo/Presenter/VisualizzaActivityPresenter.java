package com.example.iosegnalo.Presenter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.iosegnalo.Model.Segnalazione;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;
import com.example.iosegnalo.R;
import com.example.iosegnalo.View.VisualizzaView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class VisualizzaActivityPresenter {
    VisualizzaView View;
    Utente Cittadino;

    public VisualizzaActivityPresenter(VisualizzaView view)
    {
        View = view;
        Sistema sys = Sistema.getIstance();
        Cittadino = sys.getUtente();

    }


    public void creaTabella(Context contesto)
    {
        Sistema sys = Sistema.getIstance();

        ArrayList<Segnalazione> Segnalazioni = sys.getSegnalazioniCittadino(Cittadino.getId());
        if(Segnalazioni.isEmpty()==false) {

            int i;
            for (i = 0; i < Segnalazioni.size(); i++) {
                //nuova riga
                TableRow tr = new TableRow(contesto);
                tr.setBackgroundColor(ContextCompat.getColor(contesto, R.color.sfondoRighe));
                tr.setPadding(5, 15, 5, 15);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                //codice
                TextView codice = new TextView(contesto);
                codice.setText("  " + Segnalazioni.get(i).getId());
                codice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(codice);
                //coordinate
                final LatLng coordinate = new LatLng(Segnalazioni.get(i).getLatitudine(), Segnalazioni.get(i).getLongitudine());
                Button Mappa = new Button(contesto);
                Mappa.setBackgroundResource(R.drawable.google_maps_icon_130921);
                Mappa.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View.apriMappa(coordinate);
                    }
                });
                Mappa.setWidth(50);
                Mappa.setHeight(65);
                Mappa.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                Mappa.getLayoutParams().width = 150;
                Mappa.getLayoutParams().height = 200;


                tr.addView(Mappa);
                //data ultima modifica
                TextView DataModifica = new TextView(contesto);
                DataModifica.setText("    " + Segnalazioni.get(i).getDataModifica());
                DataModifica.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(DataModifica);
                //stato
                TextView Stato = new TextView(contesto);

                switch (Segnalazioni.get(i).getStato()) {
                    case 0:
                        Stato.setText("    Aperta");
                        break;
                    case 1:
                        Stato.setText("    In lavorazione");
                        break;
                    case 2:
                        Stato.setText("    Chiusa");
                        break;
                }

                Stato.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(Stato);

                View.aggiungiRiga(tr);
            }
        }
    }
}
