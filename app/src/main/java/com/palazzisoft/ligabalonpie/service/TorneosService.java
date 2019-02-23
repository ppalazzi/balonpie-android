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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TorneosService extends AsyncTask<Void, Void, List> {

    private static final String TAG = "TorneosService";

    private Integer participanteId;
    private final Resources resources;

    public TorneosService(final Integer participanteId, final Resources resources) {
        this.participanteId = participanteId;
        this.resources = resources;
    }

    @Override
    protected List doInBackground(Void... params) {
        Log.i(TAG,  "Listando torneos de Participante id " + participanteId);

        final String url = resources.getString(R.string.baseUrl).concat("torneo/{participanteId}");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Torneo[]> response = restTemplate.getForEntity(url, Torneo[].class, participanteId);
            return Arrays.asList(response.getBody());
        }
        catch (Exception e) {
            Log.e(TAG, "Error al Obtener los torneos de un Participante", e);
            return null;
        }
    }
}
