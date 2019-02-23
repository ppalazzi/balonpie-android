package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.dto.Equipo;
import com.palazzisoft.ligabalonpie.dto.Jugador;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.dto.TipoJugador;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.CrearTorneoService;
import com.palazzisoft.ligabalonpie.service.JugadoresService;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.palazzisoft.ligabalonpie.dto.TipoJugador.ARQUERO;
import static com.palazzisoft.ligabalonpie.dto.TipoJugador.ATACANTE;
import static com.palazzisoft.ligabalonpie.dto.TipoJugador.DEFENSOR;
import static com.palazzisoft.ligabalonpie.dto.TipoJugador.MEDIOCAMPISTA;

public class ElegirEquipoActivity extends AppCompatActivity {

    private static final String TAG = "ElegirEquipoActivity";

    private int presupuestoInicial;
    private Torneo torneo;

    private TextView presupuestoView;
    private TextView nombreTorneoView;
    private TextView nombreEquipoView;

    private Button button;

    private Spinner arqueroList;
    private Spinner defensor1List;
    private Spinner defensor2List;
    private Spinner defensor3List;
    private Spinner defensor4List;
    private Spinner mediocampo1List;
    private Spinner mediocampo2List;
    private Spinner mediocampo3List;
    private Spinner atacante1List;
    private Spinner atacante2List;
    private Spinner atacante3List;

    private List<Jugador> arquerosSelected = new ArrayList<>();
    private List<Jugador> defensoresSelected = new ArrayList<>();
    private List<Jugador> mediocampistaSelected = new ArrayList<>();
    private List<Jugador> atacanteSelected = new ArrayList<>();

    private int actualPosicionArquero;

    private int actualPosicionAtacante1;
    private int actualPosicionAtacante2;
    private int actualPosicionAtacante3;
    private int actualPosicionMediocampo1;
    private int actualPosicionMediocampo2;
    private int actualPosicionMediocampo3;
    private int actualPosicionDefensa1;
    private int actualPosicionDefensa2;
    private int actualPosicionDefensa3;
    private int actualPosicionDefensa4;

    private Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_equipo);

        presupuestoInicial = getResources().getInteger(R.integer.equipo_presupuesto_inicial);

        TorneoPreference torneoPreference = new TorneoPreference(getApplicationContext());
        this.torneo = torneoPreference.getTorneo();

        initiateViews();
        initialData();
        initiateCombos();
    }

    private void initiateCombos() {
        arquerosSelected = cargarListasJugadores(ARQUERO);
        defensoresSelected = cargarListasJugadores(DEFENSOR);
        mediocampistaSelected = cargarListasJugadores(MEDIOCAMPISTA);
        atacanteSelected = cargarListasJugadores(ATACANTE);

        seleccionarJugadores();
    }

    private void seleccionarJugadores() {
        seleccionarArqueroDefault();
        seleccionarDefensoresDefault();
        seleccionarMediocampistaDefault();
        seleccionarAtacantesDefault();
    }

    private void seleccionarAtacantesDefault() {
        List<String> atacantes = this.jugadoresToList(this.atacanteSelected);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, atacantes);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, atacantes);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, atacantes);

        atacante1List.setAdapter(adapter1);
        atacante1List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (atacante2List.getSelectedItemPosition() == position || atacante3List.getSelectedItemPosition() == position) {
                    atacante1List.setSelection(actualPosicionAtacante1);
                } else {
                    actualPosicionAtacante1 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        atacante2List.setAdapter(adapter2);
        atacante2List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (atacante1List.getSelectedItemPosition() == position || atacante3List.getSelectedItemPosition() == position) {
                    atacante2List.setSelection(actualPosicionAtacante2);
                } else {
                    actualPosicionAtacante2 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        atacante2List.setSelection(1);
        atacante3List.setAdapter(adapter3);
        atacante3List.setSelection(2);
        atacante3List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (atacante1List.getSelectedItemPosition() == position || atacante2List.getSelectedItemPosition() == position) {
                    atacante3List.setSelection(actualPosicionAtacante3);
                } else {
                    actualPosicionAtacante3 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        actualPosicionAtacante1 = 0;
        actualPosicionAtacante2 = 1;
        actualPosicionAtacante3 = 3;

        volver = (Button) findViewById(R.id.btn_elegir_equipo_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCrearTorneo();
            }
        });
    }

    private void seleccionarMediocampistaDefault() {
        List<String> mediocampistas = this.jugadoresToList(this.mediocampistaSelected);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mediocampistas);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mediocampistas);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mediocampistas);

        mediocampo1List.setAdapter(adapter1);
        mediocampo1List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mediocampo2List.getSelectedItemPosition() == position || mediocampo3List.getSelectedItemPosition() == position) {
                    mediocampo1List.setSelection(actualPosicionMediocampo1);
                } else {
                    actualPosicionMediocampo1 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mediocampo2List.setAdapter(adapter2);
        mediocampo2List.setSelection(1);
        mediocampo2List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mediocampo1List.getSelectedItemPosition() == position || mediocampo3List.getSelectedItemPosition() == position) {
                    mediocampo2List.setSelection(actualPosicionMediocampo2);
                } else {
                    actualPosicionMediocampo2 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mediocampo3List.setAdapter(adapter3);
        mediocampo3List.setSelection(2);
        mediocampo3List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mediocampo1List.getSelectedItemPosition() == position || mediocampo2List.getSelectedItemPosition() == position) {
                    mediocampo3List.setSelection(actualPosicionMediocampo3);
                } else {
                    actualPosicionMediocampo3 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        actualPosicionMediocampo1 = 0;
        actualPosicionMediocampo2 = 1;
        actualPosicionMediocampo3 = 2;
    }

    private void seleccionarDefensoresDefault() {
        List<String> defensoresLabel = this.jugadoresToList(this.defensoresSelected);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, defensoresLabel);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, defensoresLabel);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, defensoresLabel);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, defensoresLabel);

        defensor1List.setAdapter(adapter1);
        defensor1List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (defensor2List.getSelectedItemPosition() == position
                        || defensor3List.getSelectedItemPosition() == position
                        || defensor4List.getSelectedItemPosition() == position) {
                    defensor1List.setSelection(actualPosicionDefensa1);
                } else {
                    actualPosicionDefensa1 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        defensor2List.setAdapter(adapter2);
        defensor2List.setSelection(1);
        defensor2List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (defensor1List.getSelectedItemPosition() == position
                        || defensor3List.getSelectedItemPosition() == position
                        || defensor4List.getSelectedItemPosition() == position) {
                    defensor2List.setSelection(actualPosicionDefensa2);
                } else {
                    actualPosicionDefensa2 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        defensor3List.setAdapter(adapter3);
        defensor3List.setSelection(2);
        defensor3List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (defensor1List.getSelectedItemPosition() == position
                        || defensor2List.getSelectedItemPosition() == position
                        || defensor4List.getSelectedItemPosition() == position) {
                    defensor3List.setSelection(actualPosicionDefensa3);
                } else {
                    actualPosicionDefensa3 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        defensor4List.setAdapter(adapter4);
        defensor4List.setSelection(3);
        defensor4List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (defensor1List.getSelectedItemPosition() == position
                        || defensor3List.getSelectedItemPosition() == position
                        || defensor2List.getSelectedItemPosition() == position) {
                    defensor4List.setSelection(actualPosicionDefensa4);
                } else {
                    actualPosicionDefensa4 = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        actualPosicionDefensa1 = 0;
        actualPosicionDefensa2 = 1;
        actualPosicionDefensa3 = 2;
        actualPosicionDefensa4 = 3;
    }

    private void seleccionarArqueroDefault() {
        List<String> arquerosLabel = this.jugadoresToList(this.arquerosSelected);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arquerosLabel);

        arqueroList.setAdapter(adapter);
        actualPosicionArquero = 0;
    }

    private List<Jugador> cargarListasJugadores(int tipoJugador) {
        JugadoresService jugadorService = new JugadoresService(getResources(), tipoJugador, presupuestoInicial);
        jugadorService.execute();

        try {
            List<Jugador> jugadores = jugadorService.get();
            if (jugadores != null) {
                return jugadores;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al traer Jugadores", e);
        }

        return new ArrayList<>();
    }

    private void initiateViews() {
        this.presupuestoView = (TextView) findViewById(R.id.presupuesto_actual);
        this.nombreEquipoView = (TextView) findViewById(R.id.elegir_equipo_nombre_equipo);
        this.nombreTorneoView = (TextView) findViewById(R.id.elegir_equipo_nombre_torneo);
        this.arqueroList = (Spinner) findViewById(R.id.arquero_list_id);
        this.defensor1List = (Spinner) findViewById(R.id.defensa_1_list_id);
        this.defensor2List = (Spinner) findViewById(R.id.defensa_2_list_id);
        this.defensor3List = (Spinner) findViewById(R.id.defensa_3_list_id);
        this.defensor4List = (Spinner) findViewById(R.id.defensa_4_list_id);
        this.mediocampo1List = (Spinner) findViewById(R.id.mediocampo_1_list_id);
        this.mediocampo2List = (Spinner) findViewById(R.id.mediocampo_2_list_id);
        this.mediocampo3List = (Spinner) findViewById(R.id.mediocampo_3_list_id);
        this.atacante1List = (Spinner) findViewById(R.id.atacante_1_list_id);
        this.atacante2List = (Spinner) findViewById(R.id.atacante_2_list_id);
        this.atacante3List = (Spinner) findViewById(R.id.atacante_3_list_id);
        this.button = (Button) findViewById(R.id.btn_guardarTorneo);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTorneo();
            }
        });
    }

    private void initialData() {
        this.presupuestoView.setText(String.valueOf(this.presupuestoInicial));
        this.nombreTorneoView.setText(this.torneo.getDescripcion());
        this.nombreEquipoView.setText(this.torneo.getEquipos().get(0).getNombre());
    }

    private List<String> jugadoresToList(List<Jugador> jugadores) {
        List<String> nombres = new ArrayList<>();

        Iterator<Jugador> it = jugadores.iterator();
        while (it.hasNext()) {
            Jugador jugador = it.next();
            nombres.add(jugador.toString());
        }

        return nombres;
    }


    private void guardarTorneo() {
        try {
            Jugador arquero = this.arquerosSelected.get(arqueroList.getSelectedItemPosition());
            Jugador defensa1 = this.defensoresSelected.get(defensor1List.getSelectedItemPosition());
            Jugador defensa2 = this.defensoresSelected.get(defensor2List.getSelectedItemPosition());
            Jugador defensa3 = this.defensoresSelected.get(defensor3List.getSelectedItemPosition());
            Jugador defensa4 = this.defensoresSelected.get(defensor4List.getSelectedItemPosition());
            Jugador mediocampo1 = this.mediocampistaSelected.get(mediocampo1List.getSelectedItemPosition());
            Jugador mediocampo2 = this.mediocampistaSelected.get(mediocampo2List.getSelectedItemPosition());
            Jugador mediocampo3 = this.mediocampistaSelected.get(mediocampo3List.getSelectedItemPosition());
            Jugador atacante1 = this.atacanteSelected.get(atacante1List.getSelectedItemPosition());
            Jugador atacante2 = this.atacanteSelected.get(atacante2List.getSelectedItemPosition());
            Jugador atacante3 = this.atacanteSelected.get(atacante3List.getSelectedItemPosition());

            int valorTotal = arquero.getValor() + defensa1.getValor() + defensa2.getValor() + defensa3.getValor() +
                    defensa4.getValor() + mediocampo1.getValor() + mediocampo2.getValor() + mediocampo3.getValor() +
                    atacante1.getValor() + atacante2.getValor() + atacante3.getValor();

            if (isTorneoValid(valorTotal)) {
                Equipo equipo = torneo.getEquipos().get(0);
                equipo.addJugadores(arquero, defensa1, defensa2, defensa3, defensa4, mediocampo1, mediocampo2,
                        mediocampo3, atacante1, atacante2, atacante3);

                guardarTorneoServiceCall(torneo);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error guardando el Torneo", e);
            nombreTorneoView.setError("Ocurrio un error al guardar un Torneo, por favor intentelo nuevamente más tarde");
        }
    }

    private void guardarTorneoServiceCall(Torneo torneo) {
        CrearTorneoService service = new CrearTorneoService(getResources(), torneo);
        service.execute();

        try {
            Object torneoCreated = service.get();

            if (torneoCreated != null) {
                Log.i(TAG, "Torneo Creado correctamente");
                goToMisTorneos();
            } else {
                Log.e(TAG, "Hubo un Error al guardar el Torneo, intentelo nuevamente más tarde");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private boolean isTorneoValid(int valorTotal) {
        if (valorTotal > this.presupuestoInicial) {
            this.nombreTorneoView.setError("Torneo se ha excedido en el Presupuesto");
            return false;
        }
        return true;
    }

    private void goToMisTorneos() {
        Intent intent = new Intent(getApplicationContext(), TorneoList.class);
        startActivity(intent);
    }

    private void goToCrearTorneo() {
        Intent intent = new Intent(getApplicationContext(), CrearTorneoActivity.class);
        startActivity(intent);
    }
}
