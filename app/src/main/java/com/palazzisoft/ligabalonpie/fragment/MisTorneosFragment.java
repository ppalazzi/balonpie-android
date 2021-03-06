package com.palazzisoft.ligabalonpie.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.activity.DetallesTorneo;
import com.palazzisoft.ligabalonpie.activity.ElegirEquipoActivity;
import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.activity.TorneoList;
import com.palazzisoft.ligabalonpie.dto.Equipo;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.EquipoPreferences;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.RemoveTorneoService;
import com.palazzisoft.ligabalonpie.service.TorneosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MisTorneosFragment extends Fragment {

    private static final String TAG = "MisTorneosFragment";

    private List torneos;

    public MisTorneosFragment() {
        this.torneos = new ArrayList();
    }

    public static MisTorneosFragment getInstance() {
        MisTorneosFragment fragment = new MisTorneosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        View root = inflater.inflate(R.layout.fragment_mis_torneos, container, false);

        ListView torneosView = (ListView) root.findViewById(R.id.torneos_list);

        try {
            String[] from = {"1","2", "3"};
            int[] to = {R.id.torneo_icon, R.id.listview_torneo_name, R.id.listview_eliminar_image};

            List<Map<String, String>> aList = buildHashMap(getTorneosNameList());
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getBaseContext(), aList,
                    R.layout.custom_listview_mis_torneos, from, to);
            torneosView.setAdapter(simpleAdapter);
            addListenerContext(torneosView);
        } catch (Exception e) {
            Log.e(TAG, "Error al traer Torneos");
        }

        return root;
        */
        return null;
    }

    private void addListenerContext(ListView torneosView) {
        torneosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                RelativeLayout linearLayoutParent = (RelativeLayout) view;
                LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(0);

                ImageView image = (ImageView) linearLayoutChild.getChildAt(0);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickOnTorneoDetails(position);
                    }
                });

                TextView textView = (TextView) linearLayoutChild.getChildAt(1);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickOnTorneoDetails(position);
                    }
                });

                Button eliminar = (Button) linearLayoutChild.getChildAt(2);
                eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickOnRemoveImage(position);
                    }
                });
            }
        });
    }

    private void clickOnTorneoDetails(int position) {
        Torneo torneoSelected = (Torneo) torneos.get(position);

        TorneoPreference torneoPreference = new TorneoPreference(getContext().getApplicationContext());
        torneoPreference.saveTorneo(torneoSelected);

        EquipoPreferences equipoPreferences = new EquipoPreferences(getContext().getApplicationContext());
        equipoPreferences.saveEquipo(traerEquipoDelTorneoYParticpante(torneoSelected));

        Intent intent = new Intent(getActivity().getApplicationContext(), DetallesTorneo.class);
        intent.putExtra("torneoId", torneoSelected.getId());
        startActivity(intent);
    }

    private Equipo traerEquipoDelTorneoYParticpante(Torneo torneo) {
        ParticipantePreference participantePreference = new ParticipantePreference(getContext().getApplicationContext());
        Participante participante = participantePreference.getParticipante();

        for (Equipo equipo : torneo.getEquipos()) {
            if (equipo.getParticipante().getId().equals(participante.getId())) {
                return equipo;
            }
        }

        return null;
    }

    private void clickOnRemoveImage(int position) {
        final Torneo torneo = (Torneo) torneos.get(position);

        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
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
                Intent intent = new Intent(getActivity().getApplicationContext(), TorneoList.class);
                startActivity(intent);
            } else {
                Toast toast1 =
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Error al Eliminar el Torneo, intentelo más tarde", Toast.LENGTH_SHORT);
                toast1.show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al eliminar el Torneo " + torneo.getDescripcion());
        }
    }

    private List<Map<String, String>> buildHashMap(List<String> torneos) {
        List<Map<String, String>> aList = new ArrayList<>();

        for (String name : torneos) {
            Map<String, String> hm = new TreeMap<>();
            hm.put("1", String.valueOf(R.drawable.torneo_lista));
            hm.put("2", name);
            hm.put("3", "");
            //hm.put("3", String.valueOf(R.drawable.eliminar));
           // hm.put("listview_eliminar", String.valueOf(R.drawable.eliminar));
            //hm.put("listview_eliminar_image", String.valueOf(R.drawable.eliminar));
            aList.add(hm);
        }

        return aList;
    }

    private List<String> getTorneosNameList() throws Exception {
        List<String> torneosNombres = new ArrayList<>();

        ParticipantePreference preferences = new ParticipantePreference(getContext().getApplicationContext());
        Participante participante = preferences.getParticipante();

        TorneosService service = new TorneosService(participante.getId(), getResources());
        service.execute();

        List torneos = service.get();
        if (torneos != null) {
            Log.i(TAG, "Trayendo Torneos del Participante " + participante.getId());
            this.torneos = torneos;
            return extractNamesFromList(torneos);
        }

        return torneosNombres;
    }

    private List<String> extractNamesFromList(List<Torneo> torneos) {
        List<String> nombres = new ArrayList<>();
        for (Torneo torneo : torneos) {
            nombres.add(torneo.getDescripcion());
        }

        return nombres;
    }
}
