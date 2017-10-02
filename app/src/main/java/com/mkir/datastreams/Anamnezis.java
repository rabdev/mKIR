package com.mkir.datastreams;

/**
 * Created by nyulg on 2017. 09. 21..
 */

public class Anamnezis {
    private String anamnezisSzoveg, anamnezisID, leletID, leletSzoveg;

    public String getAnamnezisID() {
        return anamnezisID;
    }

    public void setAnamnezisID(String anamnezisID) {
        this.anamnezisID = anamnezisID;
    }

    public String getAnamnezisSzoveg() {
        return anamnezisSzoveg;
    }

    public void setAnamnezisSzoveg(String anamnezisSzoveg) {
        this.anamnezisSzoveg = anamnezisSzoveg;
    }

    public String getLeletSzoveg() {
        return leletSzoveg;
    }

    public void setLeletSzoveg(String leletSzoveg) {
        this.leletSzoveg = leletSzoveg;
    }

    public String getLeletID() {
        return leletID;
    }

    public void setLeletID(String leletID) {
        this.leletID = leletID;
    }
}
