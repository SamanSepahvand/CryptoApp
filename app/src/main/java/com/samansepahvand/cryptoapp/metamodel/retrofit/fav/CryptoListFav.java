package com.samansepahvand.cryptoapp.metamodel.retrofit.fav;


import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CryptoListFav implements Serializable
{

    @SerializedName("status")
    @Expose
    private StatusFav status;
    @SerializedName("data")
    @Expose
    private JsonObject data = null;


    public StatusFav getStatus() {
        return status;
    }

    public void setStatus(StatusFav status) {
        this.status = status;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

}