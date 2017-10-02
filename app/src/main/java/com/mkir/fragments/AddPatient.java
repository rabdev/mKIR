package com.mkir.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mkir.Constants;
import com.mkir.MainActivity;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.datastreams.PatientList;
import com.mkir.interfaces.LoginInterface;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPatient extends Fragment {

    SharedPreferences pref;
    BottomNavigationItemView addpatient_pipa, addpatient_frissit, addpatient_megse;
    String unique_id;
    Spinner addbirthy, addbirthm, addbirthd, taj_tipus, nem, csaladi_allapot;
    EditText szemely_nev, taj, megszolitas, anyja_neve,szul_orszag, szul_varos, szul_nev, biztositasi_orszag;
    EditText  allampolgarsag, utlevelszam, email, telefonszam, lakcim_orszag, lakcim_varos, lakcim_utca, iranyitoszam, szlacim, megjegyzes;
    RadioGroup ertesitesiForma;
    boolean isEven;
    ArrayAdapter<Integer> adapter1;
    ArrayAdapter<CharSequence> adapter2,  adapter4, adapter5, adapter6;
    ArrayAdapter<String> adapter3;

    public AddPatient() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pref = getActivity().getPreferences(0);

        View addpatient = inflater.inflate(R.layout.fragment_add_patient, container, false);
        getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.GONE);

        szemely_nev = (EditText) addpatient.findViewById(R.id.add_szemely_nev);
        taj = (EditText) addpatient.findViewById(R.id.addtaj);
        megszolitas = (EditText) addpatient.findViewById(R.id.addmegszolitas);
        anyja_neve= (EditText) addpatient.findViewById(R.id.addanyjaneve);
        szul_orszag= (EditText) addpatient.findViewById(R.id.addszulorszag);
        szul_varos= (EditText) addpatient.findViewById(R.id.addszulvaros);
        szul_nev= (EditText) addpatient.findViewById(R.id.addszulnev);
        biztositasi_orszag= (EditText) addpatient.findViewById(R.id.addbiztország);
        allampolgarsag= (EditText) addpatient.findViewById(R.id.addallampolgarsag);
        utlevelszam= (EditText) addpatient.findViewById(R.id.addutlevel);
        email= (EditText) addpatient.findViewById(R.id.addemail);
        telefonszam = (EditText) addpatient.findViewById(R.id.addtelszam);
        lakcim_orszag= (EditText) addpatient.findViewById(R.id.addlakcimo);
        lakcim_varos= (EditText) addpatient.findViewById(R.id.addlakcimv);
        lakcim_utca= (EditText) addpatient.findViewById(R.id.addlakcimutca);
        iranyitoszam = (EditText) addpatient.findViewById(R.id.addlakcimisz);
        szlacim = (EditText) addpatient.findViewById(R.id.addszlacim);
        megjegyzes = (EditText) addpatient.findViewById(R.id.addmegjegyzes);

        ertesitesiForma = (RadioGroup) addpatient.findViewById(R.id.addertesitesforma);

        BottomNavigationView addpatient_navbar = (BottomNavigationView) addpatient.findViewById(R.id.addpatient_navbar);
        addpatient_pipa = (BottomNavigationItemView) addpatient_navbar.findViewById(R.id.action_pipa);
        addpatient_frissit = (BottomNavigationItemView) addpatient_navbar.findViewById(R.id.action_frissit);
        addpatient_megse = (BottomNavigationItemView) addpatient_navbar.findViewById(R.id.action_megse);


        addpatient_pipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(szemely_nev.getText().toString()).isEmpty() && !(taj.getText().toString()).isEmpty()){
                    unique_id = pref.getString(Constants.UNIQUE_ID,"");
                    sendJSON(unique_id);
                } else {
                    Toast.makeText(getContext(), "Nem adott meg személynevet és TAJ-t!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addpatient_frissit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Frissítés", Toast.LENGTH_SHORT).show();
            }
        });
        addpatient_megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                getActivity().findViewById(R.id.bottom_navbar).setVisibility(View.VISIBLE);
            }
        });

        addbirthy = (Spinner) addpatient.findViewById(R.id.addbirthy);

        ArrayList<Integer> years = new ArrayList<Integer>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1920; i <= thisYear; i++) {
            years.add(i);
        }
        adapter1 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_dropdown_item, years);
        adapter1.setNotifyOnChange(true);
        addbirthy.setAdapter(adapter1);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(addbirthy);

            // Set popupWindow height to 500px
            popupWindow.setHeight(900);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        addbirthm = (Spinner) addpatient.findViewById(R.id.addbirthm);
        adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.month_array, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setNotifyOnChange(true);
        addbirthm.setAdapter(adapter2);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(addbirthm);

            popupWindow.setHeight(900);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {

        }
        addbirthd = (Spinner) addpatient.findViewById(R.id.addbirthd);

        addbirthm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> days = new ArrayList<String>();
                int day;
                if (addbirthm.getSelectedItem().equals("január") || addbirthm.getSelectedItem().equals("március") || addbirthm.getSelectedItem().equals("május") || addbirthm.getSelectedItem().equals("július") || addbirthm.getSelectedItem().equals("augusztus") || addbirthm.getSelectedItem().equals("október") || addbirthm.getSelectedItem().equals("december")) {
                    day = 31;
                    for (int i = 1; i <= day; i++) {
                        days.add(Integer.toString(i));
                    }
                } else if (addbirthm.getSelectedItem().equals("február")) {
                    addbirthy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Integer year = (Integer) addbirthy.getSelectedItem();
                            if (year % 4 == 0) {
                                isEven=true;
                                adapter2.setNotifyOnChange(true);
                                adapter1.setNotifyOnChange(true);
                            } else {
                                isEven=false;
                                adapter2.setNotifyOnChange(true);
                                adapter1.setNotifyOnChange(true);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (isEven) {
                        day = 29;
                        for (int i = 1; i <= day; i++) {
                            days.add(Integer.toString(i));
                        }
                    } else {
                        day = 28;
                        for (int i = 1; i <= day; i++) {
                            days.add(Integer.toString(i));
                        }
                    }
                } else

                {
                    day = 30;
                    for (int i = 1; i <= day; i++) {
                        days.add(Integer.toString(i));
                    }
                }

                adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, days);
                adapter3.setNotifyOnChange(true);
                addbirthd.setAdapter(adapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        try

        {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(addbirthd);

            popupWindow.setHeight(900);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException |
                IllegalAccessException e)

        {

        }

        taj_tipus = (Spinner) addpatient.findViewById(R.id.addtajtipus);
        adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.taj_tipus_array, android.R.layout.simple_spinner_dropdown_item);
        taj_tipus.setAdapter(adapter4);


        nem = (Spinner) addpatient.findViewById(R.id.addnem);
        adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.nem_array, android.R.layout.simple_spinner_dropdown_item);
        nem.setAdapter(adapter5);

        csaladi_allapot = (Spinner) addpatient.findViewById(R.id.addcsaladi_allapot);
        adapter6 = ArrayAdapter.createFromResource(getActivity(), R.array.csalad_array, android.R.layout.simple_spinner_dropdown_item);
        csaladi_allapot.setAdapter(adapter6);

        return addpatient;


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

    private void sendJSON(String unique_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        PatientList patient = new PatientList();
        patient.setSzemely_nev(szemely_nev.getText().toString());
        patient.setTaj(taj.getText().toString());
        if (taj_tipus.getSelectedItem().toString() == "TAJ") {
            patient.setTaj_tipus_id("1");
        } else if (taj_tipus.getSelectedItem().toString() == "Személyazonosító jel nincs kitöltve") {
            patient.setTaj_tipus_id("3");
        } else if (taj_tipus.getSelectedItem().toString() == "6 hónapnál fiatalabb gyermek") {
            patient.setTaj_tipus_id("4");
        } else if (taj_tipus.getSelectedItem().toString() == "Útlevélszám") {
            patient.setTaj_tipus_id("5");
        } else if (taj_tipus.getSelectedItem().toString() == "Segítő jobb engedélyének száma") {
            patient.setTaj_tipus_id("6");
        } else if (taj_tipus.getSelectedItem().toString() == "Menedékes, kérelmező, befogadó igazolvány száma") {
            patient.setTaj_tipus_id("7");
        } else if (taj_tipus.getSelectedItem().toString() == "Ismeretlen TAJ számú elhunyt személy, ill. ismeretlen beteg") {
            patient.setTaj_tipus_id("8");
        } else if (taj_tipus.getSelectedItem().toString() == "Személyazonosító a menekült, menedékes és oltalmazott státusz kérelmezését megelőzően nyújtott ellátás során") {
            patient.setTaj_tipus_id("9");
        } else {
            patient.setTaj_tipus_id("");
        }

        if (megszolitas.getText().toString().isEmpty()) {
            patient.setMegszolitas("");
        } else {
            patient.setMegszolitas(megszolitas.getText().toString());
        }
        patient.setMegszolitas(megszolitas.getText().toString());
        if (nem.getSelectedItem().toString() == "nő") {
            patient.setNeme("2");
        } else if (nem.getSelectedItem().toString() == "férfi") {
            patient.setNeme("1");
        } else {
            patient.setNeme("");
        }
        patient.setSzuletesi_ido(addbirthy.getSelectedItem().toString() + "-" + addbirthm.getSelectedItem().toString() + "-" + addbirthd.getSelectedItem().toString());

        if (szul_orszag.getText().toString().isEmpty()) {
            patient.setSzuletesi_hely_orszag("");
        } else {
            patient.setSzuletesi_hely_orszag(szul_orszag.getText().toString());
        }
        if (szul_varos.getText().toString().isEmpty()){
            patient.setSzul_varos("");
        } else {
            patient.setSzul_varos(szul_varos.getText().toString());
        }
        if (szul_nev.getText().toString().isEmpty()){
            patient.setSzuletesi_nev("");
        } else {
            patient.setSzuletesi_nev(szul_nev.getText().toString());
        }
        if(anyja_neve.getText().toString().isEmpty()){
            patient.setAnyja_neve("");
        } else {
            patient.setAnyja_neve(anyja_neve.getText().toString());
        }
        if (biztositasi_orszag.getText().toString().isEmpty()){
            patient.setBiztositasi_orszag("");
        } else {
            patient.setBiztositasi_orszag(biztositasi_orszag.getText().toString());
        }
        if (csaladi_allapot.getSelectedItem().toString().isEmpty()){
            patient.setCsaladi_allapot("");
        } else {
            patient.setCsaladi_allapot(csaladi_allapot.getSelectedItem().toString());
        }
        if (allampolgarsag.getText().toString().isEmpty()){
            patient.setAllampolgarsag_orszag("");
        } else {
            patient.setAllampolgarsag_orszag(allampolgarsag.getText().toString());
        }
        if (utlevelszam.getText().toString().isEmpty()){
            patient.setUtlevel_szam("");
        } else {
            patient.setUtlevel_szam(utlevelszam.getText().toString());
        }
        if (lakcim_orszag.getText().toString().isEmpty()){
            patient.setLakcim_orszag("");
        } else {
            patient.setLakcim_orszag(lakcim_orszag.getText().toString());
        }
        if (iranyitoszam.getText().toString().isEmpty()){
            patient.setIranyitoszam("");
        } else {
            patient.setIranyitoszam(iranyitoszam.getText().toString());
        }
        if (lakcim_varos.getText().toString().isEmpty()){
            patient.setVaros_nev("");
        } else {
            patient.setVaros_nev(lakcim_varos.getText().toString());
        }
        if (lakcim_utca.getText().toString().isEmpty()){
            patient.setUtca_hazszam("");
        } else {
            patient.setUtca_hazszam(lakcim_utca.getText().toString());
        }
        if (szlacim.getText().toString().isEmpty()){
            patient.setSzlacim("");
        } else {
            patient.setSzlacim(szlacim.getText().toString());
        }
        if(telefonszam.getText().toString().isEmpty()){
            patient.setTelefon("");
        } else {
            patient.setTelefon("+36"+telefonszam.getText().toString());
        }
        if(email.getText().toString().isEmpty()){
            patient.setEmail("");
        } else{
            patient.setEmail(email.getText().toString());
        }
        if (megjegyzes.getText().toString().isEmpty()){
            patient.setMegjegyzes("");
        } else {
            patient.setMegjegyzes(megjegyzes.getText().toString());
        }
        patient.setErtesitesiForma("e-mail");
        patient.setUnique_id(unique_id);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ADD_PATIENT);
        request.setPatientList(patient);
        Log.d(patient.toString(),patient.toString());
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
}
