package com.example.iosegnalo.Presenter;

import android.content.Intent;

import com.example.iosegnalo.View.MainView;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;

public class MainActivityPresenter {
    MainView Main;
    Utente Cittadino;

    public MainActivityPresenter(MainView main){
        Main=main;
    }

    public void validaCredenziali(String Username, String Password){
        Sistema sys = Sistema.getIstance();
        Cittadino = sys.getUtenteByUsrPass(Username,Password);
        Intent i;
        if(Cittadino.getTipo()==-1)
            Main.mostraErrore();
        else {
            Main.passaCittadinoActivity(Cittadino.getUsername(), Cittadino.getId());
        }
    }
}
