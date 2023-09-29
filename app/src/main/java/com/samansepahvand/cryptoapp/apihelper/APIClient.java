
package com.samansepahvand.cryptoapp.apihelper;

import java.security.PublicKey;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

  public     static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pro-api.coinmarketcap.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
