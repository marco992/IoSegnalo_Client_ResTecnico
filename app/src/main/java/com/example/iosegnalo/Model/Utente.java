package com.example.iosegnalo.Model;

public class Utente {

    public Utente() {
        super();
    }

    private int id;
    private String username;
    private String password;
    private int tipo;

    public Utente(String username, String password, int tipo) {
        super();
        this.username = username;
        this.password = password;
        this.tipo=tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}