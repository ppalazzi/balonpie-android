package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;

public class DashboardOptions extends AppCompatActivity {

    private Button profileButton;
    private Button createTournamentButton;
    private Button myTournamentButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_options);
        initiateComponents();
        setClickListeners();
    }

    private void initiateComponents() {
        this.profileButton = (Button) findViewById(R.id.profileButtonId);
        this.createTournamentButton = (Button) findViewById(R.id.createTournamentButtonId);
        this.myTournamentButton = (Button) findViewById(R.id.myTournamentButtonId);
        this.logoutButton = (Button) findViewById(R.id.logoutButtonId);
    }

    private void setClickListeners() {
        this.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyView(Profile.class);
            }
        });

        this.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAndBackToLogin();
            }
        });

        this.myTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyView(TorneoList.class);
            }
        });

        this.createTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyView(CrearTorneoActivity.class);
            }
        });
    }

    private void goToMyView(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void logoutAndBackToLogin() {
        ParticipantePreference preference = new ParticipantePreference(getApplicationContext());
        preference.saveParticipante(null);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
