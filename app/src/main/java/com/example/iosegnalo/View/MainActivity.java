package com.example.iosegnalo.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iosegnalo.Model.Utente;
import com.example.iosegnalo.Presenter.MainActivityPresenter;
import com.example.iosegnalo.R;

public class MainActivity extends AppCompatActivity implements MainView {

    EditText usernameTxt;
    EditText passwordTxt;
    Button accediBtn;
    Utente user;
    MainActivityPresenter Presenter;

    @Override
    public void mostraErrore(){
        Toast toast=Toast.makeText(getApplicationContext(),"Cittadino non registrato o username/password errata",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void passaCittadinoActivity(String Username, Integer ID){
        Intent i = new Intent(this, ResTecnicoActivity.class);
        startActivity(i);
    }

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

        Presenter = new MainActivityPresenter(this);

        //evento associato alla pressione del tasto ACCEDI

        accediBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Presenter.validaCredenziali(usernameTxt.getText().toString(),passwordTxt.getText().toString());
            }
        });
    }
}