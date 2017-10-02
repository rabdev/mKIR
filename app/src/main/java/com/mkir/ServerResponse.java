package com.mkir;

import com.mkir.adapters.UpComing;
import com.mkir.datastreams.Anamnezis;
import com.mkir.datastreams.PatientList;
import com.mkir.datastreams.PatientLog;
import com.mkir.datastreams.UpComingList;

/**
 * Created by nyulg on 2017. 06. 20..
 */

public class ServerResponse {
    private String result;
    private String message;
    private User user;
    private PatientList patient1;
    private Anamnezis anamnezis;

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

}
