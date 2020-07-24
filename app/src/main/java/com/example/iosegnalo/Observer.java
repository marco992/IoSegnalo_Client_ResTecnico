package com.example.iosegnalo;

import android.os.Handler;

import java.util.Observable;
import java.util.TimerTask;
import java.util.Timer;

public class Observer extends Observable {
    final Handler handler = new Handler();
    Timer tmr;
    ControlloNuoveSegnalazioni C_S = new ControlloNuoveSegnalazioni();

    public Observer() {
        Timer timer = new Timer();
        timer.schedule( C_S, 5000, 10000 );
    }



    private void createTost() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                triggerObservers();
            }
        }, 5000);

    }

    private void triggerObservers() {
        setChanged();
        notifyObservers("Sono state inserite nuove segnalazioni!");
    }

    public class ControlloNuoveSegnalazioni extends TimerTask {
        public void run() {
            boolean updated = false;
            //inserire qui il codice relativo al controllo tra nuova lista segnalazioni e vecchia
            //if(updated)
            triggerObservers();
        }
    }
}

