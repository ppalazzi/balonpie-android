package com.palazzisoft.ligabalonpie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;
import com.palazzisoft.ligabalonpie.service.TorneosService;

import java.util.ArrayList;
import java.util.List;

public class MisTorneosFragment extends Fragment {

    private static final String TAG = "MisTorneosFragment";


    public MisTorneosFragment() {
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

        try {
            ArrayAdapter<String> torneos = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_activated_1, getTorneosNameList());
            torneosView.setAdapter(torneos);
        }
        catch (Exception e) {
            Log.e("Error al traer Torneos", TAG);
        }

        return root;
    }

    private List<String> getTorneosNameList() throws Exception {
        ParticipantePreference preferences = new ParticipantePreference(getContext().getApplicationContext());
        Participante participante = preferences.getParticipante();

        TorneosService service = new TorneosService(participante.getId(), getResources());
        service.execute();

        List torneos = service.get();
        if (torneos != null) {
            Log.i(TAG, "Trayendo Torneos del Participante " + participante.getId());
            return torneos;
        }

        return new ArrayList<>();
    }
}
