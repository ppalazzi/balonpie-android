package com.palazzisoft.ligabalonpie.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.model.Participante;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_ingresar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new  HttpRequestTask().execute();
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Participante> {

        @Override
        protected Participante doInBackground(Void... params) {
            final String url = "http://192.168.1.104:8080/login/";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Participante greeting = restTemplate.getForObject(url, Participante.class);
            return greeting;
        }

        @Override
        protected void onPostExecute(Participante participante) {
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText("Resultado : " + participante.getId() + "-" + participante.getName());
        }

    }

}
