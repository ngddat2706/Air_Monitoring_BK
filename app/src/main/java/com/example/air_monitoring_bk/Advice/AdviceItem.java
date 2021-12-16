package com.example.air_monitoring_bk.Advice;

public class AdviceItem {
    private Integer Advice_icon;
    private String Advice_tv;

    public AdviceItem(Integer advice_icon, String advice_tv) {
        Advice_icon = advice_icon;
        Advice_tv = advice_tv;
    }

    public Integer getAdvice_icon() {
        return Advice_icon;
    }

    public void setAdvice_icon(Integer advice_icon) {
        Advice_icon = advice_icon;
    }

    public String getAdvice_tv() {
        return Advice_tv;
    }

    public void setAdvice_tv(String advice_tv) {
        Advice_tv = advice_tv;
    }
}
