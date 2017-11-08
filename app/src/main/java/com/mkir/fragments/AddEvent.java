package com.mkir.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.util.*;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mkir.Constants;
import com.mkir.MainActivity;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.datastreams.Elojegyzes;
import com.mkir.datastreams.PatientList;
import com.mkir.interfaces.LoginInterface;


import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.KeyEvent.*;
import static android.view.KeyEvent.ACTION_UP;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEvent extends Fragment {

    SharedPreferences pref;
    AlertDialog dialog;
    TimePicker timePicker;
    DatePicker datePicker;
    LinearLayout date_picker, time_picker, doctor_picker;
    TextView date, time, doctor;
    View dialogtp, dialogdp, addevent;
    EditText addtaj, addszemelynev, megjegyzes, naploszam;
    FragmentManager fragmentManager;

    public AddEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addevent = inflater.inflate(R.layout.fragment_add_event, container, false);
        fragmentManager = getChildFragmentManager();

        pref = getActivity().getPreferences(0);
        getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.GONE);
        date_picker= (LinearLayout) addevent.findViewById(R.id.date_picker);
        time_picker = (LinearLayout) addevent.findViewById(R.id.time_picker);
        doctor_picker = (LinearLayout) addevent.findViewById(R.id.doctor_picker);

        date = (TextView) addevent.findViewById(R.id.addevent_date);
        time = (TextView) addevent.findViewById(R.id.addevent_time);
        doctor = (TextView) addevent.findViewById(R.id.addevent_doctor);

        addtaj= (EditText) addevent.findViewById(R.id.addtaj);
        addszemelynev = (EditText) addevent.findViewById(R.id.add_szemely_nev);
        megjegyzes = (EditText) addevent.findViewById(R.id.addmegjegyzes);
        naploszam = (EditText) addevent.findViewById(R.id.addnaploszam);


        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        doctor_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorsList doctorsList = new DoctorsList();
                fragmentManager.beginTransaction()
                        .add(R.id.addevent_frame, doctorsList, doctorsList.getTag())
                        .addToBackStack("child")
                        .commit();
            }
        });

        addtaj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (addtaj.getText().toString().trim().length()<9 && addtaj.getText().toString().trim().length()>2){

                    SearchPatient searchPatient = new SearchPatient();
                    fragmentManager.beginTransaction()
                            .add(R.id.addevent_frame, searchPatient, searchPatient.getTag())
                            .addToBackStack("searchpatient")
                            .commit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BottomNavigationView addevent_navbar = (BottomNavigationView) addevent.findViewById(R.id.addevent_navbar);
        BottomNavigationItemView addevent_pipa = (BottomNavigationItemView) addevent_navbar.findViewById(R.id.action_esemeny_pipa);
        BottomNavigationItemView addevent_megse = (BottomNavigationItemView) addevent_navbar.findViewById(R.id.action_esemeny_megse);
        addevent_pipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSON();
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

    private void sendJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        Elojegyzes elojegyzes = new Elojegyzes();
        elojegyzes.setBekuldoOrvos(pref.getString(Constants.UNIQUE_ID,""));
        elojegyzes.setBekuldoOsztaly(pref.getString(Constants.OSZTALY,""));
        elojegyzes.setElojegyzes("");
        elojegyzes.setElojegyzes_nap(date.getText().toString());
        elojegyzes.setElojegyzes_ido(time.getText().toString());
        elojegyzes.setMegjegyzes(megjegyzes.getText().toString());
        elojegyzes.setNaplo_szam(naploszam.getText().toString());
        elojegyzes.setSzemely_nev(pref.getString(Constants.SZEMELY_NEV,""));
        elojegyzes.setStatusz("Kérés");
        elojegyzes.setSzemely_id(pref.getString(Constants.SZEMELY_ID,""));
        elojegyzes.setVegzoOrvos(pref.getString(Constants.DOC_ID,""));
        elojegyzes.setVegzoOsztaly(pref.getString(Constants.DOC_OSZTALY_ID,""));
        elojegyzes.setRogzitoId(pref.getString(Constants.UNIQUE_ID,""));
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ADD_ELOJEGYZES);
        request.setElojegy(elojegyzes);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
              
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }

        });
    }

    /*private void loadJSON(String tajazonosito){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        PatientList patient = new PatientList();
        patient.setTaj(tajazonosito);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.SEARCH_PATIENT);
        request.setPatientList(patient);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                ArrayList<String> data = new ArrayList<>(Arrays.asList(resp.getPatient().getTaj()));
                ArrayAdapter arrayAdapter =new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,data);
                addtaj.setAdapter(arrayAdapter);

                //Snackbar.make(getView(), data, LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }*/


    private void showDatePicker () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater.from(getContext()));
        dialogdp = inflater.inflate(R.layout.dialog_datepicker, null);
        datePicker = (DatePicker) dialogdp.findViewById(R.id.datepicker);
        datePicker.setFirstDayOfWeek(Calendar.MONDAY);

        builder.setView(dialogdp);

        builder.setPositiveButton("Mentés", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date.setText(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());

            }
        });
        builder.setNegativeButton("Bezárás", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();

    }

    private void showTimePicker () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater.from(getContext()));
        dialogtp = inflater.inflate(R.layout.dialog_timepicker, null);
        timePicker = (TimePicker) dialogtp.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        builder.setView(dialogtp);
        builder.setPositiveButton("Mentés", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time.setText(timePicker.getHour()+":"+timePicker.getMinute());

            }
        });
        builder.setNegativeButton("Bezárás", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();


        dialog.show();

    }

}
