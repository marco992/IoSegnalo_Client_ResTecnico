package com.example.iosegnalo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iosegnalo.Presenter.ResTecnicoActivityPresenter;
import com.example.iosegnalo.R;

public class ResTecnicoActivity extends AppCompatActivity implements ResTecnicoView {
    TextView nomeLabel;
    int IDUtente;
    ResTecnicoActivityPresenter Presenter;

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
        Intent i = new Intent(ResTecnicoActivity.this, ResTecnicoActivity.class);
        startActivity(i);
    }

    @Override
    public void passaVisualizzaActivity(){
        Intent i = new Intent(ResTecnicoActivity.this, VisualizzaSegnalazioniActivity.class);
        startActivity(i);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resp_tecnico);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button visualizzaButton = (Button) findViewById(R.id.visualizzaBtn);
        nomeLabel = findViewById(R.id.nomeLbl);

        Presenter = new ResTecnicoActivityPresenter(this);


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
            notificationChannel.setDescription("Ci sono nuove segnalazioni aperte!");
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
                .setContentText("Ci sono nuove segnalazioni aperte!")
                .setContentInfo("Info");

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

}
