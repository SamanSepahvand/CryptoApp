package com.samansepahvand.cryptoapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.samansepahvand.cryptoapp.R;
import com.samansepahvand.cryptoapp.adapter.CryptoFavListAdapter;
import com.samansepahvand.cryptoapp.adapter.IntervalChartTradingViewAdapter;
import com.samansepahvand.cryptoapp.bussiness.OperationResult;
import com.samansepahvand.cryptoapp.db.repository.CryptoFavRepository;
import com.samansepahvand.cryptoapp.metamodel.IntervalChartTradingViewData;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CoinPage extends AppCompatActivity implements IntervalChartTradingViewAdapter.ItemClickListenerIntervalChartTradingView {



    private  TextView name ;
    private  TextView price ;
    private  TextView change1h ;
    private  TextView txtMiddleHeader;
    private  TextView txtMiddleProfit ;
    private  ImageView imgTrendingUp;
    private  ImageView imgMore;
    private  ImageView imgBack ;
    private  ImageView imgLogo;
    private  ConstraintLayout clMiddleRight;

    private WebView webView;


    private DisplayImageOptions options;
    private ImageLoader imageLoader;


    private  Intent intent;
    private    Datum datum;

    private static SharedPreferences sharedPreferences ;
    private static  SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_coin_page);
        setContentView(R.layout.activity_coin_page_new);

       // initOldData();

        initNewData();

    }


    private  void initNewData(){
         sharedPreferences = getSharedPreferences("Crypto", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
         intent = getIntent();
         datum = (Datum) intent.getSerializableExtra("coin");

         name = findViewById(R.id.name);
         price = findViewById(R.id.price);

//        TextView symbol = findViewById(R.id.symbol);
//        TextView slug = findViewById(R.id.slug);
//        TextView volume24h = findViewById(R.id.volume24h);
//        TextView circulating_supply = findViewById(R.id.circulating_supply);
//        TextView max_supply = findViewById(R.id.max_supply);
//        TextView market_cap = findViewById(R.id.market_cap);

         change1h = findViewById(R.id.change1h);
//        TextView change24h = findViewById(R.id.change24h);
//        TextView change7d = findViewById(R.id.change7d);

         txtMiddleHeader = findViewById(R.id.txt_middle_header);
         txtMiddleProfit = findViewById(R.id.txt_middle_profit);

         imgTrendingUp = findViewById(R.id.img_trending_up);
         imgMore= findViewById(R.id.img_more);
         imgBack = findViewById(R.id.img_back);
         imgLogo = findViewById(R.id.img_logo);

         clMiddleRight=findViewById(R.id.cl_middle_right);

            webView=findViewById(R.id.webView);


        name.setText(datum.getName() + " (" + datum.getSymbol() + ")");
        txtMiddleHeader.setText(datum.getName() + " Price");

        price.setText("$" + String.format("%,f", datum.getQuote().getUSD().getPrice()));

        change1h.setText(String.format("%.2f", datum.getQuote().getUSD().getPercentChange1h()) + "%");



        initColorStatus(datum);
        initLoadLogo(datum.getSymbol());
        initRecyclerIntervalChartTradingView(datum);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OperationResult isFav= CryptoFavRepository.getInstance().IsFavCrypto(datum);
                if (isFav.IsSuccess){
                    OperationResult<Boolean> result= CryptoFavRepository.getInstance().RemoveCryptoFav(datum);
                }else{
                    OperationResult<Boolean> result= CryptoFavRepository.getInstance().SaveCrypto(datum);
                    Toast.makeText(getBaseContext(), result.Message, Toast.LENGTH_LONG).show();
                }
                IsFavCrypto(datum);

            }
        });

        IsFavCrypto(datum);

    }

private void IsFavCrypto(Datum datum){
 OperationResult isFav= CryptoFavRepository.getInstance().IsFavCrypto(datum);
    if (isFav.IsSuccess){
        imgMore.setImageResource(R.drawable.ic_round_star_rate_24);
    }else{
        imgMore.setImageResource(R.drawable.ic_round_star_rate_24_gray);
    }

}


    /// end sharedpreferences
private  void  initRecyclerIntervalChartTradingView(Datum datum){
    RecyclerView RecyclerIntervalChartTradingView;
    List<IntervalChartTradingViewData> dummyList = new ArrayList<>();
    IntervalChartTradingViewAdapter recyclerAdapter;


    recyclerAdapter = new IntervalChartTradingViewAdapter(CoinPage.this, dummyList);
    RecyclerIntervalChartTradingView = (RecyclerView) findViewById(R.id.recyclerIntervalChartTradingView);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoinPage.this, LinearLayoutManager.HORIZONTAL, false);

    RecyclerIntervalChartTradingView.setLayoutManager(linearLayoutManager);

    RecyclerIntervalChartTradingView.setAdapter(recyclerAdapter);

    IntervalChartTradingViewData pet = new IntervalChartTradingViewData(1, "1Min", "1min");
    dummyList.add(pet);

    pet = new IntervalChartTradingViewData(2, "15 Min", "15");
    dummyList.add(pet);

    pet = new IntervalChartTradingViewData(3, "1 Hour", "1H");
    dummyList.add(pet);

    pet = new IntervalChartTradingViewData(4, "4 Hour", "4H");
    dummyList.add(pet);

    pet = new IntervalChartTradingViewData(5, "1 Day", "D");
    dummyList.add(pet);

    pet = new IntervalChartTradingViewData(6, "1 Week", "W");
    dummyList.add(pet);

    pet = new IntervalChartTradingViewData(7, "1 Month", "M");
    dummyList.add(pet);

    recyclerAdapter.notifyDataSetChanged();

    initChartDetailView(datum.getSymbol(),dummyList.get(0).getKeyValue());

}


    private void initChartDetailView(String symbol,String s){


    //    String ss="https://s.tradingview.com/widgetembed/?symbol=Bitfinex:BTCUSD&interval=240&hidesidetoolbar=0&symboledit=0&saveimage=0&toolbarbg=f1f3f6&studies=RSI@tv-basicstudies\u001FBB@tv-basicstudies&hideideas=1&theme=white&style=1&timezone=exchange&withdateranges=1&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&referral_id=5096&utm_source=bittrex.com&utm_medium=widget&utm_campaign=chart";


        String ss ="https://s.tradingview.com/widgetembed/?frameElemtentId=tradingview_76d87&symbol="+symbol+"USD&interval="+s+"&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=white&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT";

        webView.loadUrl(ss);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }

    private  void  initLoadLogo(String  getSymbolName){

        ImageView imgViewLogo = imgLogo;
       // String imageUri = "https://coinicons-api.vercel.app/api/icon/" + getSymbolName.toLowerCase(Locale.ROOT);

        String imageUri = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + datum.getId()+".png";
        try {


            /* another library for load image */
//            Uri uri = Uri.parse(imageUri);
//            holder.draweeView.setImageURI(uri);
            /* end */

            // initialize image loader before using
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(CoinPage.this));

            // URL for our image that we have to load..


            // with below method we are setting display option
            // for our image..
            options = new DisplayImageOptions.Builder()

                    // stub image will display when your
                    // image is loading
                    .showStubImage(
                            R.drawable.loading)

                    // below image will be displayed when
                    // the image url is empty
                    .showImageForEmptyUri(
                            R.drawable.placeholder)

                    // cachememory method will caches the
                    // image in users external storage
                    .cacheInMemory()

                    // cache on disc will caches the image
                    // in users internal storage
                    .cacheOnDisc()

                    .build();

            imageLoader.displayImage(imageUri, imgViewLogo, options,
                    null);

        } catch (Exception e) {
            imgViewLogo.setImageResource(R.drawable.noiamge);
        }

    }
    private  void initColorStatus(Datum datum){

        String price1h = datum.getQuote().getUSD().getPercentChange1h().toString();
        if (!price1h.contains("-")) {
            change1h.setTextColor(ContextCompat.getColor(CoinPage.this, R.color.colorPrimary));
            imgTrendingUp.setImageResource(R.drawable.ic_baseline_trending_up_24_green);
            txtMiddleProfit.setTextColor(ContextCompat.getColor(CoinPage.this, R.color.colorPrimary));
            clMiddleRight.setBackgroundResource(R.drawable.card_background_all_radius_green);

        } else {
            change1h.setTextColor(ContextCompat.getColor(CoinPage.this, R.color.red));
            imgTrendingUp.setImageResource(R.drawable.ic_baseline_trending_up_24_red);
            txtMiddleProfit.setTextColor(ContextCompat.getColor(CoinPage.this, R.color.red));
            clMiddleRight.setBackgroundResource(R.drawable.card_background_all_radius_red);
        }



    }

    private void initOldData(){
        Intent intent = getIntent();
        Datum datum = (Datum) intent.getSerializableExtra("coin");

        TextView name = findViewById(R.id.name);
        TextView price = findViewById(R.id.price);
        TextView date = findViewById(R.id.date);

        TextView symbol = findViewById(R.id.symbol);
        TextView slug = findViewById(R.id.slug);
        TextView volume24h = findViewById(R.id.volume24h);
        TextView circulating_supply = findViewById(R.id.circulating_supply);
        TextView max_supply = findViewById(R.id.max_supply);
        TextView market_cap = findViewById(R.id.market_cap);

        TextView change1h = findViewById(R.id.change1h);
        TextView change24h = findViewById(R.id.change24h);
        TextView change7d = findViewById(R.id.change7d);

        name.setText(datum.getName() + " (" + datum.getSymbol() + ")");
        price.setText("Price: $" + String.format("%,f", datum.getQuote().getUSD().getPrice()));
        date.setText("Last Updated: " + parseDateToddMMyyyy(datum.getLastUpdated()));

        symbol.setText("Symbol: " + datum.getSymbol());
        slug.setText("Slug: " + datum.getSlug());
        volume24h.setText("Volume/24h: $" + String.format("%,d", Math.round(datum.getQuote().getUSD().getVolume24h())));

        circulating_supply.setText("Circulating Supply: " + String.format("%.0f", datum.getCirculatingSupply()) + " " + datum.getSymbol());
        max_supply.setText("Max Supply: " + String.format("%.0f", datum.getMaxSupply()) + " " + datum.getSymbol());

        market_cap.setText("Market Cap: $" + String.format("%,d", Math.round(datum.getQuote().getUSD().getMarketCap())));

        change1h.setText(String.format("Change 1h: %.2f", datum.getQuote().getUSD().getPercentChange1h()) + "%");
        change24h.setText(String.format("Change 24h: %.2f", datum.getQuote().getUSD().getPercentChange24h()) + "%");
        change7d.setText(String.format("Change 7d: %.2f", datum.getQuote().getUSD().getPercentChange7d()) + "%");
    }

    private String parseDateToddMMyyyy(String time) {
        //parse the server timestamp. Make sure it is in UTC timezone as per API specifications.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        //format the utc server timestamp to local timezone.
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        output.setTimeZone(TimeZone.getDefault());

        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(date);
    }

    @Override
    public void onItemClickIntervalChartTradingView(IntervalChartTradingViewData intervalChartTradingViewData) {


        Log.e("TAG", "onItemClick:  "+datum.getSymbol()+" : "+ intervalChartTradingViewData.getKeyValue());
        initChartDetailView(datum.getSymbol(),intervalChartTradingViewData.getKeyValue());

    }
}
