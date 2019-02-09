package com.palazzisoft.ligabalonpie.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.palazzisoft.ligabalonpie.dto.Equipo;
import com.palazzisoft.ligabalonpie.dto.Jugador;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.EquipoPreferences;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.CambiarJugadoresService;
import com.palazzisoft.ligabalonpie.service.JugadoresService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.palazzisoft.ligabalonpie.dto.TipoJugador.ARQUERO;
import static com.palazzisoft.ligabalonpie.dto.TipoJugador.ATACANTE;
import static com.palazzisoft.ligabalonpie.dto.TipoJugador.DEFENSOR;
import static com.palazzisoft.ligabalonpie.dto.TipoJugador.MEDIOCAMPISTA;
import static java.lang.Integer.valueOf;
import static java.util.Collections.sort;

public class CambiarJugadorActivity extends AppCompatActivity {

    private static final String TAG = "CambiarJugadorActivity";

    private Torneo torneo;
    private Equipo equipo;
    private List<Jugador> jugadoresDeEquipo;

    private int presupuestoInicial;

    private JugadorComparator comparadorPorValor = new JugadorComparator();

    // componentes
    private Spinner arqueroList;
    private Spinner defensaList1;
    private Spinner defensaList2;
    private Spinner defensaList3;
    private Spinner defensaList4;
    private Spinner mediocampoList1;
    private Spinner mediocampoList2;
    private Spinner mediocampoList3;
    private Spinner atacanteList1;
    private Spinner atacanteList2;
    private Spinner atacanteList3;

    private Button cambiar1;
    private Button cambiar2;
    private Button cambiar3;
    private Button cambiar4;
    private Button cambiar5;
    private Button cambiar6;
    private Button cambiar7;
    private Button cambiar8;
    private Button cambiar9;
    private Button cambiar10;
    private Button cambiar11;

    private Button ver1;
    private Button ver2;
    private Button ver3;
    private Button ver4;
    private Button ver5;
    private Button ver6;
    private Button ver7;
    private Button ver8;
    private Button ver9;
    private Button ver10;
    private Button ver11;

    private TextView presupuesto;
    private Button buttonVolver;
    private TextView mensages;

    private List<Jugador> arqueros = new ArrayList<>();
    private List<Jugador> defensores = new ArrayList<>();
    private List<Jugador> mediocampistas = new ArrayList<>();
    private List<Jugador> atacantes = new ArrayList<>();

    private int arqueroIndexSelected = -1;
    private int defensaIndexSelected1 = -1;
    private int defensaIndexSelected2 = -1;
    private int defensaIndexSelected3 = -1;
    private int defensaIndexSelected4 = -1;
    private int medioIndexSelected1 = -1;
    private int medioIndexSelected2 = -1;
    private int medioIndexSelected3 = -1;
    private int atacanteIndexSelected1 = -1;
    private int atacanteIndexSelected2 = -1;
    private int atacanteIndexSelected3 = -1;

    private PopupWindow popupWindow;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_jugador);

        presupuestoInicial = getResources().getInteger(R.integer.equipo_presupuesto_inicial);

        TorneoPreference torneoPreference = new TorneoPreference(getApplicationContext());
        this.torneo = torneoPreference.getTorneo();

        EquipoPreferences equipoPreferences = new EquipoPreferences(getApplicationContext());
        this.equipo = equipoPreferences.getEquipo();
        this.jugadoresDeEquipo = equipo.getJugadores();
        Collections.sort(jugadoresDeEquipo, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                if (o1.getTipoJugador().equals(o2.getTipoJugador())) {
                    return o1.getNombre().compareTo(o2.getNombre());
                }

                return valueOf(o1.getTipoJugador().getId()).compareTo(valueOf(o2.getTipoJugador().getId()));
            }
        });

        cargarCombos();
        iniciarJugadores();
        calcularPresupuesto();
    }

    private void cargarCombos() {
        this.arqueroList = (Spinner) findViewById(R.id.cambiar_jugador_nombre1);
        this.arqueroList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arqueroIndexSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.defensaList1 = (Spinner) findViewById(R.id.cambiar_jugador_nombre2);
        this.defensaList1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defensaIndexSelected1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.defensaList2 = (Spinner) findViewById(R.id.cambiar_jugador_nombre3);
        this.defensaList2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defensaIndexSelected2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.defensaList3 = (Spinner) findViewById(R.id.cambiar_jugador_nombre4);
        this.defensaList3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defensaIndexSelected3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.defensaList4 = (Spinner) findViewById(R.id.cambiar_jugador_nombre5);
        this.defensaList4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defensaIndexSelected4 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.mediocampoList1 = (Spinner) findViewById(R.id.cambiar_jugador_nombre6);
        this.mediocampoList1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medioIndexSelected1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.mediocampoList2 = (Spinner) findViewById(R.id.cambiar_jugador_nombre7);
        this.mediocampoList2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medioIndexSelected2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.mediocampoList3 = (Spinner) findViewById(R.id.cambiar_jugador_nombre8);
        this.mediocampoList3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medioIndexSelected3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.atacanteList1 = (Spinner) findViewById(R.id.cambiar_jugador_nombre9);
        this.atacanteList1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atacanteIndexSelected1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.atacanteList2 = (Spinner) findViewById(R.id.cambiar_jugador_nombre10);
        this.atacanteList2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atacanteIndexSelected2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.atacanteList3 = (Spinner) findViewById(R.id.cambiar_jugador_nombre11);
        this.atacanteList3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atacanteIndexSelected3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.cambiar1 = (Button) findViewById(R.id.cambiar_jugador_cambiar1);
        this.cambiar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(arqueroIndexSelected, jugadoresDeEquipo.get(0), ARQUERO);
            }
        });
        this.cambiar2 = (Button) findViewById(R.id.cambiar_jugador_cambiar2);
        this.cambiar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(defensaIndexSelected1, jugadoresDeEquipo.get(1), DEFENSOR);
            }
        });
        this.cambiar3 = (Button) findViewById(R.id.cambiar_jugador_cambiar3);
        this.cambiar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(defensaIndexSelected2, jugadoresDeEquipo.get(2), DEFENSOR);
            }
        });
        this.cambiar4 = (Button) findViewById(R.id.cambiar_jugador_cambiar4);
        this.cambiar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(defensaIndexSelected3, jugadoresDeEquipo.get(3), DEFENSOR);
            }
        });
        this.cambiar5 = (Button) findViewById(R.id.cambiar_jugador_cambiar5);
        this.cambiar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(defensaIndexSelected4, jugadoresDeEquipo.get(4), DEFENSOR);
            }
        });
        this.cambiar6 = (Button) findViewById(R.id.cambiar_jugador_cambiar6);
        this.cambiar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(medioIndexSelected1, jugadoresDeEquipo.get(5), MEDIOCAMPISTA);
            }
        });
        this.cambiar7 = (Button) findViewById(R.id.cambiar_jugador_cambiar7);
        this.cambiar7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(medioIndexSelected2, jugadoresDeEquipo.get(6), MEDIOCAMPISTA);
            }
        });
        this.cambiar8 = (Button) findViewById(R.id.cambiar_jugador_cambiar8);
        this.cambiar8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(medioIndexSelected3, jugadoresDeEquipo.get(7), MEDIOCAMPISTA);
            }
        });
        this.cambiar9 = (Button) findViewById(R.id.cambiar_jugador_cambiar9);
        this.cambiar9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(atacanteIndexSelected1, jugadoresDeEquipo.get(8), ATACANTE);
            }
        });
        this.cambiar10 = (Button) findViewById(R.id.cambiar_jugador_cambiar10);
        this.cambiar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(atacanteIndexSelected2, jugadoresDeEquipo.get(9), ATACANTE);
            }
        });
        this.cambiar11 = (Button) findViewById(R.id.cambiar_jugador_cambiar11);
        this.cambiar11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarJugador(atacanteIndexSelected3, jugadoresDeEquipo.get(10), ATACANTE);
            }
        });
        this.buttonVolver = (Button) findViewById(R.id.btn_cambiar_volver);
        this.buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverAFechas();
            }
        });
        this.mensages = (TextView) findViewById(R.id.mensages);
        this.presupuesto = (TextView) findViewById(R.id.plantilla_presupuestoValor);
        this.ver1 = (Button) findViewById(R.id.cambiar_jugador_ver1);
        this.ver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(0));
            }
        });
        this.ver2 = (Button) findViewById(R.id.cambiar_jugador_ver2);
        this.ver2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(1));
            }
        });
        this.ver3 = (Button) findViewById(R.id.cambiar_jugador_ver3);
        this.ver3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(2));
            }
        });
        this.ver4 = (Button) findViewById(R.id.cambiar_jugador_ver4);
        this.ver4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(3));
            }
        });
        this.ver5 = (Button) findViewById(R.id.cambiar_jugador_ver5);
        this.ver5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(4));
            }
        });
        this.ver6 = (Button) findViewById(R.id.cambiar_jugador_ver6);
        this.ver6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(5));
            }
        });
        this.ver7 = (Button) findViewById(R.id.cambiar_jugador_ver7);
        this.ver7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(6));
            }
        });
        this.ver8 = (Button) findViewById(R.id.cambiar_jugador_ver8);
        this.ver8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(7));
            }
        });
        this.ver9 = (Button) findViewById(R.id.cambiar_jugador_ver9);
        this.ver9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(8));
            }
        });
        this.ver10 = (Button) findViewById(R.id.cambiar_jugador_ver10);
        this.ver10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(9));
            }
        });
        this.ver11 = (Button) findViewById(R.id.cambiar_jugador_ver11);
        this.ver11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ver(jugadoresDeEquipo.get(10));
            }
        });
        this.relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_cambiar_jugador);
    }

    private void ver(Jugador jugador) {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.detalle_jugador,null);

        TextView texto = (TextView) customView.findViewById(R.id.detalle_jugadores_texto);
        texto.setText(String.valueOf(jugador.getNombre() + " " + jugador.getApellido()));

        TextView habilidad = (TextView) customView.findViewById(R.id.detalle_jugadores_habilidad);
        habilidad.setText("Habilidad : " + String.valueOf(jugador.getHabilidad()));

        TextView velocidad = (TextView) customView.findViewById(R.id.detalle_jugadores_velocidad);
        velocidad.setText("Velocidad : " + String.valueOf(jugador.getVelocidad()));

        TextView remate = (TextView) customView.findViewById(R.id.detalle_jugadores_remate);
        remate.setText("Remate : " + String.valueOf(jugador.getRemate()));

        TextView fisico = (TextView) customView.findViewById(R.id.detalle_jugadores_fisico);
        fisico.setText("Físico : " + String.valueOf(jugador.getFisico()));

        Button button = (Button) customView.findViewById(R.id.detalle_jugador_button);
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void calcularPresupuesto() {
        this.presupuesto.setText(String.valueOf(equipo.calcularPresupuesto()));
    }

    private void mostrarMensageConfirmacion(final Jugador nuevo, final Jugador original) {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setMessage("Desea cambiar el Jugador " + original.getApellido() + " por " + nuevo.getApellido() + "?")
                .setTitle("Cambiar Jugador").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ejecutarServicioDeCompra(nuevo, original);
                    }
                }).setNegativeButton("No", null).show();
    }

    private void cambiarJugador(int posicion, Jugador original, int tipoJugador) {
        try {
            switch (tipoJugador) {
                case 0:
                    Jugador arqueroSelected = arqueros.get(posicion);
                    mostrarMensageConfirmacion(arqueroSelected, original);
                    break;
                case 1:
                    Jugador defensoresSelected = defensores.get(posicion);
                    mostrarMensageConfirmacion(defensoresSelected, original);
                    break;
                case 2:
                    Jugador mediocampistaSelected = mediocampistas.get(posicion);
                    mostrarMensageConfirmacion(mediocampistaSelected, original);
                    break;
                case 3:
                    Jugador atacanteSelected = atacantes.get(posicion);
                    mostrarMensageConfirmacion(atacanteSelected, original);
                    break;
            }
        }
        catch (Exception e) {
            this.mensages.setError("Error al comprar Jugador, intentelo nuevamente más tarde");
        }
    }

    private void ejecutarServicioDeCompra(Jugador jugadorNuevo, Jugador original) {
        if (esCompraValida(jugadorNuevo, original)) {
            llamarAlServicioCammbiarJugador(jugadorNuevo.getId(), original.getId());
            jugadoresDeEquipo.set(jugadoresDeEquipo.indexOf(original), jugadorNuevo);
        }
        else {
            this.mensages.setError("No tiene suficiente presupuesto para comprar el Jugador");
            Log.i(TAG, "El Presupuesto no permite comprar al jugador " + jugadorNuevo.getId());
        }
    }

    private void llamarAlServicioCammbiarJugador(Integer nuevo, Integer original) {
        try {
            CambiarJugadoresService service = new CambiarJugadoresService(getResources(), equipo.getId(), original, nuevo);
            service.execute();

            Equipo equipo = service.get();

            EquipoPreferences equipoPreferences = new EquipoPreferences(getApplicationContext());
            equipoPreferences.saveEquipo(equipo);
        }
        catch (Exception e) {
            Log.e(TAG, "Ocurrió un error comprando el jugador " + nuevo + " por " + original , e);
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    private boolean esCompraValida(Jugador jugadorSelected, Jugador removido) {
        int presupuestoDeEquipo = equipo.calcularPresupuesto();
        return ( ( (presupuestoDeEquipo - removido.getValor()) + jugadorSelected.getValor() ) <= presupuestoInicial);
    }

    private void iniciarJugadores() {
        arqueros = cargarListasJugadores(ARQUERO);
        sort(arqueros, comparadorPorValor);
        defensores = cargarListasJugadores(DEFENSOR);
        sort(defensores, comparadorPorValor);
        mediocampistas = cargarListasJugadores(MEDIOCAMPISTA);
        sort(mediocampistas, comparadorPorValor);
        atacantes = cargarListasJugadores(ATACANTE);
        sort(atacantes, comparadorPorValor);
        setJugadoresSeleccionados();
    }

    private void setJugadoresSeleccionados() {
        setArqueroDefault();
        setDefensaDefault();
        setMediocampoDefault();
        setAtacanteDefault();
    }

    private void setArqueroDefault() {
        ArrayAdapter<Jugador> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arqueros);
        arqueroList.setAdapter(arrayAdapter);

        arqueroList.setSelection(obtenerPosicionDelJugador(arqueros, jugadoresDeEquipo.get(0)));
    }

    private void setDefensaDefault() {
        ArrayAdapter<Jugador> defensaAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, defensores);
        defensaList1.setAdapter(defensaAdapter1);
        defensaList1.setSelection(obtenerPosicionDelJugador(defensores, jugadoresDeEquipo.get(1)));

        ArrayAdapter<Jugador> defensaAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, defensores);
        defensaList2.setAdapter(defensaAdapter2);
        defensaList2.setSelection(obtenerPosicionDelJugador(defensores, jugadoresDeEquipo.get(2)));


        ArrayAdapter<Jugador> defensaAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, defensores);
        defensaList3.setAdapter(defensaAdapter3);
        defensaList3.setSelection(obtenerPosicionDelJugador(defensores, jugadoresDeEquipo.get(3)));


        ArrayAdapter<Jugador> defensaAdapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, defensores);
        defensaList4.setAdapter(defensaAdapter4);
        defensaList4.setSelection(obtenerPosicionDelJugador(defensores, jugadoresDeEquipo.get(4)));
    }

    private void setMediocampoDefault() {
        ArrayAdapter<Jugador> medioAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mediocampistas);
        mediocampoList1.setAdapter(medioAdapter1);
        mediocampoList1.setSelection(obtenerPosicionDelJugador(mediocampistas, jugadoresDeEquipo.get(5)));

        ArrayAdapter<Jugador> medioAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mediocampistas);
        mediocampoList2.setAdapter(medioAdapter2);
        mediocampoList2.setSelection(obtenerPosicionDelJugador(mediocampistas, jugadoresDeEquipo.get(6)));


        ArrayAdapter<Jugador> medioAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mediocampistas);
        mediocampoList3.setAdapter(medioAdapter3);
        mediocampoList3.setSelection(obtenerPosicionDelJugador(mediocampistas, jugadoresDeEquipo.get(7)));
    }

    private void setAtacanteDefault() {
        ArrayAdapter<Jugador> atacanteAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, atacantes);
        atacanteList1.setAdapter(atacanteAdapter1);
        atacanteList1.setSelection(obtenerPosicionDelJugador(atacantes, jugadoresDeEquipo.get(8)));

        ArrayAdapter<Jugador> atacanteAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, atacantes);
        atacanteList2.setAdapter(atacanteAdapter2);
        atacanteList2.setSelection(obtenerPosicionDelJugador(atacantes, jugadoresDeEquipo.get(9)));

        ArrayAdapter<Jugador> atacanteAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, atacantes);
        atacanteList3.setAdapter(atacanteAdapter3);
        atacanteList3.setSelection(obtenerPosicionDelJugador(atacantes, jugadoresDeEquipo.get(10)));

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

    private int obtenerPosicionDelJugador(List<Jugador> jugadores, Jugador jugador) {
        for (Jugador j : jugadores) {
            if (Integer.valueOf(j.getId()).equals(Integer.valueOf(jugador.getId()))) {
                return jugadores.indexOf(j);
            }
        }

        return -1;
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

    private class JugadorComparator implements Comparator<Jugador> {
        @Override
        public int compare(Jugador o1, Jugador o2) {
            return valueOf(o1.getValor()).compareTo(valueOf(o2.getValor()));
        }
    }
}
