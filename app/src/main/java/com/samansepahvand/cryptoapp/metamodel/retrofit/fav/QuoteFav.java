package com.samansepahvand.cryptoapp.metamodel.retrofit.fav;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuoteFav implements Serializable
{

    @SerializedName("USD")
    @Expose
    private USDFav uSD;
    private final static long serialVersionUID = -5780538494495942860L;

    public USDFav getUSD() {
        return uSD;
    }

    public void setUSD(USDFav uSD) {
        this.uSD = uSD;
    }

}