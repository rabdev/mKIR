package com.mkir.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mkir.Constants;
import com.mkir.MainActivity;
import com.mkir.R;
import com.mkir.datastreams.Doctors;
import com.mkir.fragments.AddEvent;

import java.util.ArrayList;

/**
 * Created by nyulg on 2017. 10. 11..
 */

public class DoctorsList extends RecyclerView.Adapter<DoctorsList.ViewHolder> {
    View view;
    SharedPreferences pref;
    private Context context;
    private ArrayList<Doctors> android;


    public DoctorsList(ArrayList<Doctors> android) {
        this.android = android;
    }

    @Override
    public DoctorsList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_doctors, viewGroup, false);
        context = viewGroup.getContext();
        return new DoctorsList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorsList.ViewHolder viewHolder, int i) {

        viewHolder.tv_doc_name.setText(android.get(i).getUserName());
        viewHolder.tv_doc_id.setText(android.get(i).getUser_id());
        viewHolder.tv_osztaly_id.setText(android.get(i).getOsztaly_id());
        viewHolder.tv_osztaly_name.setText(android.get(i).getOsztaly_nev());
        viewHolder.tv_doc_id.setVisibility(View.INVISIBLE);
        viewHolder.tv_osztaly_id.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_doc_name, tv_doc_id, tv_osztaly_id, tv_osztaly_name;
        public ViewHolder(View view) {
            super(view);

            tv_doc_name = (TextView) view.findViewById(R.id.doctor_name);
            tv_doc_id = (TextView) view.findViewById(R.id.doctor_id);
            tv_osztaly_id = (TextView) view.findViewById(R.id.doctor_osztalyid);
            tv_osztaly_name = (TextView) view.findViewById(R.id.doctor_osztaly);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String doc_name = tv_doc_name.getText().toString();
                    String doc_id = tv_doc_id.getText().toString();
                    String osztaly_id=tv_osztaly_id.getText().toString();
                    pref = ((MainActivity)v.getContext()).getPreferences(0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.DOC_ID, doc_id);
                    editor.putString(Constants.DOC_OSZTALY_ID,osztaly_id);
                    editor.putString(Constants.DOC_NAME,doc_name);
                    //Toast.makeText(v.getContext(),doc_name, Toast.LENGTH_SHORT).show();
                    editor.apply();
                    ((MainActivity)v.getContext()).dispatchKeyEvent(new KeyEvent (KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                }
            });
        }
    }


}
