package com.mkir.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mkir.Constants;
import com.mkir.MainActivity;
import com.mkir.R;

import java.util.ArrayList;

/**
 * Created by nyulg on 2017. 10. 15..
 */

public class TajType extends RecyclerView.Adapter<TajType.ViewHolder> {
    View view;
    SharedPreferences pref;
    private Context context;
    private ArrayList<com.mkir.datastreams.TajType> android;


    public TajType(ArrayList<com.mkir.datastreams.TajType> android) {
        this.android = android;
    }

    @Override
    public TajType.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_tajtype, viewGroup, false);
        return new TajType.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TajType.ViewHolder viewHolder, int i) {

        viewHolder.tv_taj_type.setText(android.get(i).getTaj_tipus());
        viewHolder.tv_taj_type_id.setText(android.get(i).getTaj_tipus_id());
        viewHolder.tv_taj_type_id.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_taj_type, tv_taj_type_id;
        public ViewHolder(View view) {
            super(view);

            tv_taj_type = (TextView) view.findViewById(R.id.tajtype_name);
            tv_taj_type_id = (TextView) view.findViewById(R.id.tajtype_id);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String taj_type = tv_taj_type.getText().toString();
                    String taj_type_id = tv_taj_type_id.getText().toString();
                    pref = ((MainActivity)v.getContext()).getPreferences(0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.TAJ_TIPUS, taj_type);
                    editor.putString(Constants.TAJ_TYPE_ID,taj_type_id);
                    editor.apply();
                    ((MainActivity)v.getContext()).getSupportFragmentManager().popBackStackImmediate();
                }
            });
        }
    }


}
