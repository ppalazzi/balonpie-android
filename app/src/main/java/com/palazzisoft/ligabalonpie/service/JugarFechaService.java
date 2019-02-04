package com.palazzisoft.ligabalonpie.service;


import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Fecha;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class JugarFechaService extends AsyncTask<Void, Void, Fecha> {

    private static final String TAG = "JugarFechaService";

    private final Resources resources;
    private final Fecha fecha;

    public JugarFechaService(final Resources resources, final Fecha fecha) {
        this.fecha = fecha;
        this.resources = resources;
    }

    @Override
    protected Fecha doInBackground(Void... params) {
        Log.i(TAG,  "Actualizando Fecha número " + fecha.getNumero());

        final String url = resources.getString(R.string.baseUrl).concat("fecha/jugarFecha");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Fecha> response = restTemplate.postForEntity(url,  fecha, Fecha.class);

        if (response.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
            Log.i(TAG, "Error al jugar Fecha con id " + fecha.getId());
            return null;
        }

        return response.getBody();
    }
}
