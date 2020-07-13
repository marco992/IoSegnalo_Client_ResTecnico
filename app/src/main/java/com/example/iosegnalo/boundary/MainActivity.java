package com.example.iosegnalo.boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iosegnalo.R;
import com.example.iosegnalo.control.ControllerComunicazione;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Thread Thread1 = null;
    Thread Thread2 = null;
    TextView statusLbl;
    EditText usernameTxt;
    EditText passwordTxt;
    Button accediBtn;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        statusLbl = findViewById(R.id.statusLbl);
        usernameTxt = findViewById(R.id.usernameText);
        passwordTxt = findViewById(R.id.passwordText);
        getSupportActionBar().hide();
        statusLbl.setText("Stato: ");
        Button accediBtn = (Button) findViewById(R.id.accediButton);
        //evento associato alla pressione del tasto ACCEDI
        accediBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList messaggio = new ArrayList();
                messaggio.add(0);
                messaggio.add(usernameTxt.getText().toString());
                messaggio.add(passwordTxt.getText().toString());
                ControllerComunicazione s = new ControllerComunicazione();

                s.setMessaggio(messaggio);
                s.creaConnessione();
                statusLbl.setText("Risposta server: " +  s.getRisposta().get(0).toString());
                Intent i;
                switch(Integer.parseInt(s.getRisposta().get(0).toString()))
                {
                    case 0:
                        i = new Intent(MainActivity.this, CittadinoActivity.class);
                        i.putExtra("nomeutente", usernameTxt.getText().toString());
                        i.putExtra("id", s.getRisposta().get(1).toString());
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(MainActivity.this, RespTecnicoActivity.class);
                        i.putExtra("nomeutente", usernameTxt.getText().toString());
                        startActivity(i);
                        break;
                    case 2:
                        //activity relativa all'amministratore di sistema
                    case -1:
                        Toast toast=Toast.makeText(getApplicationContext(),"Utente non registrato o username/password errata",Toast.LENGTH_SHORT);
                        toast.setMargin(25,25);
                        toast.show();
                        statusLbl.setText("Utente non registrato o username/password errata");
                        break;
                }
            }

        });
        }


}
