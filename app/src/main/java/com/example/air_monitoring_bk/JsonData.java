package com.example.air_monitoring_bk;

public class JsonData {
    public String place;
    public Integer AQI;
    public Integer PM25;
    public Integer PM10;
    public Integer CO2;
    public Integer SO2;
    public Integer NO2;

    public JsonData(){

    }

    public JsonData(String place, Integer AQI, Integer PM25, Integer PM10, Integer CO2, Integer SO2, Integer NO2) {
        this.place = place;
        this.AQI = AQI;
        this.PM25 = PM25;
        this.PM10 = PM10;
        this.CO2 = CO2;
        this.SO2 = SO2;
        this.NO2 = NO2;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getAQI() {
        return AQI;
    }

    public void setAQI(Integer AQI) {
        this.AQI = AQI;
    }

    public Integer getPM25() {
        return PM25;
    }

    public void setPM25(Integer PM25) {
        this.PM25 = PM25;
    }

    public Integer getPM10() {
        return PM10;
    }

    public void setPM10(Integer PM10) {
        this.PM10 = PM10;
    }

    public Integer getCO2() {
        return CO2;
    }

    public void setCO2(Integer CO2) {
        this.CO2 = CO2;
    }

    public Integer getSO2() {
        return SO2;
    }

    public void setSO2(Integer SO2) {
        this.SO2 = SO2;
    }

    public Integer getNO2() {
        return NO2;
    }

    public void setNO2(Integer NO2) {
        this.NO2 = NO2;
    }

    @Override
    public String toString() {
        return "{" +
                "\"place\": " + "\""+place + "\", " +
                "\"AQI\": " + AQI +
                ", \"PM2.5\": " + PM25 +
                ", \"PM1.0\": " + PM10 +
                ", \"CO2\": " + CO2 +
                ", \"SO2\": " + SO2 +
                ", \"NO2\": " + NO2 +
                "}";
    }
}
