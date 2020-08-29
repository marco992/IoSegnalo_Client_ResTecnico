package com.example.iosegnalo.DaSpostare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.iosegnalo.R;

import java.util.Observable;
import java.util.Observer;

public class RespTecnicoActivity extends AppCompatActivity  implements Observer {

    @Override
    public void update(Observable observable, Object data)
    {
        String s = (String) data;
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resp_tecnico);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        String nomeutente = new String(intent.getStringExtra("nomeutente"));

        com.example.iosegnalo.DaSpostare.Observer myObserver = new com.example.iosegnalo.DaSpostare.Observer();
        myObserver.addObserver(this);
    }


}

