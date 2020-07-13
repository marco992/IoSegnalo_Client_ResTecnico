package com.example.iosegnalo.control;

import android.annotation.SuppressLint;
import android.util.Log;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    private ArrayList out;

    public ControllerComunicazione(){
        MessaggioOutput = new ArrayList();
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
                    case 1:
                        objectOutputStream.writeObject(MessaggioOutput);
                    break;

                }

                String tipoRisposta = new String();
                ArrayList messaggioIN = new ArrayList();
                messaggioIN = (ArrayList) inputO.readObject();
                tipoRisposta = messaggioIN.get(0).toString();

                if (tipoRisposta != null) {
                    //verifico il primo elemento dell'arraylist per distinguere i vari messaggi
                    switch (tipoRisposta) {
                        case "0":
                            out = new ArrayList();
                            out.add(messaggioIN.get(1).toString());
                            out.add(messaggioIN.get(2).toString());
                            break;
                        case "1":
                            out = new ArrayList();
                            int i=0;
                            Log.d("myapp","Prova:"+messaggioIN.size());
                            for(i=1;i<messaggioIN.size();i++)
                            {
                                out.add(messaggioIN.get(i).toString());
                            }
                        break;
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

    public ArrayList getRisposta()
    {
        return out;
    }

}
