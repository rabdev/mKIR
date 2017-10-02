package com.mkir.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mkir.R;

import static android.view.KeyEvent.*;
import static android.view.KeyEvent.ACTION_UP;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEvent extends Fragment {

    private ImageView addevent_back;

    public AddEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addevent = inflater.inflate(R.layout.fragment_add_event, container, false);
        getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.GONE);
        addevent_back= (ImageView) addevent.findViewById(R.id.addevent_back);
        addevent_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.VISIBLE);
                getActivity().onBackPressed();
            }
        });
        BottomNavigationView addevent_navbar = (BottomNavigationView) addevent.findViewById(R.id.addevent_navbar);
        BottomNavigationItemView addevent_pipa = (BottomNavigationItemView) addevent_navbar.findViewById(R.id.action_esemeny_pipa);
        BottomNavigationItemView addevent_megegy = (BottomNavigationItemView) addevent_navbar.findViewById(R.id.action_megegy);
        BottomNavigationItemView addevent_megse = (BottomNavigationItemView) addevent_navbar.findViewById(R.id.action_esemeny_megse);
        addevent_pipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Mentés és bezárás", Toast.LENGTH_SHORT).show();
            }
        });
        addevent_megegy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Mentés és új", Toast.LENGTH_SHORT).show();
            }
        });
        addevent_megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.VISIBLE);
                getActivity().onBackPressed();
            }
        });

        return addevent;
    }
    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == ACTION_UP && keyCode == KEYCODE_BACK) {
                    // handle back button's click listener
                    getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.VISIBLE);
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }

}
