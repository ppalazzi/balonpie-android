package com.palazzisoft.ligabalonpie.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.activity.DetallesTorneo;
import com.palazzisoft.ligabalonpie.activity.ElegirEquipoActivity;
import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.activity.TorneoList;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;
import com.palazzisoft.ligabalonpie.service.RemoveTorneoService;
import com.palazzisoft.ligabalonpie.service.TorneosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        View root = inflater.inflate(R.layout.fragment_mis_torneos, container, false);

        ListView torneosView = (ListView) root.findViewById(R.id.torneos_list);
        addListenerContext(torneosView);

        try {
            String[] from = {"listview_name", "listview_image"};
            int[] to = {R.id.listview_torneo_name, R.id.listview_eliminar_image};

            List<HashMap<String, String>> aList = buildHashMap(getTorneosNameList());
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getBaseContext(), aList,
                    R.layout.custom_listview_mis_torneos, from, to);
            torneosView.setAdapter(simpleAdapter);

        } catch (Exception e) {
            Log.e("Error al traer Torneos", TAG);
        }

        return root;
    }

    private void addListenerContext(ListView torneosView) {
        torneosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LinearLayout linearLayoutParent = (LinearLayout) view;
                LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(0);

                TextView textView = (TextView) linearLayoutChild.getChildAt(0);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickOnTorneoDetails(position);
                    }
                });

                LinearLayout linearLayoutChild2 = (LinearLayout) linearLayoutChild.getChildAt(1);

                ImageView imageView = (ImageView) linearLayoutChild2.getChildAt(0);
                imageView.setOnClickListener(new View.OnClickListener() {
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

        Intent intent = new Intent(getActivity().getApplicationContext(), DetallesTorneo.class);
        intent.putExtra("torneoId", torneoSelected.getId());
        startActivity(intent);
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

    private List<HashMap<String, String>> buildHashMap(List<String> torneos) {
        List<HashMap<String, String>> aList = new ArrayList<>();

        for (String name : torneos) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_name", name);
            hm.put("listview_image", String.valueOf(R.drawable.eliminar));
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
