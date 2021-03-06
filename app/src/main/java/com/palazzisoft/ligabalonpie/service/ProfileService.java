package com.palazzisoft.ligabalonpie.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Participante;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ProfileService extends AsyncTask<Void, Void, Participante> {

    private static final String TAG = "ProfileService";

    private final Participante participante;
    private final Resources resources;

    public ProfileService(final Participante participante, final Resources resources) {
        this.participante = participante;
        this.resources = resources;
    }

    @Override
    protected Participante doInBackground(Void... params) {
        Log.i(TAG,  "Actualizando Participante con id " + participante.getId());

        final String url = resources.getString(R.string.baseUrl).concat("actualizarParticipante");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Participante> response = restTemplate.postForEntity(url,  participante, Participante.class);
            return response.getBody();
        }
        catch (Exception e) {
            Log.i(TAG, "Error al actualizar Participante con id " + participante.getId());
            return null;
        }
    }
}
