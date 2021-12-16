package com.example.air_monitoring_bk.Info;

public class InfoItem {
    private Integer AQI_image;
    private Integer AQI_face;
    private String AQI_index;
    private String AQI_affect;
    private String AQI_recommendations;

    public InfoItem(Integer AQI_image, Integer AQI_face, String AQI_index, String AQI_affect, String AQI_recommendations) {
        this.AQI_image = AQI_image;
        this.AQI_face = AQI_face;
        this.AQI_index = AQI_index;
        this.AQI_affect = AQI_affect;
        this.AQI_recommendations = AQI_recommendations;
    }

    public Integer getAQI_image() {
        return AQI_image;
    }

    public Integer getAQI_face() {
        return AQI_face;
    }

    public void setAQI_image(Integer AQI_image) {
        this.AQI_image = AQI_image;
    }

    public void setAQI_face(Integer AQI_face) {
        this.AQI_face = AQI_face;
    }

    public String getAQI_index() {
        return AQI_index;
    }

    public void setAQI_index(String AQI_index) {
        this.AQI_index = AQI_index;
    }

    public String getAQI_affect() {
        return AQI_affect;
    }

    public void setAQI_affect(String AQI_affect) {
        this.AQI_affect = AQI_affect;
    }

    public String getAQI_recommendations() {
        return AQI_recommendations;
    }

    public void setAQI_recommendations(String AQI_recommendations) {
        this.AQI_recommendations = AQI_recommendations;
    }
}
