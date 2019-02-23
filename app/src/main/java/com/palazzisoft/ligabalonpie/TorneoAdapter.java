package com.palazzisoft.ligabalonpie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palazzisoft.ligabalonpie.activity.DetallesTorneo;
import com.palazzisoft.ligabalonpie.activity.R;
import com.palazzisoft.ligabalonpie.activity.TorneoList;
import com.palazzisoft.ligabalonpie.dto.Equipo;
import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.dto.Torneo;
import com.palazzisoft.ligabalonpie.preference.EquipoPreferences;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;
import com.palazzisoft.ligabalonpie.preference.TorneoPreference;
import com.palazzisoft.ligabalonpie.service.RemoveTorneoService;

import java.util.List;


public class TorneoAdapter extends BaseAdapter {

    private static final String TAG = "TorneoAdapter";

    private Context context;
    private int[] images;
    private List<Torneo> torneos;
    private String[] buttones;
    private Activity activity;

    public TorneoAdapter(Context context, int[] images, List<Torneo> torneos, String[] buttones,
                         Activity activity) {
        this.context = context;
        this.images = images;
        this.torneos = torneos;
        this.buttones = buttones;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return torneos.size();
    }

    @Override
    public Object getItem(int position) {
        return torneos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview_mis_torneos, null);
        }

        final Torneo torneo = torneos.get(position);

        TextView text = (TextView) view.findViewById(R.id.listview_torneo_name);
        text.setTextColor(Color.BLACK);
        text.setText(torneo.getDescripcion());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnTorneoDetails(torneo);
            }
        });

        ImageView image = (ImageView)view.findViewById(R.id.torneo_icon);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnTorneoDetails(torneo);
            }
        });

        Button button = (Button) view.findViewById(R.id.listview_eliminar_image);
        button.setBackgroundColor(Color.parseColor("#FC543D"));
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnRemoveImage(torneo);
            }
        });

        return view;
    }

    private void clickOnTorneoDetails(Torneo torneo) {
        TorneoPreference torneoPreference = new TorneoPreference(context);
        torneoPreference.saveTorneo(torneo);

        EquipoPreferences equipoPreferences = new EquipoPreferences(context);
        equipoPreferences.saveEquipo(traerEquipoDelTorneoYParticpante(torneo));

        Intent intent = new Intent(activity.getApplicationContext(), DetallesTorneo.class);
        intent.putExtra("torneoId", torneo.getId());
        activity.startActivity(intent);
    }

    private Equipo traerEquipoDelTorneoYParticpante(Torneo torneo) {
        ParticipantePreference participantePreference = new ParticipantePreference(context);
        Participante participante = participantePreference.getParticipante();

        for (Equipo equipo : torneo.getEquipos()) {
            if (equipo.getParticipante().getId().equals(participante.getId())) {
                return equipo;
            }
        }

        return null;
    }

    private void clickOnRemoveImage(final Torneo torneo) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
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
        RemoveTorneoService removeTorneoService = new RemoveTorneoService(context.getResources(), torneo.getId());
        removeTorneoService.execute();

        try {
            Torneo torneoNew = removeTorneoService.get();

            if (torneoNew != null) {
                Intent intent = new Intent(context, TorneoList.class);
                context.startActivity(intent);
            } else {
                Toast toast1 =
                        Toast.makeText(context,
                                "Error al Eliminar el Torneo, intentelo más tarde", Toast.LENGTH_SHORT);
                toast1.show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al eliminar el Torneo " + torneo.getDescripcion());
        }
    }

}

