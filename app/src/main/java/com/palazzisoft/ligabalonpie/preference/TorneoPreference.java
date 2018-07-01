package com.palazzisoft.ligabalonpie.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.dto.Torneo;

public class TorneoPreference {

    private static final String TORNEO_PREFERENCE_KEY = "TORNEO_PREFERENCE_KEY";
    private final Context context;

    public TorneoPreference(final Context context) {
        this.context = context;
    }

    public void saveTorneo(Torneo torneo) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String json = new Gson().toJson(torneo);
        edit.putString(TORNEO_PREFERENCE_KEY,json);
        edit.commit();
    }

    public Torneo getTorneo() {
        String json = getSharedPreferences().getString(TORNEO_PREFERENCE_KEY,null);
        if(json == null){
            return new Torneo();
        }

        Torneo torneo = new Gson().fromJson(json, Torneo.class);
        return torneo;
    }

    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }
}
