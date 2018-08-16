package com.palazzisoft.ligabalonpie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetallesTorneo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_torneo);

        String torneoId = getIntent().getStringExtra("torneoId");
    }
}
