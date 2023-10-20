package com.samansepahvand.cryptoapp.metamodel.retrofit.single;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuoteSingle implements Serializable
{


    @SerializedName("price")
    @Expose
    private Double price;


    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}