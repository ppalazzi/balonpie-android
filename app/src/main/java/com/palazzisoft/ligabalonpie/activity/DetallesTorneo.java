package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.Fixture;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.ComenzarTorneoService;
import com.palazzisoft.ligabalonpie.service.DetalleTorneoService;

import static android.widget.Toast.LENGTH_SHORT;

public class DetallesTorneo extends AppCompatActivity {

    private static final String TAG = "DetallesTorneo";

    private LinearLayout fechaNueva;
    private LinearLayout fechaComenzada;
    private TextView nombreTorneoSinComenzar;
    private Button iniciarTorneoButton;

    private int torneoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_torneo);

        this.torneoId = getIntent().getIntExtra("torneoId", -1);
        initiateViews(torneoId);
    }

    private void initiateViews(Integer torneoId) {
        if (torneoId == -1) {
            goToDashboard();
        }

        fechaNueva = (LinearLayout) findViewById(R.id.fecha_nueva);
        fechaComenzada = (LinearLayout) findViewById(R.id.fecha_comenzada);
        nombreTorneoSinComenzar = (TextView) findViewById(R.id.torneo_nuevo_nombre_torneo);
        iniciarTorneoButton = (Button) findViewById(R.id.btn_comenzar_fecha);
        iniciarTorneoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarTorneo();
            }
        });

        DetalleTorneoService service = new DetalleTorneoService(getResources(), Integer.valueOf(torneoId));
        service.execute();

        try {
            Torneo torneo = service.get();
            if (torneo != null) {
                if (torneo.getFixture() == null) {
                    Log.i(TAG, "Fecha sin comenzar");
                    // Torneo no comenzado
                    fechaNueva.setVisibility(LinearLayout.VISIBLE);
                    fechaComenzada.setVisibility(LinearLayout.GONE);
                    cargarDatosDeTorneoNuevo(torneo);
                }
                else {
                    Log.i(TAG, "Fecha comenzada");
                    // Torneo en juego
                    fechaNueva.setVisibility(LinearLayout.GONE);
                    fechaComenzada.setVisibility(LinearLayout.VISIBLE);
                }
            }else {
                showError();
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Error al traer los detalles del Torneo");
        }
    }

    private void cargarDatosDeTorneoNuevo(Torneo torneo) {
        this.nombreTorneoSinComenzar.setText(torneo.getDescripcion());
    }

    private void refreshView() {
        fechaNueva.setVisibility(LinearLayout.GONE);
        fechaComenzada.setVisibility(LinearLayout.VISIBLE);
    }

    private void goToDashboard() {
        Intent intent = new Intent(getApplicationContext(), DashboardOptions.class);
        startActivity(intent);
    }

    private void iniciarTorneo() {
        ComenzarTorneoService service = new ComenzarTorneoService(getResources(), torneoId);
        service.execute();

        try {
            Fixture fixture = service.get();
            if (fixture != null) {
                TorneoPreference torneoPreference = new TorneoPreference(getApplicationContext());
                Torneo torneoSelected = torneoPreference.getTorneo();
                torneoSelected.setFixture(fixture);
                torneoPreference.saveTorneo(torneoSelected);
                refreshView();
            }
            else {
                showError();
            }
        }
        catch (Exception e) {
            showError();
        }
    }

    private void showError() {
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Error al intentar comenzar el Torneo, intentelo nuevamente", LENGTH_SHORT);
        toast1.show();
        goToDashboard();
    }
}
