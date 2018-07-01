package com.palazzisoft.ligabalonpie.service;

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

    public TorneosService(final Integer participanteId) {
        this.participanteId = participanteId;
    }

    @Override
    protected List doInBackground(Void... params) {
        Log.i(TAG,  "Listando torneos de Participante id " + participanteId);

        final String url = "http://192.168.0.241:8080/torneo/{participanteId}";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Torneo[]> response = restTemplate.getForEntity(url, Torneo[].class, participanteId);

        if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            Log.i(TAG, "Error al Obtener los torneos de un Participante");
            return null;
        }

        return Arrays.asList(response.getBody());
    }
}
