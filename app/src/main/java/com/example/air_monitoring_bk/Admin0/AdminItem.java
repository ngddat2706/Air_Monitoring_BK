package com.example.air_monitoring_bk.Admin0;

import android.provider.ContactsContract;

import java.util.HashMap;
import java.util.Map;

public class AdminItem {
    private Integer id;
    private String email;
    private String password;

    public AdminItem(){

    }

    public AdminItem(Integer id,String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("password", password);

        return result;
    }
}
