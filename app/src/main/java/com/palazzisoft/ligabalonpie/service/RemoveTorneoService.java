package com.palazzisoft.ligabalonpie.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Jugador;
import com.palazzisoft.ligabalonpie.dto.Torneo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RemoveTorneoService extends AsyncTask<Void, Void, Torneo> {

    private static final String TAG = "RemoveTorneoService";


    private Resources resources;
    private Integer torneoId;

    public RemoveTorneoService(Resources resources, Integer torneoId) {
        this.resources = resources;
        this.torneoId = torneoId;
    }

    @Override
    protected Torneo doInBackground(Void... params) {
        Log.i(TAG, "Removiendo el Torneo con Id " + torneoId);

        String url = resources.getString(R.string.baseUrl).concat("torneo/remove/{torneoId}");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Torneo> response = restTemplate.getForEntity(url, Torneo.class, torneoId);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            Log.e(TAG, "Error al eliminar el Torneo " + torneoId);
            return null;
        }

        return response.getBody();
    }
}
