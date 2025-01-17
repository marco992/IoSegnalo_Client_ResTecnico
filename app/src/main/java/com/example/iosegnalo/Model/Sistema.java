package com.example.iosegnalo.Model;

import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

public class Sistema {
    private static Sistema istance=null;
    ArrayList<Segnalazione> ListaSegnalazioni = new ArrayList<Segnalazione>();
    Utente ResponsabileTecnico;
    private static Comunicazione com;

    public static Sistema getIstance() {
        if(istance==null)
            istance = new Sistema();
        return istance;
    }

    public Sistema()
    {
        ListaSegnalazioni=new ArrayList<Segnalazione>();
        com = new ProxyComunicazione();
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
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();
        if(risposta.get(0).toString().equals("1"))
            return 1;
        else return -1;
    }

    public Utente getUtenteByUsrPass(String Username, String Password)
    {
            ResponsabileTecnico = new Utente();
            ArrayList risposta = new ArrayList();
            ArrayList richiesta = new ArrayList();
            richiesta.add(0);
            richiesta.add(Username);
            richiesta.add(Password);
            com.avviaComunicazione(richiesta);
            risposta = com.getRisposta();
            ResponsabileTecnico.setUsername(Username);
            ResponsabileTecnico.setPassword(Password);
            ResponsabileTecnico.setTipo(Integer.parseInt(risposta.get(0).toString()));
            ResponsabileTecnico.setId(Integer.parseInt(risposta.get(1).toString()));
            return ResponsabileTecnico;
    }

    public ArrayList<Segnalazione> prelevaSegnalazioniAperte()
    {
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(4);
        richiesta.add(0);
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();
        //ListaSegnalazioni.clear();
        ArrayList<Segnalazione> ListaSegnalazioniTemp=new ArrayList<Segnalazione>();
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
            ListaSegnalazioniTemp.add(s);
        }
        return ListaSegnalazioniTemp;
    }

    public ArrayList<Segnalazione> getSegnalazioni()
    {
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(3);
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();

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

    public Utente getUtente(){
        return ResponsabileTecnico;
    }

    public void updateListaSegnalazioni()
    {
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(4);
        richiesta.add(0);
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();
        ListaSegnalazioni.clear();
        ArrayList<Segnalazione> ListaSegnalazioniTemp=new ArrayList<Segnalazione>();
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
            ListaSegnalazioniTemp.add(s);
        }
        ListaSegnalazioni= (ArrayList<Segnalazione>) ListaSegnalazioniTemp.clone();
    }

    public boolean modificaStatoSegnalazione(int IDUtente, int IDSegnalazione, int NuovoStato){
        ArrayList richiesta = new ArrayList();
        ArrayList risposta = new ArrayList();
        richiesta.add(5);
        richiesta.add(IDUtente);
        richiesta.add(IDSegnalazione);
        richiesta.add(NuovoStato);
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();
        Log.d("myapp","Risposta modifica stato: " + risposta.toString());
        if(Integer.parseInt(risposta.get(0).toString())==1) return true;
        else return false;
    }

    public boolean verificaNuoveSegnalazioni()
    {
        ArrayList<Segnalazione> NuovaListaSegnalazioni = new ArrayList<Segnalazione>();
        NuovaListaSegnalazioni.clear();
        NuovaListaSegnalazioni = (ArrayList<Segnalazione>) prelevaSegnalazioniAperte().clone();
        int i;
        Log.d("myapp","Dim1: "+ ListaSegnalazioni.size() + "Dim2: " + NuovaListaSegnalazioni.size());
        if(NuovaListaSegnalazioni.size()>ListaSegnalazioni.size()) {
            ListaSegnalazioni = (ArrayList<Segnalazione>) NuovaListaSegnalazioni.clone();
            return true;
        }
        else return false;
    }

}