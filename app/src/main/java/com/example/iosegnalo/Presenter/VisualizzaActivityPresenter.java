package com.example.iosegnalo.Presenter;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

//import androidx.appcompat.app.AlertDialog;
//import android.support.v7.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.iosegnalo.Model.Segnalazione;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;
import com.example.iosegnalo.R;
import com.example.iosegnalo.View.VisualizzaView;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class VisualizzaActivityPresenter {
    VisualizzaView View;
    Utente ResponsabileTecnico;

    public VisualizzaActivityPresenter(VisualizzaView view) {
        View = view;
        Sistema sys = Sistema.getIstance();
        ResponsabileTecnico = sys.getUtente();

    }

    public void accettaIncarico()
    {
        Sistema sys = Sistema.getIstance();
        ResponsabileTecnico = sys.getUtente();
        sys.modificaStatoSegnalazione(ResponsabileTecnico.getId(),1);
    }


    public void creaTabella(Context contesto) {
        Sistema sys = Sistema.getIstance();

        ArrayList<Segnalazione> Segnalazioni = (ArrayList<Segnalazione>) sys.PrelevaSegnalazioniAperte().clone();
        if (Segnalazioni.isEmpty() == false) {

            int i;
            for (i = 0; i < Segnalazioni.size(); i++) {
                //nuova riga
                TableRow tr = new TableRow(contesto);
                tr.setBackgroundColor(ContextCompat.getColor(contesto, R.color.sfondoRighe));
                tr.setPadding(5, 15, 5, 15);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                //codice
                TextView codice = new TextView(contesto);

                //già era definito codice, in presenter
                codice.setOnClickListener(new View.OnClickListener() {
                    //facciamo un override del metodo onClick definito come interfaccia nella classe textview di android
                    @Override
                    public void onClick(View v) {
                        //definiamo cosa deve succedere quando premiamo il tasto
                        //deve attivarsi quel messaggio di conferma, quindi dovrai richiamare qui quel metodo che hai messo nella view
                        //ovviamente qui vediamo solo le interfacce, quindi devi aggiungere prima l'interfaccia in "VisualizzaView"
                        View.mostraFinestraConferma();
                    }

                });

                codice.setText("  " + Segnalazioni.get(i).getId());
                codice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(codice);
                //coordinate
                final LatLng coordinate = new LatLng(Segnalazioni.get(i).getLatitudine(), Segnalazioni.get(i).getLongitudine());
                Button Mappa = new Button(contesto);
                Mappa.setBackgroundResource(R.drawable.google_maps_icon_130921);
                Mappa.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View.apriMappa(coordinate);
                    }
                });
                Mappa.setWidth(50);
                Mappa.setHeight(65);
                Mappa.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                Mappa.getLayoutParams().width = 150;
                Mappa.getLayoutParams().height = 200;


                tr.addView(Mappa);
                //data ultima modifica
                TextView DataModifica = new TextView(contesto);
                DataModifica.setText("    " + Segnalazioni.get(i).getDataModifica());
                DataModifica.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(DataModifica);
                //stato
                TextView Stato = new TextView(contesto);

                switch (Segnalazioni.get(i).getStato()) {
                    case 0:
                        Stato.setText("    Aperta");
                        break;
                    case 1:
                        Stato.setText("    In lavorazione");
                        break;
                    case 2:
                        Stato.setText("    Chiusa");
                        break;
                }

                Stato.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(Stato);

                View.aggiungiRiga(tr);
            }
        }
    }
}