package com.mkir;

import com.mkir.datastreams.Anamnezis;
import com.mkir.datastreams.Doctors;
import com.mkir.datastreams.Elojegyzes;
import com.mkir.datastreams.PatientList;
import com.mkir.datastreams.PatientLog;
import com.mkir.datastreams.TajType;
import com.mkir.datastreams.UpComingList;
import com.mkir.datastreams.User;
import com.mkir.fragments.DoctorsList;

/**
 * Created by nyulg on 2017. 06. 20..
 */

public class ServerResponse {
    private String result;
    private String message;
    private User user;
    private PatientList patient1;
    private Anamnezis anamnezis;
    private Elojegyzes elojegy;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public PatientList getPatient(){return patient1;}

    public Anamnezis getAnamnezis(){return anamnezis;}

    private PatientList [] patient;

    public PatientList [] getPatientList(){
        return patient;
    }

    private UpComingList [] upComingList;

    public UpComingList [] getUpComingList(){return upComingList;}

    private PatientLog [] patientLog;

    public PatientLog [] getPatientLog(){return patientLog;}

    public Elojegyzes getElojegy() {
        return elojegy;
    }

    private Doctors[] doctorList;

    public Doctors[] getDoctorList() {
        return doctorList;
    }

    private TajType[] taj_tipus;

    public TajType[] getTajType(){
        return taj_tipus;
    }
}
