package com.example.iosegnalo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    public void mostraNotifica()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "IoSegnalo", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Lo stato delle tue segnalazioni è stato aggiornato.");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icona)
                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("IoSegnalo")
                .setContentText("Lo stato delle tue segnalazioni è stato aggiornato.")
                .setContentInfo("Info");

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

}
