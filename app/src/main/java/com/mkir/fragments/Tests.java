package com.mkir.fragments;


import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tests extends Fragment {


    public Tests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View tests = inflater.inflate(R.layout.fragment_tests, container, false);

        return tests;
    }

}
