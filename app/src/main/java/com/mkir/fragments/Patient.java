package com.mkir.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mkir.Constants;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.datastreams.*;
import com.mkir.interfaces.LoginInterface;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Patient extends Fragment {
    SharedPreferences pref;
    BottomNavigationView patientbottom;
    TextView patientname;
    TextView patientlog_header;
    RecyclerView patientlog_rv;
    String szemely_id;
    com.mkir.adapters.PatientLog adapter_plog;
    BottomNavigationItemView diagnozisok, anamnezisek, leletek;
    RelativeLayout patientdata;
    View patient;

    public Patient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        patient = inflater.inflate(R.layout.fragment_patient, container, false);
        getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.GONE);
        pref = getActivity().getPreferences(0);

        patientlog_header = (TextView) patient.findViewById(R.id.patientlog_header);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        patientlog_rv= (RecyclerView) patient.findViewById(R.id.patientlog_recyclerview);
        patientlog_rv.setLayoutManager(layoutManager);

        patientdata = (RelativeLayout) patient.findViewById(R.id.patientdata);

        szemely_id = pref.getString(Constants.SZEMELY_ID,"");

        pref=getActivity().getPreferences(0);
        String szemely_neve = pref.getString(Constants.SZEMELY_NEV,"");
        patientname = (TextView) patient.findViewById(R.id.patientname);
        patientname.setText(szemely_neve);

        patientbottom = (BottomNavigationView) patient.findViewById(R.id.patient_navbar);

        diagnozisok = (BottomNavigationItemView) patientbottom.findViewById(R.id.action_diagnozisok);
        anamnezisek = (BottomNavigationItemView) patientbottom.findViewById(R.id.action_anamnezisek);
        leletek = (BottomNavigationItemView) patientbottom.findViewById(R.id.action_leletek);

        diagnozisok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientlog_header.setText("Diagnózisok");
                loadJSON(szemely_id, Constants.PATIENT_LOG_DIAG);
            }
        });

        anamnezisek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientlog_header.setText("Anamnézisek");
                loadJSON(szemely_id, Constants.PATIENT_LOG_ANAM);
            }
        });

        leletek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientlog_header.setText("Leletek");
                loadJSON(szemely_id, Constants.PATIENT_LOG_LELET);
            }
        });


        patientdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientData patientData = new PatientData();
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.patient_frame, patientData, patientData.getTag())
                        .addToBackStack("2")
                        .commit();
                patientbottom.setVisibility(View.GONE);
            }
        });
        patientlog_header.setText("Diagnózisok");
        loadJSON(szemely_id, Constants.PATIENT_LOG_DIAG);

        return patient;
    }

    private void loadJSON(String szemely_id, String operation){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        com.mkir.datastreams.PatientLog patientLog = new com.mkir.datastreams.PatientLog();
        patientLog.setSzemely_id(szemely_id);
        ServerRequest request = new ServerRequest();
        request.setOperation(operation);
        request.setPatientLog(patientLog);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                ArrayList<com.mkir.datastreams.PatientLog> data = new ArrayList<>(Arrays.asList(resp.getPatientLog()));
                adapter_plog=new com.mkir.adapters.PatientLog(data);
                patientlog_rv.setAdapter(adapter_plog);


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Snackbar.make(getView(), t.getLocalizedMessage(), LENGTH_LONG).show();
            }
        });
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
                    getFragmentManager().popBackStack();
                    getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });

    }


}
