package com.mkir.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mkir.Constants;
import com.mkir.MainActivity;
import com.mkir.R;
import com.mkir.datastreams.PatientList;
import com.mkir.datastreams.UpComingList;
import com.mkir.fragments.Patient;

import java.util.ArrayList;

/**
 * Created by nyulg on 2017. 09. 20..
 */

public class UpComing extends RecyclerView.Adapter<UpComing.ViewHolder> {
    View view;
    SharedPreferences pref;
    private Context context;
    private ArrayList<UpComingList> android;


    public UpComing(ArrayList<UpComingList> android) {
        this.android = android;
    }

    @Override
    public UpComing.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_upcoming, viewGroup, false);
        return new UpComing.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UpComing.ViewHolder viewHolder, int i) {

        viewHolder.tv_upcoming_name.setText(android.get(i).getSzemely_nev());
        viewHolder.tv_upcoming_date.setText(android.get(i).getElojegyzes_nap()+" "+android.get(i).getElojegyzes_ido());
        /*viewHolder.tv_my_patient_id.setText(android.get(i).getSzemely_id());
        viewHolder.tv_my_patient_id.setVisibility(View.GONE);*/
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_upcoming_name, tv_upcoming_date;
        //private LinearLayout mypatient;
        public ViewHolder(View view) {
            super(view);

            tv_upcoming_name = (TextView)view.findViewById(R.id.upcoming_name);
            tv_upcoming_date = (TextView) view.findViewById(R.id.upcoming_time);
            /*mypatient = (LinearLayout) view.findViewById(R.id.my_patient);
            mypatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mypatientid = tv_my_patient_id.getText().toString();
                    String mypatientname= tv_my_patient_name.getText().toString();
                    pref = ((MainActivity)v.getContext()).getPreferences(0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.SZEMELY_ID, mypatientid);
                    editor.putString(Constants.SZEMELY_NEV, mypatientname);
                    editor.apply();
                    Patient patient = new Patient();
                    FragmentManager fragmentManager = ((FragmentActivity)v.getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, patient, patient.getTag())
                            .addToBackStack(null)
                            .commit();
                }
            });*/

        }
    }


}