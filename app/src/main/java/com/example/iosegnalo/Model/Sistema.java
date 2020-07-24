package com.example.iosegnalo.Model;

import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

public class Sistema {
    private static Sistema istance=null;
    ArrayList<Segnalazione> ListaSegnalazioni = new ArrayList<Segnalazione>();

    public static Sistema getIstance() {
        if(istance==null)
            istance = new Sistema();
        return istance;
    }

    public Sistema()
    {
        ListaSegnalazioni=new ArrayList<Segnalazione>();
    }

    public int inserisciSegnalazione(int Tipologia, String Descrizione, int IDUtente, Double latitudine, Double Longitudine, String Recapito)
    {
        Segnalazione s = new Segnalazione();
        //inserisci prima tutto in segnalazione
        s.setTipologia(Tipologia);
        s.setDescrizione(Descrizione);
        s.setIDcittadino(IDUtente);
        s.setLatitudine(latitudine);
        s.setLongitudine(Longitudine);
        s.setRecapito(Recapito);
        //inserisci poi tutto in un messaggio
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(2);
        richiesta.add(s.getTipologia());
        richiesta.add(s.getDescrizione());
        richiesta.add(s.getIDcittadino());
        richiesta.add(s.getLatitudine());
        richiesta.add(s.getLongitudine());
        richiesta.add(s.getRecapito());
        Comunicazione c = new Comunicazione(richiesta);
        c.creaConnessione();
        risposta=c.getRisposta();
        if(risposta.get(0).toString().equals("1"))
            return 1;
        else return -1;
    }

    public Utente getUtente(String Username, String Password)
    {
        Utente u = new Utente();

        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(0);
        richiesta.add(Username);
        richiesta.add(Password);
        Comunicazione c = new Comunicazione(richiesta);
        c.creaConnessione();
        risposta=c.getRisposta();
        u.setUsername(Username);
        u.setPassword(Password);
        u.setTipo(Integer.parseInt(risposta.get(0).toString()));
        u.setId(Integer.parseInt(risposta.get(1).toString()));
        return u;
    }

    public ArrayList<Segnalazione> getSegnalazioniCittadino(int IDUtente)
    {
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(1);
        richiesta.add(IDUtente);
        Comunicazione c = new Comunicazione(richiesta);
        c.creaConnessione();
        risposta=c.getRisposta();

        int i;
        for(i=0;i<risposta.size();i=i+5) {
            Segnalazione s = new Segnalazione();
            s.setId(Integer.parseInt(risposta.get(i).toString()));
            //s.getDescrizione();
            s.setDataModifica(Date.valueOf(risposta.get(i+3).toString()));
            //s.setIDcittadino();
            s.setLatitudine(Double.parseDouble(risposta.get(i+1).toString()));
            s.setLongitudine(Double.parseDouble(risposta.get(i+2).toString()));
            //s.getNota();
            //s.getRecapito();
            s.setStato(Integer.parseInt(risposta.get(i+4).toString()));
            ListaSegnalazioni.add(s);
        }
        return ListaSegnalazioni;
    }

    public ArrayList<Segnalazione> getSegnalazioni()
    {
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(3);
        Comunicazione c = new Comunicazione(richiesta);
        c.creaConnessione();
        risposta=c.getRisposta();

        int i;
        for(i=0;i<risposta.size();i=i+5) {
            Segnalazione s = new Segnalazione();
            s.setId(Integer.parseInt(risposta.get(i).toString()));
            //s.getDescrizione();
            s.setDataModifica(Date.valueOf(risposta.get(i+3).toString()));
            //s.setIDcittadino();
            s.setLatitudine(Double.parseDouble(risposta.get(i+1).toString()));
            s.setLongitudine(Double.parseDouble(risposta.get(i+2).toString()));
            //s.getNota();
            //s.getRecapito();
            s.setStato(Integer.parseInt(risposta.get(i+4).toString()));
            ListaSegnalazioni.add(s);
        }
        return ListaSegnalazioni;
    }

}