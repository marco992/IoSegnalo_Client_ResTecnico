package com.example.iosegnalo.boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iosegnalo.R;
import com.example.iosegnalo.control.ControllerComunicazione;

import java.util.ArrayList;

public class CittadinoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cittadino);
        Button segnalaButton = (Button) findViewById(R.id.segnalaBtn);
        Button visualizzaButton = (Button) findViewById(R.id.visualizzaBtn);
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
                                                 startActivity(i);
                                             }
                                         }
        );
    }
}
