package com.example.air_monitoring_bk.Station;

import java.util.HashMap;
import java.util.Map;

public class StationItem {
    private int id;
    private String name;
    private boolean checkbox;

    public StationItem(){

    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public StationItem(int id, String name, boolean checkbox) {
        this.id = id;
        this.name = name;
        this.checkbox = checkbox;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("checkbox", checkbox);

        return result;
    }

    @Override
    public String toString() {
        return "StationItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checkbox=" + checkbox +
                '}';
    }
}
