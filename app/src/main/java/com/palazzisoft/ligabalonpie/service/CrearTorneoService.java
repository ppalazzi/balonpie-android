package com.palazzisoft.ligabalonpie.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Torneo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class CrearTorneoService extends AsyncTask<Void, Void, Object> {

    private static final String TAG = "CrearTorneoService";

    private Torneo torneo;
    private Resources resources;

    public CrearTorneoService(Resources resources, Torneo torneo) {
        this.resources = resources;
        this.torneo = torneo;
    }

    @Override
    protected Object doInBackground(Void... params) {
        Log.i(TAG, "Guardarndo Torneo con nombre " + torneo.getDescripcion());

        final String url = resources.getString(R.string.baseUrl).concat("crearTorneo");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(url, torneo, Object.class);
            return response.getBody();
        }
        catch (Exception e) {
            Log.e(TAG, "Error al Intentar Crear el Torneo");
            return null;
        }

        /*
        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            Log.e(TAG, response.getBody().toString());
            return null;
        }
        else if (response.getStatusCode() != HttpStatus.OK) {
            Log.e(TAG, "Error al Intentar Crear el Torneo");
        }
        */
    }
}
