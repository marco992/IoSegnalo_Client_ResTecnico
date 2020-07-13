package com.example.iosegnalo.boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iosegnalo.R;

public class CittadinoActivity extends AppCompatActivity {
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
        Intent intent = getIntent();
        nomeLabel.setText("Benvenuto "+intent.getStringExtra("nomeutente"));
        IDUtente = Integer.parseInt(intent.getStringExtra("id"));
        //evento associato alla pressione del tasto ACCEDI
        segnalaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(CittadinoActivity.this, SegnalaActivity.class);
                    startActivity(i);
                }
            }
            );
        visualizzaButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {

                                                 Intent i = new Intent(CittadinoActivity.this, VisualizzaSegnalazioniActivity.class);
                                                 i.putExtra("id", Integer.toString(IDUtente));
                                                 startActivity(i);
                                             }
                                         }
        );
    }
}
