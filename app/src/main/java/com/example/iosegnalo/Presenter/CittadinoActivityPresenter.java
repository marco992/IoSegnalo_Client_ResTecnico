package com.example.iosegnalo.Presenter;

import com.example.iosegnalo.View.CittadinoView;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;

public class CittadinoActivityPresenter {
    CittadinoView View;
    Utente Cittadino;

    public CittadinoActivityPresenter(CittadinoView view){
        View = view;
        Sistema sys = Sistema.getIstance();
        Cittadino = sys.getUtente();
        View.setID(Cittadino.getId());
        View.setUsername(Cittadino.getUsername());

    }
    public void clickSegnalaButton(){
        View.passaSegnalaActivity();
    }
    public void clickVisualizzaButton()
    {
        View.passaVisualizzaActivity();
    }
}
