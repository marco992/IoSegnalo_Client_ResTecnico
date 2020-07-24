package com.example.iosegnalo.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;
import com.example.iosegnalo.R;
import com.example.iosegnalo.Model.Comunicazione;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText usernameTxt;
    EditText passwordTxt;
    Button accediBtn;
    Utente user;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        usernameTxt = findViewById(R.id.usernameText);
        passwordTxt = findViewById(R.id.passwordText);
        getSupportActionBar().hide();
        Button accediBtn = (Button) findViewById(R.id.accediButton);

        //evento associato alla pressione del tasto ACCEDI

        accediBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sistema sys = Sistema.getIstance();
                user = sys.getUtente(usernameTxt.getText().toString(),passwordTxt.getText().toString());
//Log.d("iosegnalo","ok:"+user.getTipo());
                Intent i;
                switch(user.getTipo())
                {
                    case 0:
                        //ho riconosciuto un utente cittadino
                        i = new Intent(MainActivity.this, CittadinoActivity.class);
                        i.putExtra("nomeutente", user.getUsername());
                        i.putExtra("id", Integer.toString(user.getId()));
                        startActivity(i);
                        break;
                    case 1:
                        //ho riconosciuto un utente responsabile squadra
                        i = new Intent(MainActivity.this, RespTecnicoActivity.class);
                        i.putExtra("nomeutente", user.getUsername());
                        startActivity(i);
                        break;
                    case 2:
                        //ho riconosciuto un utente amministratore di sistema
                    case -1:
                        //non ho riconosciuto un utente valido
                        Toast toast=Toast.makeText(getApplicationContext(),"Utente non registrato o username/password errata",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                }
            }

        });
        }


}
