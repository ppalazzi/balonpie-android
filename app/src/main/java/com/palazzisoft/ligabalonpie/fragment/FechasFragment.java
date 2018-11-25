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

    private TextView golLocValue3;
    private TextView localEquipo3;
    private TextView golVisValue3;
    private TextView visitanteEquipo3;

    private TextView golLocValue4;
    private TextView localEquipo4;
    private TextView golVisValue4;
    private TextView visitanteEquipo4;

    private TextView golLocValue5;
    private TextView localEquipo5;
    private TextView golVisValue5;
    private TextView visitanteEquipo5;

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

        this.golLocValue3 = (TextView) view.findViewById(R.id.golLocValue3);
        this.localEquipo3 = (TextView) view.findViewById(R.id.localEquipo3);
        this.golVisValue3 = (TextView) view.findViewById(R.id.golVisValue3);
        this.visitanteEquipo3 = (TextView) view.findViewById(R.id.visitanteEquipo3);

        this.golLocValue4 = (TextView) view.findViewById(R.id.golLocValue4);
        this.localEquipo4 = (TextView) view.findViewById(R.id.localEquipo4);
        this.golVisValue4 = (TextView) view.findViewById(R.id.golVisValue4);
        this.visitanteEquipo4 = (TextView) view.findViewById(R.id.visitanteEquipo4);

        this.golLocValue5 = (TextView) view.findViewById(R.id.golLocValue5);
        this.localEquipo5 = (TextView) view.findViewById(R.id.localEquipo5);
        this.golVisValue5 = (TextView) view.findViewById(R.id.golVisValue5);
        this.visitanteEquipo5 = (TextView) view.findViewById(R.id.visitanteEquipo5);
    }

    private void loadData() {
        Partido partido1 = fecha.getPartidos().get(0);
        Partido partido2 = fecha.getPartidos().get(1);
        Partido partido3 = fecha.getPartidos().get(2);
        Partido partido4 = fecha.getPartidos().get(3);
        Partido partido5 = fecha.getPartidos().get(4);

        this.golLocValue.setText(partido1.mostrarGolesLocal());
        this.localEquipo.setText(partido1.getLocal().getNombre());
        this.golVisValue.setText(partido1.mostrarGolesVisitante());
        this.visitanteEquipo.setText(partido1.getVisitante().getNombre());

        this.golLocValue2.setText(partido2.mostrarGolesLocal());
        this.localEquipo2.setText(partido2.getLocal().getNombre());
        this.golVisValue2.setText(partido2.mostrarGolesVisitante());
        this.visitanteEquipo2.setText(partido2.getVisitante().getNombre());

        this.golLocValue3.setText(partido3.mostrarGolesLocal());
        this.localEquipo3.setText(partido3.getLocal().getNombre());
        this.golVisValue3.setText(partido3.mostrarGolesVisitante());
        this.visitanteEquipo3.setText(partido3.getVisitante().getNombre());

        this.golLocValue4.setText(partido4.mostrarGolesLocal());
        this.localEquipo4.setText(partido4.getLocal().getNombre());
        this.golVisValue4.setText(partido4.mostrarGolesVisitante());
        this.visitanteEquipo4.setText(partido4.getVisitante().getNombre());

        this.golLocValue5.setText(partido5.mostrarGolesLocal());
        this.localEquipo5.setText(partido5.getLocal().getNombre());
        this.golVisValue5.setText(partido5.mostrarGolesVisitante());
        this.visitanteEquipo5.setText(partido5.getVisitante().getNombre());
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
