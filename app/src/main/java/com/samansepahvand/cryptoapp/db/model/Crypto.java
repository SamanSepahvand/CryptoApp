package com.samansepahvand.cryptoapp.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Table(name = Crypto.TABLE_NAME)
public class Crypto extends Model {


    public static final String TABLE_NAME = "Crypto";

    public static final String Ids = "ids";
    public static final String Name = "name";
    public static final String Symbol = "Symbol";


    @Column(name = Ids)
    @SerializedName("id")
    @Expose
    private Integer ids;

    @Column(name = Name)
    @SerializedName("name")
    @Expose
    private String name;

    @Column(name = Symbol)
    @SerializedName("symbol")
    @Expose
    private String symbol;

    private  int position;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
