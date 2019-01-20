package com.palazzisoft.ligabalonpie.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.EquiposEstadisticas;
import com.palazzisoft.ligabalonpie.dto.Fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gerbio on 25/11/2018.
 */

public class PosicionesService extends AsyncTask<Void, Void, List> {

    private static final String TAG = "EquiposEstadisticas";

    private final Resources resources;
    private final Integer torneoId;

    public PosicionesService(final Resources resources, Integer torneoId) {
        this.resources = resources;
        this.torneoId = torneoId;
    }

    @Override
    protected List doInBackground(Void... params) {
        Log.i(TAG,  "Actualizando fixture número de torneo" + torneoId);

        final String url = resources.getString(R.string.baseUrl).concat("/fixture/posiciones/{torneoId}");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<EquiposEstadisticas[]> response = restTemplate.getForEntity(url, EquiposEstadisticas[].class, torneoId);

        if (response.getStatusCode() != HttpStatus.OK) {
            Log.e(TAG, "Error al traer las posiciones del Torneo");
            return null;
        }

        return Arrays.asList(response.getBody());
    }
}
