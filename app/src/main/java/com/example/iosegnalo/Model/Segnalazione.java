package com.example.iosegnalo.Model;

import java.sql.Date;

public class Segnalazione {

    private int id;
    private int tipologia;
    private String descrizione;
    private int cittadino;
    private int stato;
    private String nota;
    private Date DataModifica;
    private double latitudine;
    private double longitudine;
    private String recapito;

    public Segnalazione() {
        super();
    }

    public Segnalazione(int id, int tipologia, String descrizione, int iDcittadino, String nota, int stato, Date dataModifica,
                        double latitudine, double longitudine, String recapito) {
        super();
        this.id = id;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.cittadino = iDcittadino;
        this.nota = nota;
        this.stato = stato;
        DataModifica = dataModifica;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.recapito = recapito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String Nota) {
        this.nota = Nota;
    }

    public String getRecapito() {
        return recapito;
    }

    public void setRecapito(String Recapito) {
        this.recapito = Recapito;
    }

    public int getTipologia() {
        return tipologia;
    }

    public void setTipologia(int tipologia) {
        this.tipologia = tipologia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getIDcittadino() {
        return cittadino;
    }

    public void setIDcittadino(int iDcittadino) {
        cittadino = iDcittadino;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public Date getDataModifica() {
        return DataModifica;
    }

    public void setDataModifica(Date dataModifica) {
        DataModifica = dataModifica;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

}