package com.palazzisoft.ligabalonpie.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Participante;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ParticipantePorEmailService extends AsyncTask<Void, Void,Participante> {

    private static final String TAG = "ParticipantePorEmail";


    private Resources resources;
    private String email;

    public ParticipantePorEmailService(final Resources resources, String email) {
        this.email = email;
        this.resources = resources;
    }

    @Override
    protected Participante doInBackground(Void... params) {
        final String url = resources.getString(R.string.baseUrl).concat("participante?user=").concat(email);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Participante> participante = restTemplate.getForEntity(url, Participante.class);
            return participante.getBody();
        }
        catch (Exception e) {
            Log.e(TAG, "Error trayendo Participante por email", e);
            return null;
        }
    }
}
