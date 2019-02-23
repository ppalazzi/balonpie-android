package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.EquiposEstadisticas;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.service.PosicionesService;

import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class TablaPosiciones extends AppCompatActivity {

    private static final String TAG = "TablaPosiciones";

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

    private TextView equipoPosicion3;
    private TextView equipoNombre3;
    private TextView equipoPuntos3;
    private TextView equipo3GolesFavor;
    private TextView equipo3GolesEnContra;

    private TextView equipoPosicion4;
    private TextView equipoNombre4;
    private TextView equipoPuntos4;
    private TextView equipo4GolesFavor;
    private TextView equipo4GolesEnContra;

    private TextView equipoPosicion5;
    private TextView equipoNombre5;
    private TextView equipoPuntos5;
    private TextView equipo5GolesFavor;
    private TextView equipo5GolesEnContra;

    private TextView equipoPosicion6;
    private TextView equipoNombre6;
    private TextView equipoPuntos6;
    private TextView equipo6GolesFavor;
    private TextView equipo6GolesEnContra;

    private TextView equipoPosicion7;
    private TextView equipoNombre7;
    private TextView equipoPuntos7;
    private TextView equipo7GolesFavor;
    private TextView equipo7GolesEnContra;

    private TextView equipoPosicion8;
    private TextView equipoNombre8;
    private TextView equipoPuntos8;
    private TextView equipo8GolesFavor;
    private TextView equipo8GolesEnContra;

    private TextView equipoPosicion9;
    private TextView equipoNombre9;
    private TextView equipoPuntos9;
    private TextView equipo9GolesFavor;
    private TextView equipo9GolesEnContra;

    private TextView equipoPosicion10;
    private TextView equipoNombre10;
    private TextView equipoPuntos10;
    private TextView equipo10GolesFavor;
    private TextView equipo10GolesEnContra;
    private Button buttonVolver;

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

        this.equipoNombre2 = (TextView) findViewById(R.id.equ2_nom);
        this.equipoPosicion2 = (TextView) findViewById(R.id.equ2_pos);
        this.equipoPuntos2 = (TextView) findViewById(R.id.equ2_pun);
        this.equipo2GolesEnContra = (TextView) findViewById(R.id.equ2_gc);
        this.equipo2GolesFavor = (TextView) findViewById(R.id.equ2_gf);

        this.equipoNombre3 = (TextView) findViewById(R.id.equ3_nom);
        this.equipoPosicion3 = (TextView) findViewById(R.id.equ3_pos);
        this.equipoPuntos3 = (TextView) findViewById(R.id.equ3_pun);
        this.equipo3GolesEnContra = (TextView) findViewById(R.id.equ3_gc);
        this.equipo3GolesFavor = (TextView) findViewById(R.id.equ3_gf);

        this.equipoNombre4 = (TextView) findViewById(R.id.equ4_nom);
        this.equipoPosicion4 = (TextView) findViewById(R.id.equ4_pos);
        this.equipoPuntos4 = (TextView) findViewById(R.id.equ4_pun);
        this.equipo4GolesEnContra = (TextView) findViewById(R.id.equ4_gc);
        this.equipo4GolesFavor = (TextView) findViewById(R.id.equ4_gf);

        this.equipoNombre5 = (TextView) findViewById(R.id.equ5_nom);
        this.equipoPosicion5 = (TextView) findViewById(R.id.equ5_pos);
        this.equipoPuntos5 = (TextView) findViewById(R.id.equ5_pun);
        this.equipo5GolesEnContra = (TextView) findViewById(R.id.equ5_gc);
        this.equipo5GolesFavor = (TextView) findViewById(R.id.equ5_gf);

        this.equipoNombre6 = (TextView) findViewById(R.id.equ6_nom);
        this.equipoPosicion6 = (TextView) findViewById(R.id.equ6_pos);
        this.equipoPuntos6 = (TextView) findViewById(R.id.equ6_pun);
        this.equipo6GolesEnContra = (TextView) findViewById(R.id.equ6_gc);
        this.equipo6GolesFavor = (TextView) findViewById(R.id.equ6_gf);

        this.equipoNombre7 = (TextView) findViewById(R.id.equ7_nom);
        this.equipoPosicion7 = (TextView) findViewById(R.id.equ7_pos);
        this.equipoPuntos7 = (TextView) findViewById(R.id.equ7_pun);
        this.equipo7GolesEnContra = (TextView) findViewById(R.id.equ7_gc);
        this.equipo7GolesFavor = (TextView) findViewById(R.id.equ7_gf);

        this.equipoNombre8 = (TextView) findViewById(R.id.equ8_nom);
        this.equipoPosicion8 = (TextView) findViewById(R.id.equ8_pos);
        this.equipoPuntos8 = (TextView) findViewById(R.id.equ8_pun);
        this.equipo8GolesEnContra = (TextView) findViewById(R.id.equ8_gc);
        this.equipo8GolesFavor = (TextView) findViewById(R.id.equ8_gf);

        this.equipoNombre9 = (TextView) findViewById(R.id.equ9_nom);
        this.equipoPosicion9 = (TextView) findViewById(R.id.equ9_pos);
        this.equipoPuntos9 = (TextView) findViewById(R.id.equ9_pun);
        this.equipo9GolesEnContra = (TextView) findViewById(R.id.equ9_gc);
        this.equipo9GolesFavor = (TextView) findViewById(R.id.equ9_gf);

        this.equipoNombre10 = (TextView) findViewById(R.id.equ10_nom);
        this.equipoPosicion10 = (TextView) findViewById(R.id.equ10_pos);
        this.equipoPuntos10 = (TextView) findViewById(R.id.equ10_pun);
        this.equipo10GolesEnContra = (TextView) findViewById(R.id.equ10_gc);
        this.equipo10GolesFavor = (TextView) findViewById(R.id.equ10_gf);

        this.buttonVolver = (Button) findViewById(R.id.btn_volver);
        this.buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverAFechas();
            }
        });
    }

    private void verPosiciones() {
        if (torneo.getFixture() != null) {
            PosicionesService service = new PosicionesService(this.getResources(), torneo.getId());

            try {
                service.execute();
                posicionesEquipo = service.get();

                if (posicionesEquipo != null && posicionesEquipo.size() > 0) {
                    mostrarEquipos(posicionesEquipo);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    private void mostrarEquipos(List<EquiposEstadisticas> equipos) {
        List<TextView> posiciones = Arrays.asList(equipoPosicion1, equipoPosicion2, equipoPosicion3, equipoPosicion4,
                equipoPosicion5, equipoPosicion6, equipoPosicion7, equipoPosicion8, equipoPosicion9, equipoPosicion10);
        List<TextView> nombres = Arrays.asList(equipoNombre1, equipoNombre2, equipoNombre3, equipoNombre4,equipoNombre5,
                equipoNombre6,equipoNombre7,equipoNombre8,equipoNombre9,equipoNombre10);
        List<TextView> puntos = Arrays.asList(equipoPuntos1, equipoPuntos2, equipoPuntos3, equipoPuntos4, equipoPuntos5,
                equipoPuntos6, equipoPuntos7, equipoPuntos8, equipoPuntos9, equipoPuntos10);
        List<TextView> gf = Arrays.asList(equipo1GolesFavor, equipo2GolesFavor, equipo3GolesFavor, equipo4GolesFavor,
                equipo5GolesFavor, equipo6GolesFavor, equipo7GolesFavor, equipo8GolesFavor, equipo9GolesFavor, equipo10GolesFavor);
        List<TextView> gc = Arrays.asList(equipo1GolesEnContra, equipo2GolesEnContra, equipo3GolesEnContra, equipo4GolesEnContra,
                equipo5GolesEnContra, equipo6GolesEnContra, equipo7GolesEnContra, equipo8GolesEnContra, equipo9GolesEnContra, equipo10GolesEnContra);

        for (int i = 0; i < 10; i++) {
            posiciones.get(i).setText(String.valueOf(i+1));
            nombres.get(i).setText(equipos.get(i).getEquipo().getNombre());
            puntos.get(i).setText(String.valueOf(equipos.get(i).getPuntos()));
            gf.get(i).setText(String.valueOf(equipos.get(i).getGolesAFavor()));
            gc.get(i).setText(String.valueOf(equipos.get(i).getGolesRecibidos()));
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

    private void volverAFechas() {
        Intent intent = new Intent(getApplicationContext(), DetallesTorneo.class);
        intent.putExtra("torneoId", torneo.getId());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        volverAFechas();
    }
}
