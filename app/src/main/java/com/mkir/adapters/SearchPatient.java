package com.mkir.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mkir.Constants;
import com.mkir.MainActivity;
import com.mkir.R;
import com.mkir.datastreams.PatientList;
import com.mkir.fragments.AddEvent;

import java.util.ArrayList;

/**
 * Created by nyulg on 2017. 10. 22..
 */

public class SearchPatient extends RecyclerView.Adapter<SearchPatient.ViewHolder> {
    View view;
    SharedPreferences pref;
    private ArrayList<PatientList> android;


    public SearchPatient(ArrayList<PatientList> android) {
        this.android = android;
    }

    @Override
    public SearchPatient.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_searchpatient, viewGroup, false);
        return new SearchPatient.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchPatient.ViewHolder viewHolder, int i) {

        viewHolder.tv_sp_szemely_id.setText(android.get(i).getSzemely_id());
        viewHolder.tv_sp_taj.setText(android.get(i).getTaj());
        viewHolder.tv_sp_szemely_nev.setText(android.get(i).getSzemely_nev());
        viewHolder.tv_sp_szemely_id.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_sp_szemely_nev, tv_sp_szemely_id, tv_sp_taj;
        public ViewHolder(View view) {
            super(view);

            tv_sp_szemely_id = (TextView)view.findViewById(R.id.sp_szemely_id);
            tv_sp_szemely_nev = (TextView) view.findViewById(R.id.sp_szemely_name);
            tv_sp_taj = (TextView) view.findViewById(R.id.sp_taj);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String szemely_id = tv_sp_szemely_id.getText().toString();
                    String szemely_nev = tv_sp_szemely_nev.getText().toString();
                    String taj = tv_sp_taj.getText().toString();
                    pref = ((MainActivity)v.getContext()).getPreferences(0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.SZEMELY_ID,szemely_id);
                    editor.putString(Constants.SZEMELY_NEV,szemely_nev);
                    editor.putString(Constants.TAJ,taj);
                    editor.apply();
                    ((MainActivity)v.getContext()).dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                }
            });

        }
    }
}
