package com.samansepahvand.cryptoapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.samansepahvand.cryptoapp.R;
import com.samansepahvand.cryptoapp.adapter.CryptoFavListAdapter;
import com.samansepahvand.cryptoapp.apihelper.APIClient;
import com.samansepahvand.cryptoapp.apihelper.APIInterface;
import com.samansepahvand.cryptoapp.metamodel.retrofit.CryptoList;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Status;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //CryptoListAdapter adapter;
    private APIInterface apiInterface;
    private RecyclerView recyclerView;
    private List<Datum> cryptoList = null;
    public static final String TAG = "MainActivity";

    private CryptoFavListAdapter adapterFav;

    private ImageView imgNotif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        initRecyclerView();
        getCoinList();


    }


    private void initRecyclerView() {
        // Lookup the recyclerview in activity layout
        recyclerView = findViewById(R.id.my_recycler_view);
        imgNotif = findViewById(R.id.img_notif);
        // Initialize data
        cryptoList = new ArrayList<>();

        // Create adapter passing in the sample user data
        adapterFav = new CryptoFavListAdapter(MainActivity.this, cryptoList);


        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapterFav);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterFav.setClickListener(new CryptoFavListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, CoinPage.class);
                intent.putExtra("coin", adapterFav.getItem(position));
                startActivity(intent);
            }
        });
    }


    private void getCoinList() {

        try {


            Call<CryptoList> call2 = apiInterface.doGetUserList("100");

            call2.enqueue(new Callback<CryptoList>() {
                @Override
                public void onResponse(Call<CryptoList> call, Response<CryptoList> response) {
                    CryptoList list = response.body();

                    // do not reinitialize an existing reference used by an adapter
                    // add to the existing list

                    Log.e("TAGSamansss", "onResponse: " + response.errorBody());
                    if (list.getData().size() > 0) {
                        cryptoList.clear();
                        cryptoList.addAll(list.getData());

                        adapterFav.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CryptoList> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    Log.d("XXXX", t.getLocalizedMessage());
                    call.cancel();
                }
            });

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "getCoinList: " + e.getMessage());
        }
    }

//    private void initRecyclerViewOld() {
//        // Lookup the recyclerview in activity layout
//        recyclerView = findViewById(R.id.my_recycler_view);
//
//        // Initialize data
//        cryptoList = new ArrayList<>();
//
//        // Create adapter passing in the sample user data
//        adapter = new CryptoListAdapter(cryptoList);
//
//
//        // Attach the adapter to the recyclerview to populate items
//        recyclerView.setAdapter(adapter);
//
//        // Set layout manager to position the items
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter.setClickListener(new CryptoListAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                //Toast.makeText(MainActivity.this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, CoinPage.class);
//                intent.putExtra("coin", adapter.getItem(position));
//                startActivity(intent);
//            }
//        });
//    }

//
//    private void getCoinListOld() {
//
//        try{
//
//
//
//            Call<CryptoList> call2 = apiInterface.doGetUserList("100");
//
//            call2.enqueue(new Callback<CryptoList>() {
//                @Override
//                public void onResponse(Call<CryptoList> call, Response<CryptoList> response) {
//                    CryptoList list = response.body();
//
//                    // do not reinitialize an existing reference used by an adapter
//                    // add to the existing list
//
//                    Log.e("TAGSamansss", "onResponse: " + response.errorBody());
//                    if (list.getData().size() > 0) {
//                        cryptoList.clear();
//                        cryptoList.addAll(list.getData());
//
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<CryptoList> call, Throwable t) {
//                    Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
//                    Log.d("XXXX", t.getLocalizedMessage());
//                    call.cancel();
//                }
//            });
//
//        }catch (Exception e){
//            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "getCoinList: "+e.getMessage() );
//        }
//    }


}
