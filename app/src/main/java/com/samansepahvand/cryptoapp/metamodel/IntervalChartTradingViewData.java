package com.samansepahvand.cryptoapp.metamodel;

public class IntervalChartTradingViewData {

    private int id;
    private String Name;
    private String KeyValue;

    public IntervalChartTradingViewData(int id, String name, String keyValue) {
        this.id = id;
        Name = name;
        KeyValue = keyValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getKeyValue() {
        return KeyValue;
    }

    public void setKeyValue(String keyValue) {
        KeyValue = keyValue;
    }
}
