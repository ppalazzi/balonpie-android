package com.palazzisoft.ligabalonpie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.fragment.MisTorneosFragment;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;

public class TorneoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torneo_list);

        MisTorneosFragment fragment = (MisTorneosFragment) getSupportFragmentManager().
                findFragmentById(R.id.torneos_container);

        if (fragment == null) {
            fragment = MisTorneosFragment.getInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.torneos_container, fragment).commit();
        }
    }

    private Participante getParticipanteFromSession() {
        ParticipantePreference preference = new ParticipantePreference(getApplicationContext());
        return preference.getParticipante();
    }
}
