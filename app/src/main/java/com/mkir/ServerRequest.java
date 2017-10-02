package com.mkir;

import com.mkir.datastreams.Anamnezis;
import com.mkir.datastreams.PatientList;
import com.mkir.datastreams.PatientLog;
import com.mkir.datastreams.UpComingList;

/**
 * Created by nyulg on 2017. 06. 20..
 */

public class ServerRequest {
    private String operation;
    private User user;
    private PatientList patient;
    private UpComingList upComingList;
    private Anamnezis anamnezis;
    private PatientLog patientLog;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPatientList (PatientList patient) {
        this.patient = patient;
    }

    public void setUpComingList (UpComingList upComingList) {this.upComingList = upComingList;}

    public void setAnamnezis (Anamnezis anamnezis) {this.anamnezis=anamnezis;}

    public void setPatientLog (PatientLog patientLog) {this.patientLog=patientLog;}

}
