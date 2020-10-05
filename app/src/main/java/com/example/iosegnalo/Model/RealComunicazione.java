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

public class RealComunicazione implements Comunicazione {

    public static String SERVER_IP = "192.168.1.5";
    public static final int SERVER_PORT = 7777;
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream inputO;
    InputStream InStream;
    Thread Thread1 = null;
    ArrayList Messaggio;


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
                objectOutputStream.writeObject(Messaggio);
                ArrayList messaggioIN = new ArrayList();
                messaggioIN = new ArrayList( (ArrayList) inputO.readObject());
                Messaggio.clear();
                int i;
                for(i=1;i<messaggioIN.size();i++)
                {
                    Messaggio.add(messaggioIN.get(i).toString());
                }
                objectOutputStream.close();
                inputO.close();
                socket.close();
            } catch (Exception ex) {
                Log.d("iosegnalo","Errore!");
            }
        }
    }

    public void avviaComunicazione(ArrayList Richiesta)
    {
        setRichiesta(Richiesta);
        Thread1 = new Thread(new Thread1());
        Thread1.start();
        try {
            Thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setRichiesta(ArrayList Richiesta)
    {
        Messaggio= (ArrayList) Richiesta.clone();
    }


    public ArrayList getRisposta()
    {
        return Messaggio;
    }

}
