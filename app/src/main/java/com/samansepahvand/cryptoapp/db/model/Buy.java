package com.samansepahvand.cryptoapp.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Table(name = Buy.TABLE_NAME)
public class Buy extends Model {


    public static final String TABLE_NAME = "Buy";

    public static final String UserId = "userId";
    public static final String IdCrypto = "idCrypto";
    public static final String Symbol = "Symbol";

    public static final String AmountUSDT = "AmountUSDT";
    public static final String CurrentPrice = "CurrentPrice";

    public static final String BuyType = "BuyType";

    public static final String CreationDate = "CreationDate";

    @Column(name = UserId)
    private Integer userId;

    @Column(name = IdCrypto)
    private Integer idCrypto;

    @Column(name = Symbol)
    private String symbol;

    @Column(name = AmountUSDT)
    private Double amountUSDT;


    @Column(name = CurrentPrice)
    private Double currentPrice;


    @Column(name = BuyType)
    private Integer buyType;  //1 long //-1 short


    @Column(name = CreationDate)
    private Date creationDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(Integer idCrypto) {
        this.idCrypto = idCrypto;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getAmountUSDT() {
        return amountUSDT;
    }

    public void setAmountUSDT(Double amountUSDT) {
        this.amountUSDT = amountUSDT;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}