package com.palazzisoft.ligabalonpie.service;


import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Equipo;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class CambiarJugadoresService extends AsyncTask<Void, Void, Equipo> {

    private static final String TAG = "CambiarJugadoresService";

    private final Resources resources;
    private final Integer equipoId;
    private final Integer originalId;
    private final Integer nuevoId;

    public CambiarJugadoresService(final Resources resources, final Integer equipoId, final Integer originalId,
                                   final Integer nuevoId) {
        this.resources = resources;
        this.equipoId = equipoId;
        this.originalId = originalId;
        this.nuevoId = nuevoId;
    }

    @Override
    protected Equipo doInBackground(Void... params) {
        Log.i(TAG, "Reemplazando Jugador " + originalId + " por " + nuevoId);

        final String url = resources.getString(R.string.baseUrl).concat("fecha/cambiarJugador/{equipoId}/{originalId}/{nuevoId}");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Equipo> response =
                    restTemplate.getForEntity(url, Equipo.class, new Object[] {equipoId, originalId, nuevoId});
            return response.getBody();
        }
        catch (Exception e) {
            Log.e(TAG, "Error cambiando los Jugadores " + originalId + " por " + nuevoId);
            return null;
        }
    }
}
