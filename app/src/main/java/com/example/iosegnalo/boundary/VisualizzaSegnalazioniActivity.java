package com.example.iosegnalo.boundary;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import com.example.iosegnalo.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class VisualizzaSegnalazioniActivity extends AppCompatActivity {
    ArrayList segnalazioni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_segnalazioni);
        //da inserire: chiamata remota su socket per la ricezione della lista di segnalazioni
        segnalazioni = new ArrayList();
        segnalazioni.add("20");
        double lat=10.334234;
        double lon=13.2020;
        segnalazioni.add(lat);
        segnalazioni.add(lon);
        segnalazioni.add("10.07.2020");
        segnalazioni.add("Aperto");

        TableLayout tl = (TableLayout) findViewById(R.id.tabella);

        //int i=0;
       // for(i=0;i<segnalazioni.size();i++){
//
        //}

        //aggiungo una nuova riga

        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(ContextCompat.getColor(this,R.color.sfondoRighe));
        tr.setPadding(5,15,5,5);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        /* Create a Button to be the row-content. */
        TextView codice = new TextView(this);
        codice.setText("  "+segnalazioni.get(0).toString());
        codice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        /* Add Button to row. */
        tr.addView(codice);
        Button Mappa = new Button(this);
        Mappa.setBackgroundResource(R.drawable.google_maps_icon_130921);
        //ViewGroup.LayoutParams params = Mappa.getLayoutParams();
       // params.width = 100;
        //Mappa.setLayoutParams(params);
// Changes the height and width to the specified *pixels*

        //Mappa.setText(segnalazioni.get(0).toString());

        //Mappa.setLayoutParams(new TableRow.LayoutParams());
        Mappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apriMappa(new LatLng((double)segnalazioni.get(1),(double)segnalazioni.get(2)));
            }
        });
        Mappa.setWidth(50);
        Mappa.setHeight(65);
        Mappa.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        tr.addView(Mappa);

        TextView DataModifica = new TextView(this);
        DataModifica.setText("    "+segnalazioni.get(3).toString());
        DataModifica.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        tr.addView(DataModifica);

        TextView Stato = new TextView(this);
        Stato.setText("      "+segnalazioni.get(4).toString());
        Stato.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        tr.addView(Stato);

//tr.setBackgroundResource(R.drawable.sf_gradient_03);
        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


    }

    private void apriMappa(LatLng coordinate){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + coordinate.latitude+","+coordinate.longitude));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
