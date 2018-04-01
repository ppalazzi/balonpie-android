package com.palazzisoft.ligabalonpie.service;

import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.dto.Participante;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ProfileService extends AsyncTask<Void, Void, Participante> {

    private static final String TAG = "ProfileService";

    private final Participante participante;

    public ProfileService(final Participante participante) {
        this.participante = participante;
    }

    @Override
    protected Participante doInBackground(Void... params) {
        Log.i(TAG,  "Actualizando Participante con id " + participante.getId());

        final String url = "http://192.168.0.18:8080/actualizarParticipante";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Participante> response = restTemplate.postForEntity(url,  participante, Participante.class);

        if (response.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
            Log.i(TAG, "Error al actualizar Participante con id " + participante.getId());
            return null;
        }

        return response.getBody();
    }
}
