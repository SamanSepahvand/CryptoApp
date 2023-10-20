package com.samansepahvand.cryptoapp.metamodel.retrofit.single;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Status;

import java.io.Serializable;
import java.util.List;

public class CryptoSingle implements Serializable
{

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private DatumSingle data = null;
    private final static long serialVersionUID = -4369048252305703014L;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public DatumSingle getData() {
        return data;
    }

    public void setData(DatumSingle data) {
        this.data = data;
    }
}