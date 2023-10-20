package com.samansepahvand.cryptoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samansepahvand.cryptoapp.R;
import com.samansepahvand.cryptoapp.adapter.CryptoFavListAdapter;
import com.samansepahvand.cryptoapp.adapter.CryptoListAdapter;
import com.samansepahvand.cryptoapp.adapter.FilterCryptoListAdapter;
import com.samansepahvand.cryptoapp.adapter.IntervalChartTradingViewAdapter;
import com.samansepahvand.cryptoapp.apihelper.APIClient;
import com.samansepahvand.cryptoapp.apihelper.APIInterface;
import com.samansepahvand.cryptoapp.bussiness.OperationResult;
import com.samansepahvand.cryptoapp.db.repository.CryptoFavRepository;
import com.samansepahvand.cryptoapp.db.repository.CryptoRepository;
import com.samansepahvand.cryptoapp.metamodel.IntervalChartTradingViewData;
import com.samansepahvand.cryptoapp.metamodel.retrofit.CryptoList;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCryptoActivity extends AppCompatActivity  implements FilterCryptoListAdapter.ItemClickListenerCryptoList, SearchView.OnQueryTextListener {
    private SearchView searchView;
    //CryptoListAdapter adapter;
    private APIInterface apiInterface;
    private RecyclerView recyclerView;
    private List<Datum> cryptoList = null;
    public static final String TAG = "MainActivity";
    private CryptoListAdapter adapterFav;
    private ImageView imgNotif;


    private List<Datum> listFilterData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_crypto);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        initView();


        initRecyclerView();

        initRecyclerFilter();
        getCoinList("LimitAssets");

    }

    private  void initView(){
        searchView = findViewById(R.id.txt_header_result);
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        textView.setTextColor(this.getResources().getColor(R.color.gray_light));
        textView.setTextSize(16);

        searchView.setOnQueryTextListener(this);
        //      textView.setTypeface(Constants.CustomStyleElement());


    }
    private void initRecyclerView() {
        // Lookup the recyclerview in activity layout
        recyclerView = findViewById(R.id.my_recycler_view);
        imgNotif = findViewById(R.id.img_notif);
        // Initialize data
        cryptoList = new ArrayList<>();

        // Create adapter passing in the sample user data
        adapterFav = new CryptoListAdapter(cryptoList,ListCryptoActivity.this);


        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapterFav);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterFav.setClickListener(new CryptoListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ListCryptoActivity.this, CoinPage.class);
                intent.putExtra("coin", adapterFav.getItem(position));
                startActivity(intent);
            }
        });
    }
    private void getCoinList(String apiName) {

        try {
            Call<CryptoList> call2 =null;

            switch (apiName){
                case "LimitAssets":
                    call2 = apiInterface.LimitAssets("100");
                    break;
                case "Latest":
                    call2 = apiInterface.Latest();
                    break;
                case "MostVisited":
                    call2 = apiInterface.MostVisited();
                    break;

                case "GainersLosers":
                    call2 = apiInterface.GainersLosers();
                 break;

                default:
                    call2 = apiInterface.LimitAssets("10");
                    break;
            }


            call2.enqueue(new Callback<CryptoList>() {
                @Override
                public void onResponse(Call<CryptoList> call, Response<CryptoList> response) {
                    CryptoList list = response.body();

                    // do not reinitialize an existing reference used by an adapter
                    // add to the existing list

                    Log.e("TAGSamansss", "onResponse: " + response.errorBody());



                   if(list!=null) {
                       if (list.getData().size() > 0) {
                           cryptoList.clear();
                           cryptoList.addAll(list.getData());
                           OperationResult<Boolean> result= CryptoRepository.getInstance().SaveCrypto(list.getData());
                           adapterFav.notifyDataSetChanged();

                       } else {
                           Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(getBaseContext(), "Nothing to Show !", Toast.LENGTH_SHORT).show();
                   }
                }

                @Override
                public void onFailure(Call<CryptoList> call, Throwable t) {
                    Toast.makeText(ListCryptoActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    Log.d("XXXX", t.getLocalizedMessage());
                    call.cancel();
                }
            });

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "getCoinList: " + e.getMessage());
        }
    }

    private  void  initRecyclerFilter(){
        RecyclerView RecyclerFilterCryptoList;
        List<IntervalChartTradingViewData> dummyList = new ArrayList<>();
        FilterCryptoListAdapter recyclerAdapter;


        recyclerAdapter = new FilterCryptoListAdapter(ListCryptoActivity.this, dummyList);
        RecyclerFilterCryptoList = (RecyclerView) findViewById(R.id.recyclerIntervalChartTradingView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCryptoActivity.this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerFilterCryptoList.setLayoutManager(linearLayoutManager);

        RecyclerFilterCryptoList.setAdapter(recyclerAdapter);

        IntervalChartTradingViewData pet = new IntervalChartTradingViewData(1, "Limit Assets", "LimitAssets");
        dummyList.add(pet);

        pet = new IntervalChartTradingViewData(2, "Latest", "Latest");
        dummyList.add(pet);

        pet = new IntervalChartTradingViewData(3, "Most Visited", "MostVisited");
        dummyList.add(pet);

        pet = new IntervalChartTradingViewData(4, "Gainers & Losers", "GainersLosers");
        dummyList.add(pet);

//
//        pet = new IntervalChartTradingViewData(4, "Gainers & Losers", "GainersLosers");
//        dummyList.add(pet);


        recyclerAdapter.notifyDataSetChanged();

       // initChartDetailView(datum.getSymbol(),dummyList.get(0).getKeyValue());

    }


    @Override
    public void onItemClickCryptoList(String apiName) {

        if (apiName!=null)getCoinList(apiName);
        else
        Toast.makeText(ListCryptoActivity.this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        int countShow = 0;

        String userInput = newText.toLowerCase();
        List<Datum> newList = new ArrayList<>();

        for (Datum crypto : cryptoList) {
            if (crypto.getName().toLowerCase().contains(userInput) ||
                    crypto.getSymbol().toLowerCase().contains(userInput)) {
                newList.add(crypto);
            } else {

            }

            adapterFav.UpdateList(newList);

        }
        return true;
    }
}