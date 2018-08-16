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

public class DetalleTorneoService extends AsyncTask<Void, Void, Torneo> {

    private static final String TAG = "DetalleTorneoService";

    private final Resources resources;
    private final Integer torneoId;

    public DetalleTorneoService(final Resources resources, final Integer torneoId) {
        this.resources = resources;
        this.torneoId = torneoId;
    }

    @Override
    protected Torneo doInBackground(Void... params) {
        Log.i(TAG,  "Trayendo Torneo con id " + torneoId);

        final String url = resources.getString(R.string.baseUrl).concat("");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Torneo> response = restTemplate.getForEntity(url, Torneo.class, torneoId);

        if (response.getStatusCode() != HttpStatus.OK) {
            Log.e(TAG, "Error al traer los detalles del Torneo");
            return null;
        }

        return response.getBody();
    }
}
