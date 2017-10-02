package com.mkir.datastreams;

/**
 * Created by nyulg on 2017. 07. 29..
 */

public class PatientList {
    private String szemely_nev, taj_azonosito, taj_tipus_id, megszolitas, neme_id, szuletesi_ido, szuletesi_hely_orszag, szul_varos, megjegyzes;
    private String szuletesi_nev, anyja_neve, biztositasi_orszag, csaladi_allapot, allampolgarsag_orszag, utlevel_szam, lakcim_orszag, iranyitoszam, varos_nev, utca_hazszam, szlacim, telefon, email, ertesitesiForma;
    private String unique_id;
    private String szemely_id;

    public PatientList() {
    }

    public String getSzemely_nev(){return szemely_nev;}

    public String getSzemely_id(){return szemely_id;}

    public String getUnique_id(){return unique_id;}

    public String getTaj(){return taj_azonosito;}

    public String getNeme(){return neme_id;}

    public void setSzemely_nev(String szemely_nev){this.szemely_nev=szemely_nev;}

    public void setUnique_id(String unique_id){this.unique_id=unique_id;}

    public void setSzemely_id (String szemely_id){this.szemely_id=szemely_id;}

    public void setTaj(String taj_azonosito) {this.taj_azonosito = taj_azonosito;}

    public void setNeme(String neme_id) {this.neme_id = neme_id;}

    public String getSzuletesi_ido() {return szuletesi_ido;}

    public void setSzuletesi_ido(String szuletesi_ido) {this.szuletesi_ido = szuletesi_ido;}

    public String getSzuletesi_hely_orszag() {return szuletesi_hely_orszag;}

    public void setSzuletesi_hely_orszag(String szuletesi_hely_orszag) {this.szuletesi_hely_orszag = szuletesi_hely_orszag;}

    public String getMegjegyzes() {return megjegyzes;}

    public void setMegjegyzes(String megjegyzes) {this.megjegyzes = megjegyzes;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getTelefon() {return telefon;}

    public void setTelefon(String telefon) {this.telefon = telefon;}

    public String getUtca_hazszam() {return utca_hazszam;}

    public void setUtca_hazszam(String utca_hazszam) {this.utca_hazszam = utca_hazszam;}

    public String getVaros_nev() {return varos_nev;}

    public void setVaros_nev(String varos_nev) {this.varos_nev = varos_nev;}

    public String getIranyitoszam() {return iranyitoszam;}

    public void setIranyitoszam(String iranyitoszam) {this.iranyitoszam = iranyitoszam;}

    public String getLakcim_orszag() {return lakcim_orszag;}

    public void setLakcim_orszag(String lakcim_orszag) {this.lakcim_orszag = lakcim_orszag;}

    public String getAnyja_neve() {return anyja_neve;}

    public String getSzuletesi_nev() {return szuletesi_nev;}

    public String getTaj_tipus_id() {
        return taj_tipus_id;
    }

    public void setTaj_tipus_id(String taj_tipus_id) {
        this.taj_tipus_id = taj_tipus_id;
    }

    public String getMegszolitas() {
        return megszolitas;
    }

    public void setMegszolitas(String megszolitas) {
        this.megszolitas = megszolitas;
    }

    public String getSzul_varos() {
        return szul_varos;
    }

    public void setSzul_varos(String szul_varos) {
        this.szul_varos = szul_varos;
    }

    public String getBiztositasi_orszag() {
        return biztositasi_orszag;
    }

    public void setBiztositasi_orszag(String biztositasi_orszag) {
        this.biztositasi_orszag = biztositasi_orszag;
    }

    public String getCsaladi_allapot() {
        return csaladi_allapot;
    }

    public void setCsaladi_allapot(String csaladi_allapot) {
        this.csaladi_allapot = csaladi_allapot;
    }

    public String getAllampolgarsag_orszag() {
        return allampolgarsag_orszag;
    }

    public void setAllampolgarsag_orszag(String allampolgarsag_orszag) {
        this.allampolgarsag_orszag = allampolgarsag_orszag;
    }

    public String getUtlevel_szam() {
        return utlevel_szam;
    }

    public void setUtlevel_szam(String utlevel_szam) {
        this.utlevel_szam = utlevel_szam;
    }

    public String getSzlacim() {
        return szlacim;
    }

    public void setSzlacim(String szlacim) {
        this.szlacim = szlacim;
    }

    public String getErtesitesiForma() {
        return ertesitesiForma;
    }

    public void setErtesitesiForma(String ertesitesiForma) {
        this.ertesitesiForma = ertesitesiForma;
    }

    public void setSzuletesi_nev(String szuletesi_nev) {
        this.szuletesi_nev = szuletesi_nev;
    }

    public void setAnyja_neve(String anyja_neve) {
        this.anyja_neve = anyja_neve;
    }
}
