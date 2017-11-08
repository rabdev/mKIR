package com.mkir.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mkir.Constants;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.datastreams.PatientList;
import com.mkir.interfaces.LoginInterface;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPatient extends Fragment {

    RecyclerView searchpatient_rv;
    EditText et_searchpatient;
    com.mkir.adapters.SearchPatient adapter_sp;
    SharedPreferences pref;
    AddEvent addEvent;

    public SearchPatient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View searchpatient=inflater.inflate(R.layout.fragment_search_patient, container, false);
        pref = getActivity().getPreferences(0);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        searchpatient_rv= (RecyclerView) searchpatient.findViewById(R.id.searchpatient_recyclerview);
        searchpatient_rv.setLayoutManager(layoutManager);
        searchpatient_rv.setFocusableInTouchMode(false);

        et_searchpatient = (EditText) searchpatient.findViewById(R.id.et_searchpatient);
        et_searchpatient.setFocusableInTouchMode(true);
        et_searchpatient.setFocusable(true);
        et_searchpatient.requestFocus();
        et_searchpatient.isFocused();
        et_searchpatient.setActivated(true);

        addEvent = (AddEvent) getParentFragment();
        et_searchpatient.setText(addEvent.addtaj.getText().toString());
        et_searchpatient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadJSON(et_searchpatient.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                addEvent.addtaj.setText(pref.getString(Constants.TAJ,""));
                addEvent.addszemelynev.setText(pref.getString(Constants.SZEMELY_NEV,""));
            }
        });

        loadJSON(et_searchpatient.getText().toString());

        return searchpatient;
    }

    private void loadJSON(String taj){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        ServerRequest request = new ServerRequest();
        PatientList patientList = new PatientList();
        patientList.setTaj(taj);
        request.setOperation(Constants.SEARCH_PATIENT);
        request.setPatientList(patientList);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                ArrayList<PatientList> data = new ArrayList<>(Arrays.asList(resp.getPatientList()));
                adapter_sp= new com.mkir.adapters.SearchPatient(data);
                searchpatient_rv.setAdapter(adapter_sp);


                //Snackbar.make(getView(), data, LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
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
                    getParentFragment().getChildFragmentManager().popBackStackImmediate();
                    return true;
                }
                return false;
            }
        });

    }

}
