package com.example.iosegnalo.Presenter;

import android.view.Gravity;
import android.widget.Toast;

import com.example.iosegnalo.DaSpostare.Observer;
import com.example.iosegnalo.View.CittadinoView;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;

import java.util.Timer;
import java.util.TimerTask;

public class CittadinoActivityPresenter {
    CittadinoView View;
    Utente Cittadino;
    ControlloreNuoveSegnalazioni CS;

    public CittadinoActivityPresenter(CittadinoView view){
        View = view;
        Sistema sys = Sistema.getIstance();
        Cittadino = sys.getUtente();
        View.setID(Cittadino.getId());
        View.setUsername(Cittadino.getUsername());

        CS = new ControlloreNuoveSegnalazioni();

        Timer timer = new Timer();
        timer.schedule( CS, 10000, 10000 );
        //mostraNotifica();
    }
    public void clickSegnalaButton(){
        View.passaSegnalaActivity();
    }
    public void clickVisualizzaButton()
    {
        View.passaVisualizzaActivity();
    }

    public class ControlloreNuoveSegnalazioni extends TimerTask {
        public void run() {
            if(verificaSegnalazioni()==true)
            View.mostraNotifica();
        }

        public boolean verificaSegnalazioni()
        {
            //prelevare la lista delle segnalazioni, confrontarla con quella attuale. Se la data di ultima modifica di
            //ciascuna segnalazione è diversa, allora attivare la notifica
            return false;
        }
        
    }
}
