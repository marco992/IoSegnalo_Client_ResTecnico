package com.example.iosegnalo.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.iosegnalo.Model.Segnalazione;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.R;
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
    ArrayList<Segnalazione> segnalazioni;
    int IDUtente;
    int i;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_segnalazioni);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //prelevo l'id dall'activity che mi ha richiamato (MainActivity)
        Intent intent = getIntent();
        IDUtente = Integer.parseInt(intent.getStringExtra("id"));

        Sistema sys = Sistema.getIstance();

        segnalazioni = sys.getSegnalazioniCittadino(IDUtente);

        if(segnalazioni!=null)
        aggiornaTabellaSegnalazioni(segnalazioni);

    }

    private void apriMappa(LatLng coordinate){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.d("iosegnalo","lat:"+coordinate.latitude+",lon:"+coordinate.longitude);
        intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + coordinate.latitude+","+coordinate.longitude));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void aggiornaTabellaSegnalazioni(ArrayList<Segnalazione> segnalazioni)
    {
        //aggiungo una riga per ogni segnalazione associata al cittadino

        TableLayout tl = (TableLayout) findViewById(R.id.tabella);

        for(i=0;i<segnalazioni.size();i++) {
            //nuova riga
            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(ContextCompat.getColor(this, R.color.sfondoRighe));
            tr.setPadding(5, 15, 5, 15);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //codice
            TextView codice = new TextView(this);
            codice.setText("  " + segnalazioni.get(i).getId());
            codice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(codice);
            //coordinate
            final LatLng coordinate = new LatLng(segnalazioni.get(i).getLatitudine(), segnalazioni.get(i).getLongitudine());
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
            //data ultima modifica
            TextView DataModifica = new TextView(this);
            DataModifica.setText("    " + segnalazioni.get(i).getDataModifica());
            DataModifica.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(DataModifica);
            //stato
            TextView Stato = new TextView(this);

            switch(segnalazioni.get(i).getStato())
            {
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

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

}
