package com.palazzisoft.ligabalonpie.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.Jugador;
import com.palazzisoft.ligabalonpie.service.JugadorActualizarService;
import com.palazzisoft.ligabalonpie.service.JugadoresService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminJugador extends AppCompatActivity {

    private static final String TAG = "AdminJugador";


    private Button volver;
    private Button buscar;
    private Button actualizar;

    private Spinner tipoJugador;
    private Spinner jugadores;

    private TextView nombre;
    private TextView apellido;
    private TextView velocidad;
    private TextView remate;
    private TextView habilidad;
    private TextView fisico;
    private TextView valor;

    Map<Integer, List<Jugador>> mapJugadores = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jugador);
        cargarJugadoresMap();
        initiateViews();
    }

    private void cargarJugadoresMap() {
        int presupuesto = getResources().getInteger(R.integer.equipo_presupuesto_inicial);

        Log.i(TAG, "Trayendo Jugadores por tipo");

        try {
            JugadoresService service = new JugadoresService(getResources(), 0, presupuesto);
            service.execute();
            mapJugadores.put(0, service.get());

            service = new JugadoresService(getResources(), 1, presupuesto);
            service.execute();
            mapJugadores.put(1, service.get());

            service = new JugadoresService(getResources(),2, presupuesto);
            service.execute();
            mapJugadores.put(2, service.get());

            service = new JugadoresService(getResources(), 3, presupuesto);
            service.execute();
            mapJugadores.put(3, service.get());
        }
        catch (Exception e) {
            Log.e(TAG, "Error trayendo Jugadores, intentelo nuevamente más tarde", e);
            goToAdmins();
        }
    }

    private void initiateViews() {
        this.nombre = (TextView) findViewById(R.id.nombre_valor);
        this.apellido = (TextView) findViewById(R.id.apellido_valor);
        this.remate = (TextView) findViewById(R.id.remate_valor);
        this.velocidad = (TextView) findViewById(R.id.velocidad_valor);
        this.fisico = (TextView) findViewById(R.id.fisico_valor);
        this.valor = (TextView) findViewById(R.id.valor_valor);
        this.habilidad = (TextView) findViewById(R.id.habilidad_valor);
        this.volver = (Button) findViewById(R.id.boton_volver);
        this.volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
            }
        });

        this.jugadores = (Spinner) findViewById(R.id.spinner_jugador);
        this.tipoJugador = (Spinner) findViewById(R.id.spinner_tipo);
        this.tipoJugador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Jugador> jugadoresMap = mapJugadores.get(position);
                jugadores.setAdapter(new ArrayAdapter<>(parent.getContext(),
                        android.R.layout.simple_spinner_item, jugadoresAMostrar(jugadoresMap)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cargarTiposDeJugador();
        this.buscar = (Button) findViewById(R.id.boton_buscar_jugador);
        this.buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Jugador> jugadorChosen = mapJugadores.get(tipoJugador.getSelectedItemPosition());

                nombre.setText(jugadorChosen.get(jugadores.getSelectedItemPosition()).getNombre());
                apellido.setText(jugadorChosen.get(jugadores.getSelectedItemPosition()).getApellido());
                velocidad.setText("" + jugadorChosen.get(jugadores.getSelectedItemPosition()).getVelocidad());
                remate.setText("" + jugadorChosen.get(jugadores.getSelectedItemPosition()).getRemate());
                fisico.setText("" + jugadorChosen.get(jugadores.getSelectedItemPosition()).getFisico());
                habilidad.setText("" + jugadorChosen.get(jugadores.getSelectedItemPosition()).getHabilidad());
                valor.setText("" + jugadorChosen.get(jugadores.getSelectedItemPosition()).getValor());
            }
        });

        this.actualizar = (Button) findViewById(R.id.boton_actualizar);
        this.actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
    }

    private void actualizar() {
        try {
            if (isValido()) {
                List<Jugador> jugadorChosen = mapJugadores.get(tipoJugador.getSelectedItemPosition());
                Jugador selected = jugadorChosen.get(jugadores.getSelectedItemPosition());

                actualizarJugador(selected);
                JugadorActualizarService service = new JugadorActualizarService(getResources(), selected);
                service.execute();

                Jugador jugadadorNuevo = service.get();
                jugadorChosen.set(tipoJugador.getSelectedItemPosition(), jugadadorNuevo);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                dialogo.setTitle("Mensaje");
                dialogo.setMessage("Jugador actualizado correctamente");
                dialogo.setNeutralButton("Cerrar", null);
                dialogo.show();
            }
        }catch (Exception e) {
            Log.e(TAG, "Error actualizando Participante", e);
        }
    }

    private Jugador actualizarJugador(Jugador original) {
        original.setNombre(this.nombre.getText().toString());
        original.setApellido(this.apellido.getText().toString());
        original.setRemate(Integer.valueOf(this.remate.getText().toString()));
        original.setValor(Integer.valueOf(this.valor.getText().toString()));
        original.setVelocidad(Integer.valueOf(this.velocidad.getText().toString()));
        original.setHabilidad(Integer.valueOf(this.habilidad.getText().toString()));
        original.setFisico(Integer.valueOf(this.fisico.getText().toString()));

        return original;
    }

    private boolean isValido() {
        Integer velocidadN = (this.velocidad.getText() != null) ? Integer.valueOf(this.velocidad.getText().toString())
                : 0;
        Integer remateN = (this.remate.getText() != null ) ? Integer.valueOf(this.remate.getText().toString()) : 0;

        Integer fisicoN = (this.fisico.getText() != null) ? Integer.valueOf(this.fisico.getText().toString()) : 0;

        Integer habilidadN = (this.habilidad.getText() != null) ? Integer.valueOf(this.habilidad.getText().toString()) : 0;

        Integer valorN = (this.valor.getText() != null) ? Integer.valueOf(this.valor.getText().toString()) : 0;

        if (valorN < 0 || valorN > 1_000_000) {
            Toast.makeText(getApplicationContext(), "Valor debe estar entre 0 y 1.000.000", Toast.LENGTH_LONG).show();
            return false;
        }

        if (habilidadN < 0 || habilidadN > 10) {
            Toast.makeText(getApplicationContext(), "Habilidad debe estar entre 0 y 10", Toast.LENGTH_LONG).show();
            return false;
        }

        if (fisicoN < 0 || fisicoN > 10) {
            Toast.makeText(getApplicationContext(), "Físico debe estar entre 0 y 10", Toast.LENGTH_LONG).show();
            return false;
        }

        if (velocidadN < 0 || velocidadN > 10) {
            Toast.makeText(getApplicationContext(), "Velocidad debe estar entre 0 y 10", Toast.LENGTH_LONG).show();
            return false;
        }

        if (remateN < 0 || remateN > 10) {
            Toast.makeText(getApplicationContext(), "Remate debe estar entre 0 y 10", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private String[] jugadoresAMostrar(List<Jugador> jugadoresMap) {
        String[] response = new String[jugadoresMap.size()];

        for (int i = 0; i < jugadoresMap.size(); i++) {
            response[i] = jugadoresMap.get(i).getNombre() + " " + jugadoresMap.get(i).getApellido();
        }

        return  response;
    }

    private void cargarTiposDeJugador() {
        String[] tipoJugador = new String[]{"Arquero", "Defensor", "Mediocampista", "Atacante"};
        this.tipoJugador.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoJugador));
    }

    @Override
    public void onBackPressed() {
    }

    private void goToAdmins() {
        Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
        startActivity(intent);
    }
}
