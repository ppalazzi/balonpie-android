package com.palazzisoft.ligabalonpie.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.palazzisoft.ligabalonpie.dto.Equipo;

public class EquipoPreferences {

    private static final String EQUIPO_PREFERENCE_KEY = "EQUIPO_PREFERENCE_KEY";
    private final Context context;

    public EquipoPreferences(Context context) {
        this.context = context;
    }

    public void saveEquipo(final Equipo equipo) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String json = new Gson().toJson(equipo);
        edit.putString(EQUIPO_PREFERENCE_KEY,json);
        edit.commit();
    }

    public Equipo getEquipo() {
        String json = getSharedPreferences().getString(EQUIPO_PREFERENCE_KEY ,null);

        if (json == null) {
            return new Equipo();
        }

        Equipo equipo = new Gson().fromJson(json, Equipo.class);
        return equipo;
    }

    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }
}
