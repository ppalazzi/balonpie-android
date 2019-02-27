package com.palazzisoft.ligabalonpie.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.TorneoAdapter;
import com.palazzisoft.ligabalonpie.dto.Equipo;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.EquipoPreferences;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.RemoveTorneoService;
import com.palazzisoft.ligabalonpie.service.TorneosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TorneoList extends AppCompatActivity {

    private static final String TAG = "TorneoList";

    private ListView torneoList;
    private List torneos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torneo_list);

        torneoList = (ListView) findViewById(R.id.torneos_list);
        addListenerContext(torneoList);

        try {
            List<Torneo> torneosDeParticipante = getTorneos();

            int[] logos = new int[torneosDeParticipante.size()];
            String[] botones = new String[torneosDeParticipante.size()];

            for (int i = 0; i < torneosDeParticipante.size(); i++) {
                logos[i] = R.id.torneo_icon;
                botones[i] = "Eliminar";
            }

            TorneoAdapter adapter = new TorneoAdapter(getApplicationContext(), logos,
                    torneosDeParticipante, botones, this);
            torneoList.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "Error al traer Torneos");
        }
    }

    private void addListenerContext(ListView torneosView) {
        torneosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LinearLayout linearLayoutParent = (LinearLayout) view;

                ImageView image = (ImageView) linearLayoutParent.getChildAt(0);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickOnTorneoDetails(position);
                    }
                });

                TextView textView = (TextView) linearLayoutParent.getChildAt(1);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickOnTorneoDetails(position);
                    }
                });

                Button eliminar = (Button) linearLayoutParent.getChildAt(2);
                eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickOnRemoveImage(position);
                    }
                });
            }
        });
    }


    private void clickOnRemoveImage(int position) {
        final Torneo torneo = (Torneo) torneos.get(position);

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmación");
        dialogo.setMessage("¿Realmente desea eliminar el Torneo " + torneo.getDescripcion() + "?");
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarTorneoFromList(torneo);
            }
        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogo.create();
        dialogo.show();
    }

    private void eliminarTorneoFromList(Torneo torneo) {
        RemoveTorneoService removeTorneoService = new RemoveTorneoService(getResources(), torneo.getId());
        removeTorneoService.execute();

        try {
            Torneo torneoNew = removeTorneoService.get();

            if (torneoNew != null) {
                Intent intent = new Intent(getApplicationContext(), DashboardOptions.class);
                startActivity(intent);
            } else {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Error al Eliminar el Torneo, intentelo más tarde", Toast.LENGTH_SHORT);
                toast1.show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al eliminar el Torneo " + torneo.getDescripcion());
        }
    }

    private void clickOnTorneoDetails(int position) {
        Torneo torneoSelected = (Torneo) torneos.get(position);

        TorneoPreference torneoPreference = new TorneoPreference(getApplicationContext());
        torneoPreference.saveTorneo(torneoSelected);

        EquipoPreferences equipoPreferences = new EquipoPreferences(getApplicationContext());
        equipoPreferences.saveEquipo(traerEquipoDelTorneoYParticpante(torneoSelected));

        Intent intent = new Intent(getApplicationContext(), DetallesTorneo.class);
        intent.putExtra("torneoId", torneoSelected.getId());
        startActivity(intent);
    }

    private Equipo traerEquipoDelTorneoYParticpante(Torneo torneo) {
        ParticipantePreference participantePreference = new ParticipantePreference(getApplicationContext());
        Participante participante = participantePreference.getParticipante();

        for (Equipo equipo : torneo.getEquipos()) {
            if (equipo.getParticipante().getId().equals(participante.getId())) {
                return equipo;
            }
        }

        return null;
    }

    private List<Map<String, String>> buildHashMap(List<String> torneos) {
        List<Map<String, String>> aList = new ArrayList<>();

        for (String name : torneos) {
            Map<String, String> hm = new TreeMap<>();
            hm.put("1", String.valueOf(R.drawable.torneo_lista));
            hm.put("2", name);
            hm.put("3", "Eliminar");
            //hm.put("3", String.valueOf(R.drawable.eliminar));
            // hm.put("listview_eliminar", String.valueOf(R.drawable.eliminar));
            //hm.put("listview_eliminar_image", String.valueOf(R.drawable.eliminar));
            aList.add(hm);
        }

        return aList;
    }

    private List<Torneo> getTorneos() throws Exception {
        ParticipantePreference preferences = new ParticipantePreference(getApplicationContext());
        Participante participante = preferences.getParticipante();

        TorneosService service = new TorneosService(participante.getId(), getResources());
        service.execute();

        torneos = service.get();
        if (torneos == null) {
            Log.i(TAG, "El Participante no tiene Torneo " + participante.getId());
            return null;
        }

        return torneos;
    }

    private List<String> extractNamesFromList(List<Torneo> torneos) {
        List<String> nombres = new ArrayList<>();
        for (Torneo torneo : torneos) {
            nombres.add(torneo.getDescripcion());
        }

        return nombres;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DashboardOptions.class);
        startActivity(intent);
    }
}
