package com.mkir.datastreams;

/**
 * Created by nyulg on 2017. 06. 20..
 */

public class User {

    private String userName1;
    private String username;
    private String kivalasztott_osztaly;
    private String unique_id;
    private String password;
    private String old_password;
    private String new_password;

    public String getName() {
        return userName1;
    }


    public String getUsername() {
        return username;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setName(String userName1) {
        this.userName1 = userName1;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getKivalasztott_osztaly() {
        return kivalasztott_osztaly;
    }

    public void setKivalasztott_osztaly(String kivalasztott_osztaly) {
        this.kivalasztott_osztaly = kivalasztott_osztaly;
    }
}
