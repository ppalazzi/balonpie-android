package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.EquiposEstadisticas;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.service.PosicionesService;

import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class TablaPosiciones extends AppCompatActivity {

    private Torneo torneo;
    private List posicionesEquipo;

    private TextView equipoPosicion1;
    private TextView equipoNombre1;
    private TextView equipoPuntos1;
    private TextView equipo1GolesFavor;
    private TextView equipo1GolesEnContra;

    private TextView equipoPosicion2;
    private TextView equipoNombre2;
    private TextView equipoPuntos2;
    private TextView equipo2GolesFavor;
    private TextView equipo2GolesEnContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_posiciones);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        this.torneo = (Torneo) bundle.get("torneo");
        cargarVista();
        verPosiciones();
    }

    private void cargarVista() {
        this.equipoPosicion1 = (TextView) findViewById(R.id.equ1_pos);
        this.equipoNombre1 = (TextView) findViewById(R.id.equ1_nom);
        this.equipoPuntos1 = (TextView) findViewById(R.id.equ1_pun);
        this.equipo1GolesFavor = (TextView) findViewById(R.id.equ1_gf);
        this.equipo1GolesEnContra = (TextView) findViewById(R.id.equ1_gc);

        this.equipoNombre2 = (TextView) findViewById(R.id.equ2_pos);
        this.equipoPosicion2 = (TextView) findViewById(R.id.equ2_pos);
        this.equipoPuntos2 = (TextView) findViewById(R.id.equ2_pun);
        this.equipo2GolesEnContra = (TextView) findViewById(R.id.equ2_gc);
        this.equipo2GolesFavor = (TextView) findViewById(R.id.equ2_gf);
    }

    private void verPosiciones() {
        if (torneo.getFixture() != null) {
            PosicionesService service = new PosicionesService(this.getResources(), torneo.getId());
            service.execute();

            try {
                posicionesEquipo = service.get();
                mostrarEquipos(posicionesEquipo);
            }
            catch (Exception e) {
                showError();
            }
        }
    }

    private void mostrarEquipos(List<EquiposEstadisticas> equipos) {
        List<TextView> posiciones = Arrays.asList(equipoPosicion1, equipoPosicion2);
        List<TextView> nombres = Arrays.asList(equipoNombre1, equipoNombre2);
        List<TextView> puntos = Arrays.asList(equipoPuntos1, equipoPuntos2);
        List<TextView> gf = Arrays.asList(equipo1GolesFavor, equipo2GolesFavor);
        List<TextView> gc = Arrays.asList(equipo1GolesEnContra, equipo2GolesEnContra);

        for (int i = 0 ; i < 2 ; i++) {
            posiciones.get(i).setText(i);
            nombres.get(i).setText(equipos.get(i).getEquipo().getNombre());
            puntos.get(i).setText(equipos.get(i).getPuntos());
            gf.get(i).setText(equipos.get(i).getGolesAFavor());
            gc.get(i).setText(equipos.get(i).getGolesRecibidos());
        }
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
