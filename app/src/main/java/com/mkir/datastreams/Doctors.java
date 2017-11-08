package com.mkir.datastreams;

/**
 * Created by nyulg on 2017. 10. 11..
 */

public class Doctors {
    private String user_id, userName, osztaly_id, osztaly_nev;

    public String getOsztaly_nev() {
        return osztaly_nev;
    }

    public void setOsztaly_nev(String osztaly_nev) {
        this.osztaly_nev = osztaly_nev;
    }

    public String getOsztaly_id() {
        return osztaly_id;
    }

    public void setOsztaly_id(String osztaly_id) {
        this.osztaly_id = osztaly_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
