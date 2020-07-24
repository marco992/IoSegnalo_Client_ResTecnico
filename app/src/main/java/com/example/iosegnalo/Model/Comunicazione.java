package com.example.iosegnalo.Model;

import android.annotation.SuppressLint;
import android.util.Log;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Comunicazione {

    public static String SERVER_IP = "192.168.1.5";
    public static final int SERVER_PORT = 7777;
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream inputO;
    InputStream InStream;
    Thread Thread1 = null;
    ArrayList Messaggio;

    public Comunicazione(ArrayList messaggio)
    {
        Messaggio = new ArrayList(messaggio);
    }

    class Thread1 implements Runnable {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            int tipo = -1;
            int tipoRichiesta = -1;
            String tipoUtente = null;
            try {
                SocketAddress sockaddr = new InetSocketAddress(SERVER_IP, SERVER_PORT);
                Socket socket = new Socket();
                socket.connect(sockaddr, 10000);
                //socket = new Socket(SERVER_IP, SERVER_PORT);
                OutputStream OutStream = socket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(OutStream);
                InStream = socket.getInputStream();
                inputO = new ObjectInputStream(InStream);
                tipoRichiesta=Integer.parseInt(Messaggio.get(0).toString());
                switch(tipoRichiesta)
                {
                    case 0:
                        //richiesta di login
                        objectOutputStream.writeObject(Messaggio);
                        break;
                    case 1:
                        //richiesta di visualizzazione segnalazioni cittadino
                        objectOutputStream.writeObject(Messaggio);
                    break;
                    case 2:
                        //richiesta di aggiunta nuova segnalazione
                        objectOutputStream.writeObject(Messaggio);
                        break;
                }

                String tipoRisposta = new String();
                ArrayList messaggioIN = new ArrayList();
                messaggioIN = new ArrayList( (ArrayList) inputO.readObject());
                tipoRisposta = messaggioIN.get(0).toString();

                if (tipoRisposta != null) {
                    //verifico il primo elemento dell'arraylist per distinguere i vari messaggi
                    switch (tipoRisposta) {
                        case "0":
                            Messaggio.clear();
                            Messaggio.add(messaggioIN.get(1).toString());
                            Messaggio.add(messaggioIN.get(2).toString());
                            break;
                        case "1":
                            Messaggio.clear();
                            int i;
                            for(i=1;i<messaggioIN.size();i++)
                            {
                                Messaggio.add(messaggioIN.get(i).toString());
                            }
                            break;
                        case "2":
                            Messaggio.clear();
                            Messaggio.add(messaggioIN.get(1).toString());
                        break;
                    }
                }
                objectOutputStream.close();
                inputO.close();
                socket.close();
            } catch (Exception ex) {
                Log.d("iosegnalo","Errore!");
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
            Messaggio.add(messaggio.get(i));
        }
    }

    public ArrayList getRisposta()
    {
        return Messaggio;
    }

}
