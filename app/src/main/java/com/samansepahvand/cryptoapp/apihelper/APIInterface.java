package com.samansepahvand.cryptoapp.apihelper;


import com.google.gson.JsonObject;
import com.samansepahvand.cryptoapp.metamodel.retrofit.CryptoList;
import com.samansepahvand.cryptoapp.metamodel.retrofit.single.CryptoSingle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {

    //https://pro.coinmarketcap.com/api/v1#section/Quick-Start-Guide
    @Headers("X-CMC_PRO_API_KEY: 1efe5e27-9e4d-4e97-a9eb-f5a66e5f9016")
    @GET("/v1/cryptocurrency/listings/latest?")
    Call<CryptoList> LimitAssets(@Query("limit") String page);


    @Headers("X-CMC_PRO_API_KEY: 1efe5e27-9e4d-4e97-a9eb-f5a66e5f9016")
    @GET("/v1/cryptocurrency/listings/latest")
    Call<CryptoList> Latest();


    @Headers("X-CMC_PRO_API_KEY: 1efe5e27-9e4d-4e97-a9eb-f5a66e5f9016")
    @GET("/v1/cryptocurrency/trending/most-visited")
    Call<CryptoList> MostVisited();


    @Headers("X-CMC_PRO_API_KEY: 1efe5e27-9e4d-4e97-a9eb-f5a66e5f9016")
    @GET("/v1/cryptocurrency/trending/gainers-losers")
    Call<CryptoList> GainersLosers();


    @Headers("X-CMC_PRO_API_KEY: 1efe5e27-9e4d-4e97-a9eb-f5a66e5f9016")
    @GET("/v1/cryptocurrency/quotes/latest?id=1,1027,825")
    Call<CryptoList> Cryptocurrencies();

    @Headers("X-CMC_PRO_API_KEY: 1efe5e27-9e4d-4e97-a9eb-f5a66e5f9016")
    @GET("/v2/tools/price-conversion?")
    Call<CryptoSingle> PriceConversion(@Query("amount") String amount, @Query("id") String id, @Query("convert_id") String id_convert);

}
