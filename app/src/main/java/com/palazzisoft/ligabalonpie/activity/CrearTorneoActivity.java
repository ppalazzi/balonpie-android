package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.palazzisoft.ligabalonpie.dto.Equipo;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.ValidarTorneoService;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static org.springframework.util.StringUtils.hasText;

public class CrearTorneoActivity extends AppCompatActivity {

    private static final String TAG = "CrearTorneoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_torneo);

        Button button = (Button) findViewById(R.id.btn_agregar_jugadores);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToElegirEquipo();
            }
        });
    }

    private void goToElegirEquipo() {
        EditText nombreEquipo = (EditText) findViewById(R.id.nombre_equipo);
        EditText nombreTorneo  = (EditText) findViewById(R.id.nombre_torneo);


        validarNombreDeTorneo(nombreTorneo);
        validarNombreDeEquipo(nombreEquipo);

        Torneo torneo = new Torneo();
        torneo.setDescripcion(nombreTorneo.getText().toString());

        Equipo equipo = new Equipo();
        equipo.setName(nombreEquipo.getText().toString());
        torneo.setEquipos(new ArrayList<Equipo>());
        torneo.getEquipos().add(equipo);

        TorneoPreference torneoPreference = new TorneoPreference(getApplicationContext());
        torneoPreference.saveTorneo(torneo);

        Intent intent = new Intent(getApplicationContext(), ElegirEquipoActivity.class);
        startActivity(intent);
    }

    private void validarNombreDeEquipo(EditText nombreEquipo) {
        if (!hasText(nombreEquipo.getText().toString())) {
            nombreEquipo.setError("Debe completar el campo Equipo");
        }
    }

    private void validarNombreDeTorneo(EditText editText) {
        ValidarTorneoService validarTorneoService = new ValidarTorneoService(editText.getText().toString());
        validarTorneoService.execute();

        try {
            Boolean isValidName = validarTorneoService.get();
            if (isValidName == null || isValidName == FALSE) {
                if(isValidName) {
                    editText.setError("El nombre el torneo ya existe");
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Error al Validar nombre del Torneo");
        }
    }
}
