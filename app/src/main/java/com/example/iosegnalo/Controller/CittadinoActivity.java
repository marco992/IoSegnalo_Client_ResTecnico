package com.example.iosegnalo.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.R;

import java.util.Observable;
import java.util.Observer;

public class CittadinoActivity  extends AppCompatActivity  {

    int IDUtente;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cittadino);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button segnalaButton = (Button) findViewById(R.id.segnalaBtn);
        Button visualizzaButton = (Button) findViewById(R.id.visualizzaBtn);
        TextView nomeLabel = findViewById(R.id.nomeLbl);

        //prelevo il nome utente e l'id dall'activity che mi ha richiamato (MainActivity)
        Intent intent = getIntent();
        nomeLabel.setText("Benvenuto "+intent.getStringExtra("nomeutente"));
        IDUtente = Integer.parseInt(intent.getStringExtra("id"));

        //evento associato al bottone Accedi
        segnalaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CittadinoActivity.this, SegnalaActivity.class);
                i.putExtra("id", Integer.toString(IDUtente));
                startActivity(i);
                }
            });

        //evento associato al bottone Visualizza segnalazioni
        visualizzaButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {

                                                 Intent i = new Intent(CittadinoActivity.this, VisualizzaSegnalazioniActivity.class);
                                                 i.putExtra("id", Integer.toString(IDUtente));
                                                 startActivity(i);
                                             }
                                         });
    }
}
