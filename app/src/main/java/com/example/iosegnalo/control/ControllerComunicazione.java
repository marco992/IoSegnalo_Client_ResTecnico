package com.example.iosegnalo.control;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.iosegnalo.boundary.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerComunicazione {
    public static String SERVER_IP = "192.168.1.5";
    public static final int SERVER_PORT = 7777;
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream inputO;
    InputStream InStream;
    Thread Thread1 = null;
    ArrayList MessaggioOutput;
    private String risp;

    public ControllerComunicazione(){
        MessaggioOutput = new ArrayList();
        //MessaggioOutput.add(0); //i=0
        //MessaggioOutput.add("Marco"); //i=1
        //MessaggioOutput.add("Vaiano"); //i=2

        //if(creaConnessione()==-1)
            //Toast.makeText(getApplicationContext(),"Problema di connessione al server!",Toast.LENGTH_SHORT).show();



    }

    class Thread1 implements Runnable {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            int tipo = -1;
            int tipoRichiesta = 0;
            String tipoUtente = null;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                OutputStream OutStream = socket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(OutStream);
                InStream = socket.getInputStream();
                inputO = new ObjectInputStream(InStream);
                switch(tipoRichiesta)
                {
                    case 0:
                        //.setText("Stato: Effettuo adesso l'invio della richiesta...");


                        objectOutputStream.writeObject(MessaggioOutput);
                        //objectOutputStream.flush();
                        break;
                    //case value2:
                    //...
                    //break;
                    // eventuali altri case
                    //case valueN:
                    //...
                    //default:
                }

                String tipoRisposta = new String();
                ArrayList messaggioIN = new ArrayList();
                messaggioIN = (ArrayList) inputO.readObject();
                tipoRisposta = messaggioIN.get(0).toString();
                if (tipoRisposta != null) {
                    //verifico il primo elemento dell'arraylist per distinguere i vari messaggi
                    switch (tipoRisposta) {
                        case "0":
                            risp=messaggioIN.get(1).toString();
                            break;
                        //case value2:
                        //...
                        //break;
                        // eventuali altri case
                        //case valueN:
                        //...
                        default:
                            break;
                    }
                }
                objectOutputStream.close();
                inputO.close();
                socket.close();
            } catch (Exception ex) {
                System.out.println("Errore!");
            }
        }
    }

    public void creaConnessione()
    {
        Thread1 = new Thread(new Thread1());
        Thread1.start();
        try {
            Thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void setMessaggio(ArrayList messaggio){
        int i=0;
        for(i=0; i<messaggio.size();i++){
            MessaggioOutput.add(messaggio.get(i));
        }
    }



    public String getRisposta()
    {
        return risp;
    }

}
