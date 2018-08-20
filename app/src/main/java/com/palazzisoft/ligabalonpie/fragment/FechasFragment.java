package com.palazzisoft.ligabalonpie.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.dto.Fecha;
import com.palazzisoft.ligabalonpie.dto.Partido;

public class FechasFragment extends Fragment {

    private Fecha fecha;

    private TextView golLocValue;
    private TextView localEquipo;
    private TextView golVisValue;
    private TextView visitanteEquipo;

    private TextView golLocValue2;
    private TextView localEquipo2;
    private TextView golVisValue2;
    private TextView visitanteEquipo2;

    public FechasFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.fecha = (Fecha) bundle.getSerializable("fecha");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fechas, container, false);
        initiateView(view);
        loadData();

        return view;
    }

    private void initiateView(View view) {
        this.golLocValue = (TextView) view.findViewById(R.id.golLocValue);
        this.localEquipo = (TextView)view.findViewById(R.id.localEquipo);
        this.golVisValue = (TextView)view.findViewById(R.id.golVisValue);
        this.visitanteEquipo = (TextView) view.findViewById(R.id.visitanteEquipo);

        this.golLocValue2 = (TextView) view.findViewById(R.id.golLocValue2);
        this.localEquipo2 = (TextView) view.findViewById(R.id.localEquipo2);
        this.golVisValue2 = (TextView) view.findViewById(R.id.golVisValue2);
        this.visitanteEquipo2 = (TextView) view.findViewById(R.id.visitanteEquipo2);
    }

    private void loadData() {
        Partido partido1 = fecha.getPartidos().get(0);
        Partido partido2 = fecha.getPartidos().get(1);
        Partido partido3 = fecha.getPartidos().get(2);
        Partido partido4 = fecha.getPartidos().get(3);

        this.golLocValue.setText(partido1.getGolesLocal().toString());
        this.localEquipo.setText(partido1.getLocal().getNombre());
        this.golVisValue.setText(partido1.getGolesVisitante().toString());
        this.visitanteEquipo.setText(partido1.getVisitante().getNombre());

        this.golLocValue2.setText(partido2.getGolesLocal().toString());
        this.localEquipo2.setText(partido2.getLocal().getNombre());
        this.golVisValue2.setText(partido2.getGolesVisitante().toString());
        this.visitanteEquipo2.setText(partido2.getVisitante().getNombre());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
