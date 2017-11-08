package com.mkir.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkir.Constants;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.datastreams.Doctors;
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
public class DoctorsList extends Fragment {

    RecyclerView doctorslist_rv;
    com.mkir.adapters.DoctorsList adapter_dl;
    SharedPreferences pref;
    AddEvent addEvent;

    public DoctorsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View doctorslist =inflater.inflate(R.layout.fragment_doctorslist, container, false);
        pref = getActivity().getPreferences(0);
        addEvent = (AddEvent) getParentFragment();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        doctorslist_rv= (RecyclerView) doctorslist.findViewById(R.id.doctors_recyclerview);
        doctorslist_rv.setLayoutManager(layoutManager);

        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                addEvent.doctor.setText(pref.getString(Constants.DOC_NAME,""));
            }
        });

        loadJSON();

        return doctorslist;
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DOCTORS_LIST);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                ArrayList<Doctors> data = new ArrayList<>(Arrays.asList(resp.getDoctorList()));
                adapter_dl=new com.mkir.adapters.DoctorsList(data);
                doctorslist_rv.setAdapter(adapter_dl);


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
