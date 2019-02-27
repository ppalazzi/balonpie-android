package com.palazzisoft.ligabalonpie.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Jugador;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by gerbio on 27/2/2019.
 */

public class JugadorActualizarService extends AsyncTask<Void, Void, Jugador> {

    private static final String TAG = "JugadorActualizarServic";


    private final Resources resources;
    private final Jugador jugador;

    public JugadorActualizarService(final Resources resources, final Jugador jugador) {
        this.resources = resources;
        this.jugador = jugador;
    }

    @Override
    protected Jugador doInBackground(Void... params) {
        Log.i(TAG, "Actualizando Jugador" + jugador.getId());

        final String url = resources.getString(R.string.baseUrl).concat("/jugador/update");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Jugador> jugadorNuevo = restTemplate.postForEntity(url, jugador, Jugador.class);
            return jugadorNuevo.getBody();
        } catch (Exception e) {
           Log.e(TAG, "Error al actualizar un Jugador " + jugador.getId(), e);
            return null;
        }
    }
}
