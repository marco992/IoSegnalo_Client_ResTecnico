package com.example.iosegnalo.Model;

import java.util.ArrayList;

public interface Comunicazione {
    ArrayList getRisposta();
    void avviaComunicazione(ArrayList Richiesta);
}
