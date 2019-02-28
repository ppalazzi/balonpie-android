package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AdminDashboard extends AppCompatActivity {

    private Button botonParticipante;
    private Button botonJugador;
    private Button botonSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        cargarView();
    }

    private void cargarView() {
        botonParticipante = (Button) findViewById(R.id.botonParticipante);
        botonParticipante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParticipanteAdmin.class);
                startActivity(intent);
            }
        });

        botonJugador = (Button) findViewById(R.id.botonJugador);
        botonJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminJugador.class);
                startActivity(intent);
            }
        });
        botonSalir = (Button) findViewById(R.id.adminSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
