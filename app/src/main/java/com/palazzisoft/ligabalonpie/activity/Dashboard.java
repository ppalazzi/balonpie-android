package com.palazzisoft.ligabalonpie.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.palazzisoft.ligabalonpie.dto.Participante;
import com.palazzisoft.ligabalonpie.fragment.HomeFragment;
import com.palazzisoft.ligabalonpie.fragment.MiPerfil;
import com.palazzisoft.ligabalonpie.preference.ParticipantePreference;

public class Dashboard extends AppCompatActivity {

    private String[] menus;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        menus = getResources().getStringArray(R.array.lista_barra);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, menus));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(savedInstanceState == null) {
    //        selectItem(0);
        }

        drawerToogle = buildAtionBarDrawerToggle();
        drawerToogle.setDrawerIndicatorEnabled(false);
    }

    private ActionBarDrawerToggle buildAtionBarDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    };

    private void selectItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0 :
                fragment = new HomeFragment();
                break;
            case 1 :
            case 2 :
                ParticipantePreference preference = new ParticipantePreference(getApplicationContext());
                preference.saveParticipante(new Participante());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToogle.onConfigurationChanged(newConfig);
    }
}
