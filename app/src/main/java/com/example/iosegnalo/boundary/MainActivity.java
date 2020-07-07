package com.example.iosegnalo.boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iosegnalo.R;
import com.example.iosegnalo.SocketManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Thread Thread1 = null;
    Thread Thread2 = null;
    TextView statusLbl;
    EditText usernameTxt;
    EditText passwordTxt;
    Button accediBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                SocketManager s = new SocketManager();

                statusLbl.setText("Risposta server: " +  s.getRisposta());
                if (s.getRisposta().equals("1"))
                {
                    Intent i = new Intent(MainActivity.this, CittadinoActivity.class);
                    startActivity(i);
                }
            }

        });
        }


}
