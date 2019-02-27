package com.palazzisoft.ligabalonpie.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Jugador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JugadoresService extends AsyncTask<Void, Void, List> {

    private static final String TAG = "JugadoresService";

    private int type;
    private final int budget;
    private final Resources resources;


    public JugadoresService(final Resources resources, final int type, final int budget) {
        this.resources = resources;
        this.type = type;
        this.budget = budget;
    }

    @Override
    protected List doInBackground(Void... params) {
        Log.i(TAG, "Trayendo Jugadores del tipo " + type + " y con un presupuesto de " + budget);

        final String url = resources.getString(R.string.baseUrl).concat("jugador/{type}/{budget}");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Jugador[]> response = restTemplate.getForEntity(url, Jugador[].class, type, budget);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            Log.i(TAG, "No se encuentran Jugadores con ese perfil");
            return new ArrayList();
        }

        return Arrays.asList(response.getBody());
    }
}
