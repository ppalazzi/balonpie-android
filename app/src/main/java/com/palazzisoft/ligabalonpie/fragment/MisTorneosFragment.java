package com.palazzisoft.ligabalonpie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.palazzisoft.ligabalonpie.activity.R;

public class MisTorneosFragment extends Fragment {


    public MisTorneosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mis_torneos, container, false);
    }

}
