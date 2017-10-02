package com.mkir.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTest extends Fragment {


    public AddTest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addtest = inflater.inflate(R.layout.fragment_add_test, container, false);
        getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.GONE);
        return addtest;
    }
    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
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
