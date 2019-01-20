package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.EquiposEstadisticas;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.service.PosicionesService;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class TablaPosiciones extends AppCompatActivity {

    private Torneo torneo;
    private List posicionesEquipo;

    private TextView equipoPosicion1;
    private TextView equipoNombre1;
    private TextView equipoGoles1;
    private TextView equipo1GolesFavor;
    private TextView equipo1GolesEnContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_posiciones);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        this.torneo = (Torneo) bundle.get("torneo");
        cargarVista();
    }

    private void cargarVista() {
        this.equipoPosicion1 = (TextView) findViewById(R.id.equ1_pos);
        this.equipoNombre1 = (TextView) findViewById(R.id.equ1_nom);
        this.equipoGoles1 = (TextView) findViewById(R.id.equ1_gol);
        this.equipo1GolesFavor = (TextView) findViewById(R.id.equ1_gf);
        this.equipo1GolesEnContra = (TextView) findViewById(R.id.equ1_gc);
    }

    private void verPosiciones() {
        if (torneo.getFixture() != null) {
            PosicionesService service = new PosicionesService(this.getResources(), torneo.getId());
            service.execute();

            try {
                posicionesEquipo = service.get();
                mostrarEquipo1((EquiposEstadisticas) posicionesEquipo.get(0));
            }
            catch (Exception e) {
                showError();
            }
        }
    }

    private void mostrarEquipo1(EquiposEstadisticas equipo) {
        this.equipoPosicion1.setText("1");
        this.equipoNombre1.setText(equipo.getEquipo().getNombre());
    }

    private void showError() {
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Error al mostrar tabla de posiciones, intentelo nuevamente", LENGTH_SHORT);
        toast1.show();
        goToDashboard();
    }

    private void goToDashboard() {
        Intent intent = new Intent(getApplicationContext(), DashboardOptions.class);
        startActivity(intent);
    }
}
