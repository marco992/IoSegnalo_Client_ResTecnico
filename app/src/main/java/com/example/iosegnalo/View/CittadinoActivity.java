package com.example.iosegnalo.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iosegnalo.Presenter.CittadinoActivityPresenter;
import com.example.iosegnalo.R;

public class CittadinoActivity  extends AppCompatActivity implements CittadinoView  {
    TextView nomeLabel;
    int IDUtente;
    CittadinoActivityPresenter Presenter;

    @Override
    public void setUsername(String Username) {
        nomeLabel.setText("Benvenuto "+ Username);
    }

    @Override
    public void setID(Integer ID) {
        IDUtente=ID;
    }

    @Override
    public void passaSegnalaActivity(){
        Intent i = new Intent(CittadinoActivity.this, SegnalaActivity.class);
        startActivity(i);
    }

    @Override
    public void passaVisualizzaActivity(){
        Intent i = new Intent(CittadinoActivity.this, VisualizzaSegnalazioniActivity.class);
        startActivity(i);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cittadino);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button segnalaButton = (Button) findViewById(R.id.segnalaBtn);
        Button visualizzaButton = (Button) findViewById(R.id.visualizzaBtn);
        nomeLabel = findViewById(R.id.nomeLbl);

        Presenter = new CittadinoActivityPresenter(this);

        //evento associato al bottone Accedi
        segnalaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Presenter.clickSegnalaButton();
                }
            });

        //evento associato al bottone Visualizza segnalazioni
        visualizzaButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Presenter.clickVisualizzaButton();
                                             }
                                         });
    }
}
