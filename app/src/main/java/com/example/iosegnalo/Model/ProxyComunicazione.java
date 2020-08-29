package com.example.iosegnalo.Model;

import android.util.Log;

import java.util.ArrayList;

public class ProxyComunicazione implements Comunicazione {
    private RealComunicazione realComunicazione;
    private static ArrayList Mess;

    public ProxyComunicazione(){
        Mess = new ArrayList();
    }
    public ArrayList getRisposta(){
            if(realComunicazione!=null)
                return realComunicazione.getRisposta();
            else
                return null;
    }
    public void avviaComunicazione(ArrayList Richiesta)
    {
        if(!comparaMessaggi(Mess,Richiesta)) {
            Mess.clear();
            int i;
            //Messaggio= (ArrayList) Richiesta.clone();
            for(i=0;i<Richiesta.size();i++) {
                Log.d("myapp","Mex:"+Richiesta.get(i).toString());

                Mess.add(Richiesta.get(i).toString());
                Log.d("myapp","Mex2:"+Mess.get(i).toString());

            }
            realComunicazione = new RealComunicazione();
            realComunicazione.avviaComunicazione(Mess);
        }
    }
    public boolean comparaMessaggi(ArrayList VecchiaRichiesta, ArrayList NuovaRichiesta)
    {
        if((NuovaRichiesta.size())!=VecchiaRichiesta.size()) {
            return false;
        }
        else
        {
            int i;
            for (i=0;i<NuovaRichiesta.size();i++){
                if(VecchiaRichiesta.get(i).toString().compareTo(NuovaRichiesta.get(i).toString())!=0){
                    Log.d("myapp","Stringa1(vecchia): "+VecchiaRichiesta.get(i).toString() + "Stringa2(nuova): "+NuovaRichiesta.get(i).toString());
                    return false;
                }
            }
        }
        return true;
    }
}
