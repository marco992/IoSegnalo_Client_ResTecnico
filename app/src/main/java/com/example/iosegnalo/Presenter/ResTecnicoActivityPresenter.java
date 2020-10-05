package com.example.iosegnalo.Presenter;

import android.util.Log;

import com.example.iosegnalo.Model.Segnalazione;
import com.example.iosegnalo.View.ResTecnicoView;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ResTecnicoActivityPresenter {
    ResTecnicoView View;
    Utente ResponsabileTecnico;
    ControlloreNuoveSegnalazioni CS;
    private static ArrayList<Segnalazione> ListaSegnalazioni;


    public ResTecnicoActivityPresenter(ResTecnicoView view){
        View = view;
        Sistema sys = Sistema.getIstance();
        ResponsabileTecnico = sys.getUtente();
        View.setID(ResponsabileTecnico.getId());
        View.setUsername(ResponsabileTecnico.getUsername());


        CS = new ControlloreNuoveSegnalazioni();
        sys.updateListaSegnalazioni();

        Timer timer = new Timer();
        //dalay=ritardo della prima esecuzione, period=periodo di esecuzione del timer (300000 corrisponde a 5 minuti)
        timer.schedule( CS, 1000, 300000 );
    }
    public void clickVisualizzaButton()
    {
        View.passaVisualizzaActivity();
    }

    public class ControlloreNuoveSegnalazioni extends TimerTask {
        public void run() {
            Sistema sys = Sistema.getIstance();
            if(sys.verificaNuoveSegnalazioni()==true)
            View.mostraNotifica();
        }

    }
}
