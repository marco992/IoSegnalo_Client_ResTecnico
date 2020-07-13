package com.example.iosegnalo.boundary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import com.example.iosegnalo.R;
import com.example.iosegnalo.control.ControllerComunicazione;
import com.google.android.gms.maps.model.LatLng;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class VisualizzaSegnalazioniActivity extends AppCompatActivity {
    ArrayList segnalazioni;
    int IDUtente;
    int i;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_segnalazioni);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //da inserire: chiamata remota su socket per la ricezione della lista di segnalazioni
        Intent intent = getIntent();
        IDUtente = Integer.parseInt(intent.getStringExtra("id"));

        ArrayList messaggio = new ArrayList();


        ControllerComunicazione s = new ControllerComunicazione();
        //messaggio di visualizzazione
       messaggio.add(1);
        messaggio.add(IDUtente);
        s.setMessaggio(messaggio);
        s.creaConnessione();

        segnalazioni = new ArrayList(s.getRisposta());
Log.d("myapp",Integer.toString(s.getRisposta().size()));
        TableLayout tl = (TableLayout) findViewById(R.id.tabella);

        //aggiungo una nuova riga

        for(i=0;i<segnalazioni.size();i=i+5) {
            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(ContextCompat.getColor(this, R.color.sfondoRighe));
            tr.setPadding(5, 15, 5, 15);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView codice = new TextView(this);
            codice.setText("  " + segnalazioni.get(i).toString());
            codice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(codice);

            final LatLng coordinate = new LatLng(Double.parseDouble(segnalazioni.get(i+1).toString()), Double.parseDouble(segnalazioni.get(i+2).toString()));
            Button Mappa = new Button(this);
            Mappa.setBackgroundResource(R.drawable.google_maps_icon_130921);
            Mappa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apriMappa(coordinate);
                }
            });
            Mappa.setWidth(50);
            Mappa.setHeight(65);
            Mappa.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            Mappa.getLayoutParams().width=150;
            Mappa.getLayoutParams().height=200;


            tr.addView(Mappa);

            TextView DataModifica = new TextView(this);
            DataModifica.setText("    " + segnalazioni.get(i+3).toString());
            DataModifica.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(DataModifica);

            TextView Stato = new TextView(this);
            switch(segnalazioni.get(i+4).toString())
            {
                    case "0":
                    Stato.setText("    Aperta");
                    break;
                    case "1":
                    Stato.setText("    In lavorazione");
                    break;
                    case "2":
                        Stato.setText("    Chiusa");
                    break;
            }


            Stato.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(Stato);

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }

    private void apriMappa(LatLng coordinate){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.d("myapp","lat:"+coordinate.latitude+",lon:"+coordinate.longitude);
        intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + coordinate.latitude+","+coordinate.longitude));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
