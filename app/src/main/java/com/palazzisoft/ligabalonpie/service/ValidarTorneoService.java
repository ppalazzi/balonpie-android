package com.palazzisoft.ligabalonpie.service;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class ValidarTorneoService extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "ValidarTorneoService";

    private String name;

    public ValidarTorneoService(final String name) {
        this.name = name;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.i(TAG,  "Validando nombre del Torneo" + name);

        final String url = "http://192.168.0.241:8080/torneo/nombreValido/{name}";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class, name);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            Log.i(TAG, "Error al Validar el nombre del torneo");
            return null;
        }
        else {
            Log.i(TAG, "Error al Validar el nombre del Torneo");
        }

        return response.getBody();
    }
}
