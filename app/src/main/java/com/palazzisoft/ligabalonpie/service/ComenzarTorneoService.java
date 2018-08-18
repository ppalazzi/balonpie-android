package com.palazzisoft.ligabalonpie.service;


import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ComenzarTorneoService extends AsyncTask<Void, Void, Fixture> {

    private static final String TAG = "ComenzarTorneoService";

    private final Resources resources;
    private final int torneoId;

    public ComenzarTorneoService(final Resources resources, final int torneoId) {
        this.resources = resources;
        this.torneoId = torneoId;
    }

    @Override
    protected Fixture doInBackground(Void... params) {
        Log.i(TAG, "Comenzando Torneo " + torneoId);

        final String url = resources.getString(R.string.baseUrl).concat("/arrancarTorneo/{torneoId}");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Fixture> response = restTemplate.getForEntity(url, Fixture.class, torneoId);

        if (response.getStatusCode() != HttpStatus.OK) {
            Log.e(TAG, "Hubo un error al comenzar el Torneo " + torneoId);
            return null;
        }

        return response.getBody();
    }
}
