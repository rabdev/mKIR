package com.mkir.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mkir.Constants;
import com.mkir.MainActivity;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.datastreams.Anamnezis;
import com.mkir.interfaces.LoginInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by nyulg on 2017. 09. 23..
 */

public class PatientLog extends RecyclerView.Adapter<PatientLog.ViewHolder> {
    View view;
    TextView patientlog_text;
    WebView patientlog_webview;
    AlertDialog dialog;
    int x;
    SharedPreferences pref;
    private Context context;
    private ArrayList<com.mkir.datastreams.PatientLog> android;


    public PatientLog(ArrayList<com.mkir.datastreams.PatientLog> android) {
        this.android = android;
    }

    @Override
    public PatientLog.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_patientlog, viewGroup, false);
        return new PatientLog.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PatientLog.ViewHolder viewHolder, int i) {

        if (android.get(i).getAnamnezisID()!=null) {
            x=1;
            viewHolder.tv_id.setText(android.get(i).getAnamnezisID());
            viewHolder.tv_id.setVisibility(View.GONE);
            viewHolder.tv_diagnozis.setText("Anamnézis került felvételre");
            if (android.get(i).getEllatasVege()!=null) {
                viewHolder.tv_date.setText(android.get(i).getEllatasVege());
            } else {
                viewHolder.tv_date.setText("2017-01-01");
            }
        } else if (android.get(i).getDiagnozisok_id()!=null){
            viewHolder.tv_id.setText(android.get(i).getKOD10());
            viewHolder.tv_diagnozis.setText(android.get(i).getNEV());
            if (android.get(i).getEllatasVege()!=null) {
                viewHolder.tv_date.setText(android.get(i).getEllatasVege());
            } else {
                viewHolder.tv_date.setText("2017-01-01");
            }
        } else if (android.get(i).getLeletID()!=null){
            x=2;
            viewHolder.tv_id.setText(android.get(i).getLeletID());
            viewHolder.tv_id.setVisibility(View.GONE);
            viewHolder.tv_diagnozis.setText("Lelet került hozzáadásra");
            if (android.get(i).getEllatasVege()!=null) {
                viewHolder.tv_date.setText(android.get(i).getEllatasVege());
            } else {
                viewHolder.tv_date.setText("2017-01-01");
            }
        } else {
            viewHolder.tv_diagnozis.setText("Hellobello");
        }
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_diagnozis, tv_id, tv_date;
        private LinearLayout patientlog_click;
        public ViewHolder(View view) {
            super(view);

            tv_diagnozis = (TextView)view.findViewById(R.id.diagnozis);
            tv_id = (TextView) view.findViewById(R.id.id);
            tv_date = (TextView) view.findViewById(R.id.date);
            patientlog_click = (LinearLayout) view.findViewById(R.id.patientlog_click);
            patientlog_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(tv_id.getText().toString());
                }
            });

        }
    }

    private void showDialog (String patientlogid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = (LayoutInflater.from(view.getContext()));
        View dialogview = inflater.inflate(R.layout.dialog_patientlog, null);
        patientlog_text = (TextView) dialogview.findViewById(R.id.patientlog_text);
        patientlog_webview = (WebView) dialogview.findViewById(R.id.patientlog_webview);
        builder.setView(dialogview);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);
        ServerRequest request1 = new ServerRequest();
        Anamnezis anamnezis = new Anamnezis();

        if (x==1){
            builder.setTitle("Anamnézis");
            anamnezis.setAnamnezisID(patientlogid);
            request1.setOperation(Constants.ANAMNEZIS);
        } else if (x==2) {
            builder.setTitle("Lelet");
            anamnezis.setLeletID(patientlogid);
            request1.setOperation(Constants.LELET);
        }

        request1.setAnamnezis(anamnezis);
        Call<ServerResponse> response = loginInterface.operation(request1);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if (resp.getAnamnezis().getAnamnezisSzoveg() != null){
                    patientlog_text.setText(Html.fromHtml(resp.getAnamnezis().getAnamnezisSzoveg().toString(), Html.FROM_HTML_MODE_COMPACT));
                    patientlog_webview.setVisibility(View.GONE);
                } else if (resp.getAnamnezis().getLeletSzoveg()!=null){
                    patientlog_webview.loadDataWithBaseURL(null, resp.getAnamnezis().getLeletSzoveg().toString(), "text/html", "utf-8", null);
                    //patientlog_text.setText(Html.fromHtml(resp.getAnamnezis().getLeletSzoveg().toString(), Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH));
                    patientlog_text.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                //Snackbar.make(((MainActivity)view.getContext()), t.getLocalizedMessage(), LENGTH_LONG).show();
            }
        });


        builder.setPositiveButton("Szerkesztés", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
