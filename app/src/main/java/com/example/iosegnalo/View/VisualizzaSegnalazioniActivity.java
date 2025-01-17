package com.example.iosegnalo.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.iosegnalo.Presenter.VisualizzaActivityPresenter;
import com.example.iosegnalo.R;
import com.google.android.gms.maps.model.LatLng;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class VisualizzaSegnalazioniActivity extends AppCompatActivity implements VisualizzaView {
    VisualizzaActivityPresenter Presenter;
    TableLayout table;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_segnalazioni);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        Presenter = new VisualizzaActivityPresenter(this);
        Presenter.creaTabella(getApplicationContext());

    }

    public void apriMappa(LatLng coordinate){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.d("iosegnalo","lat:"+coordinate.latitude+",lon:"+coordinate.longitude);
        intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + coordinate.latitude+","+coordinate.longitude));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void aggiungiRiga(TableRow TR)
    {
        table = (TableLayout) findViewById(R.id.tabella);
        table.addView(TR, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void mostraFinestraConferma(final int IDSegnalazione) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Creazione intervento");
        builder.setMessage("Vuoi prendere in carico la segnalazione? Lo stato passerà da APERTA a IN LAVORAZIONE.");

        // add the buttons
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Presenter.accettaIncarico(IDSegnalazione);
                Intent i = new Intent(getApplicationContext(), ResTecnicoActivity.class);
                startActivity(i);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void mostraMessaggio(String Messaggio){
        Toast toast=Toast.makeText(getApplicationContext(),Messaggio,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
