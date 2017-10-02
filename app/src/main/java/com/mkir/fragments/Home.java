package com.mkir.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkir.Constants;
import com.mkir.adapters.UpComing;
import com.mkir.datastreams.PatientList;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.adapters.MyPatients;
import com.mkir.datastreams.UpComingList;
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
public class Home extends Fragment {

    SharedPreferences pref;
    RecyclerView mypatients, upcoming;
    MyPatients adapter_pl;
    UpComing adapter_ul;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        pref=getActivity().getPreferences(0);
        mypatients = (RecyclerView) home.findViewById(R.id.my_patient_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        upcoming = (RecyclerView) home.findViewById(R.id.upcoming_recyclerview);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());

        mypatients.setLayoutManager(layoutManager);
        upcoming.setLayoutManager(layoutManager1);

        String unique_id = pref.getString(Constants.UNIQUE_ID,"");
        loadUpcoming(unique_id);
        loadJSON(unique_id);

        return home;
    }

    private void loadJSON(String unique_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        PatientList patient = new PatientList();
        patient.setUnique_id(unique_id);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.PATIENT_LIST);
        request.setPatientList(patient);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                ArrayList<PatientList> data = new ArrayList<>(Arrays.asList(resp.getPatientList()));
                adapter_pl=new MyPatients(data);
                mypatients.setAdapter(adapter_pl);


                //Snackbar.make(getView(), data, LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Snackbar.make(getView(), t.getLocalizedMessage(), LENGTH_LONG).show();
            }
        });
    }

   private void loadUpcoming(String unique_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        UpComingList upComingList = new UpComingList();
        upComingList.setSzemely_id(unique_id);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPCOMING_LIST);
        request.setUpComingList(upComingList);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                ArrayList<UpComingList> upcl = new ArrayList<>(Arrays.asList(resp.getUpComingList()));
                adapter_ul=new UpComing(upcl);
                upcoming.setAdapter(adapter_ul);


                //Snackbar.make(getView(), data, LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Snackbar.make(getView(), t.getLocalizedMessage(), LENGTH_LONG).show();
            }
        });
    }

}
