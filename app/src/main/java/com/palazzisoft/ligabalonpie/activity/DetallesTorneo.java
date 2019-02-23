package com.palazzisoft.ligabalonpie.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.Fecha;
import com.palazzisoft.ligabalonpie.dto.Fixture;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.fragment.FechasFragment;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.ComenzarTorneoService;
import com.palazzisoft.ligabalonpie.service.DetalleTorneoService;
import com.palazzisoft.ligabalonpie.service.JugarFechaService;

import java.util.ListIterator;

import static android.widget.Toast.LENGTH_SHORT;

public class DetallesTorneo extends AppCompatActivity {

    private static final String TAG = "DetallesTorneo";

    private LinearLayout fechaNueva;
    private LinearLayout fechaComenzada;
    private TextView nombreTorneoSinComenzar;
    private Button iniciarTorneoButton;

    private TextView detalle_torneo_nombre_torneo;
    private TextView numero_fecha;

    private Button botonSiguiente;
    private Button botonAnterior;

    private Button jugarFecha;
    private Button cambiarJugador;
    private Button tablaPosiciones;
    private Button volver;

    private int torneoId;
    private Torneo torneo;
    private int fechaActualIndex = 0;

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
        detalle_torneo_nombre_torneo = (TextView) findViewById(R.id.detalle_torneo_nombre_torneo);
        numero_fecha = (TextView) findViewById(R.id.numero_fecha);
        botonSiguiente = (Button) findViewById(R.id.btn_fecha_siguiente);
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteFecha();
            }
        });
        botonAnterior = (Button) findViewById(R.id.btn_fecha_anterior);
        botonAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anteriorFecha();
            }
        });
        jugarFecha = (Button) findViewById(R.id.btn_jugar);
        jugarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugarFecha();
            }
        });
        cambiarJugador = (Button) findViewById(R.id.btn_cambiar_jugador);
        cambiarJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador();
            }
        });
        tablaPosiciones = (Button) findViewById(R.id.btn_posiciones);
        tablaPosiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verPosiciones();
            }
        });
        volver = (Button) findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDashboard();
            }
        });

        DetalleTorneoService service = new DetalleTorneoService(getResources(), Integer.valueOf(torneoId));
        service.execute();

        try {
            this.torneo = service.get();
            if (torneo != null) {
                if (torneo.getFixture() == null) {
                    Log.i(TAG, "Fecha sin comenzar");
                    // Torneo no comenzado
                    fechaNueva.setVisibility(LinearLayout.VISIBLE);
                    fechaComenzada.setVisibility(LinearLayout.GONE);
                    cargarDatosDeTorneoNuevo(torneo);
                } else {
                    Log.i(TAG, "Fecha comenzada");
                    // Torneo en juego
                    fechaNueva.setVisibility(LinearLayout.GONE);
                    fechaComenzada.setVisibility(LinearLayout.VISIBLE);
                    cargarDetallesDeTorneoComenzado(torneo);
                }
            } else {
                showError();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al traer los detalles del Torneo", e);
        }
    }

    private void cargarDetallesDeTorneoComenzado(Torneo torneo) {
        this.detalle_torneo_nombre_torneo.setText(torneo.getDescripcion());
        this.numero_fecha.setText(torneo.getFixture().getFechas().get(0).getNumero().toString());
        cargarDatosFecha(torneo, true);
    }

    private void cargarDatosDeTorneoNuevo(Torneo torneo) {
        this.nombreTorneoSinComenzar.setText(torneo.getDescripcion());
    }

    private void anteriorFecha() {
        if (torneo != null && torneo.getFixture().getFechas() != null &&
                fechaActualIndex > 0) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FechasFragment fechasFragment = new FechasFragment();
            fragmentTransaction.replace(R.id.fechas_container, fechasFragment);
            fragmentTransaction.addToBackStack(null);

            fechaActualIndex--;
            Bundle data = new Bundle();
            data.putSerializable("fecha", torneo.getFixture().getFechas().get(fechaActualIndex));
            fechasFragment.setArguments(data);
            fragmentTransaction.commit();
            this.numero_fecha.setText(String.valueOf(fechaActualIndex + 1));
        }
    }

    private void siguienteFecha() {
        if (torneo != null && torneo.getFixture().getFechas() != null &&
                torneo.getFixture().getFechas().size() > fechaActualIndex + 1) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FechasFragment fechasFragment = new FechasFragment();
            fragmentTransaction.replace(R.id.fechas_container, fechasFragment);
            fragmentTransaction.addToBackStack(null);

            fechaActualIndex++;
            Bundle data = new Bundle();
            data.putSerializable("fecha", torneo.getFixture().getFechas().get(fechaActualIndex));
            fechasFragment.setArguments(data);
            fragmentTransaction.commit();
            this.numero_fecha.setText(String.valueOf(fechaActualIndex + 1));
        }
    }

    private void cargarDatosFecha(Torneo torneo, boolean nuevo) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FechasFragment fechasFragment = new FechasFragment();

        if (nuevo) {
            fragmentTransaction.add(R.id.fechas_container, fechasFragment);
        }
        else {
            fragmentTransaction.replace(R.id.fechas_container, fechasFragment);
        }

        if (torneo.getFixture().getFechas() != null) {
            Bundle data = new Bundle();
            data.putSerializable("fecha", torneo.getFixture().getFechas().get(fechaActualIndex));
            fechasFragment.setArguments(data);
            fragmentTransaction.commit();
        } else {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "El torneo no tiene fechas creadas", LENGTH_SHORT);
            toast1.show();
        }
    }

    private void refreshView() {
        fechaNueva.setVisibility(LinearLayout.GONE);
        fechaComenzada.setVisibility(LinearLayout.VISIBLE);
        Intent intent = new Intent(getApplicationContext(), DetallesTorneo.class);
        startActivity(intent);
    }

    private void goToDashboard() {
        Intent intent = new Intent(getApplicationContext(), DashboardOptions.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goToDashboard();
    }

    private void verPosiciones() {
        if (torneo.getFixture() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("torneo", torneo);

            Intent intent = new Intent(getApplicationContext(), TablaPosiciones.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    private void cambiarJugador() {
        if (torneo.getFixture().getFechas().get(0).getPartidos().get(0).isJugado()) {
            Intent intent = new Intent(getApplicationContext(), CambiarJugadorActivity.class);
            startActivity(intent);
        }
    }

    private boolean fechaAnteriorJugada() {
        if (fechaActualIndex == 0) {
            return true;
        }

        return torneo.getFixture().getFechas().get(fechaActualIndex-1).getPartidos().get(0).isJugado();
    }

    private boolean esFechaJugada() {
        return torneo.getFixture().getFechas().get(fechaActualIndex).getPartidos().get(0).isJugado();
    }

    private void jugarFecha() {
        if (!fechaAnteriorJugada() || esFechaJugada()) {
            return;
        }

        JugarFechaService service = new
                JugarFechaService(getResources(), torneo.getFixture().getFechas().get(fechaActualIndex));
        service.execute();

        try {
            Fecha fecha = service.get();

            ListIterator<Fecha> it = torneo.getFixture().getFechas().listIterator();
            while (it.hasNext()) {
                Fecha currentFecha = it.next();

                if (fecha.getId().equals(currentFecha.getId())) {
                    it.set(fecha);

                    TorneoPreference torneoPreference = new TorneoPreference(getApplicationContext());
                    torneoPreference.saveTorneo(torneo);
                    cargarDatosFecha(torneo, false);
                }
            }

            if (fecha == null) {
                showError();
            }
        }
        catch (Exception e) {
            showError();
        }
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
            } else {
                showError();
            }
        } catch (Exception e) {
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
