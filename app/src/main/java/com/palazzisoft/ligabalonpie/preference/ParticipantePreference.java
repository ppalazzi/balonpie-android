package com.palazzisoft.ligabalonpie.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.palazzisoft.ligabalonpie.dto.Participante;

public class ParticipantePreference {

    private static final String PARTICIPANTE_PREFERENCE_KEY = "PERSON_PREFERENCE_KEY";
    private final Context context;

    public ParticipantePreference(Context context) {
        this.context = context;
    }

    public void saveParticipante(Participante participante) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String json = new Gson().toJson(participante);
        edit.putString(PARTICIPANTE_PREFERENCE_KEY,json);
        edit.commit();
    }

    public Participante getParticipante() {
        String json = getSharedPreferences().getString(PARTICIPANTE_PREFERENCE_KEY,null);
        if(json == null){
            return new Participante();
        }
        Participante participante = new Gson().fromJson(json, Participante.class);
        return participante;
    }

    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }
}
