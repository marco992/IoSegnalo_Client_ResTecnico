package com.example.iosegnalo.Presenter;

import android.util.Log;

import com.example.iosegnalo.Model.Segnalazione;
import com.example.iosegnalo.View.CittadinoView;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CittadinoActivityPresenter {
    CittadinoView View;
    Utente Cittadino;
    ControlloreNuoveSegnalazioni CS;
    private static ArrayList<Segnalazione> ListaSegnalazioni;


    public CittadinoActivityPresenter(CittadinoView view){
        View = view;
        Sistema sys = Sistema.getIstance();
        Cittadino = sys.getUtente();
        View.setID(Cittadino.getId());
        View.setUsername(Cittadino.getUsername());


        CS = new ControlloreNuoveSegnalazioni();
        ListaSegnalazioni = new ArrayList<Segnalazione>();
        ListaSegnalazioni = (ArrayList<Segnalazione>) sys.getSegnalazioniCittadino(Cittadino.getId()).clone();

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
            if(verificaModificaSegnalazioni()==true)
            View.mostraNotifica();
        }

        public boolean verificaModificaSegnalazioni()
        {
            ArrayList<Segnalazione> NuovaListaSegnalazioni = new ArrayList<Segnalazione>();
            Sistema sys = Sistema.getIstance();
            NuovaListaSegnalazioni.clear();
            NuovaListaSegnalazioni = (ArrayList<Segnalazione>) sys.getSegnalazioniCittadino(Cittadino.getId()).clone();
            int i;
            Log.d("myapp","Dim1: "+ ListaSegnalazioni.size() + "Dim2: " + NuovaListaSegnalazioni.size());

            for(i=0;i<NuovaListaSegnalazioni.size();i++) {
                Log.d("myapp","(vecchia): "+ListaSegnalazioni.get(i).getDataModifica().toString() + "(nuova): "+NuovaListaSegnalazioni.get(i).getDataModifica().toString());

                if (NuovaListaSegnalazioni.get(i).getDataModifica().compareTo(ListaSegnalazioni.get(i).getDataModifica()) != 0) {
                    Log.d("myapp","OK!");

                    ListaSegnalazioni= (ArrayList<Segnalazione>) NuovaListaSegnalazioni.clone();
                    return true;
                }
                //prelevare la lista delle segnalazioni, confrontarla con quella attuale. Se la data di ultima modifica di
                //ciascuna segnalazione è diversa, allora attivare la notifica
            }
            return false;
        }


    }
}
