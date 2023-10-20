package com.samansepahvand.cryptoapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.samansepahvand.cryptoapp.R;
import com.samansepahvand.cryptoapp.adapter.CustomSpinnerAdapter;
import com.samansepahvand.cryptoapp.apihelper.APIClient;
import com.samansepahvand.cryptoapp.apihelper.APIInterface;
import com.samansepahvand.cryptoapp.db.model.Crypto;
import com.samansepahvand.cryptoapp.db.model.CryptoFav;
import com.samansepahvand.cryptoapp.db.repository.CryptoFavRepository;
import com.samansepahvand.cryptoapp.db.repository.CryptoRepository;
import com.samansepahvand.cryptoapp.helper.Utility;
import com.samansepahvand.cryptoapp.metamodel.retrofit.CryptoList;
import com.samansepahvand.cryptoapp.metamodel.retrofit.single.CryptoSingle;
import com.samansepahvand.cryptoapp.metamodel.retrofit.single.DatumSingle;
import com.samansepahvand.cryptoapp.metamodel.retrofit.single.QuoteSingle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptocurencyConverterCalculator extends AppCompatActivity {

    private APIInterface apiInterface;
    public TextView firstSymbol,secondAmount,secondSymbol,txtTotalCrypto;
    public EditText edtFirstAmount;
    public ImageView imgRotate,imgBack;
    public Spinner spinnerFirst,spinnerSecond;
    public List<Crypto> cryptoList= new ArrayList<>();

    public Crypto cryptoFirst= new Crypto();
    public Crypto cryptoSecond= new Crypto();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptocurency_converter_calculator_new);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        initView();

    }

    private void initView() {


        txtTotalCrypto=findViewById(R.id.txt_total_crypto);


        edtFirstAmount=findViewById(R.id.txt_amount_first);
        firstSymbol=findViewById(R.id.txt_symbole_first);

        secondAmount=findViewById(R.id.txt_amount_convert);
        secondSymbol=findViewById(R.id.txt_symbole_convert);

        spinnerFirst=findViewById(R.id.spinnerFirst);
        spinnerSecond=findViewById(R.id.spinner_convert);

        imgRotate=findViewById(R.id.img_rotate);


        cryptoList= CryptoRepository.getInstance().GetCrypto();
        if (cryptoList.size()>0) {

            initFirstSpinner();
            initSecondSpinner();
            cryptoFirst=cryptoList.get(0);
            cryptoFirst.setPosition(0);
            txtTotalCrypto.setText("Total Crypto can you convert :"+cryptoList.size()+"");
            CallApiConvert(1,cryptoFirst.getIds(),cryptoList.get(1).getIds());
        }else{
            txtTotalCrypto.setText("Total Crypto can you convert :"+"0"+"");
            Toast.makeText(getBaseContext(), "Nothing to Show ! system redirect you.", Toast.LENGTH_LONG).show();
            startActivity( new Intent(CryptocurencyConverterCalculator.this, ListCryptoActivity.class));
        }



        imgRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cryptoFirst!=null && cryptoSecond!=null){

                    Crypto crypto= new Crypto();
                    crypto=cryptoFirst;
                    cryptoFirst=cryptoSecond;
                    cryptoSecond=crypto;

                    firstSymbol.setText(cryptoFirst.getSymbol());
                    secondSymbol.setText(crypto.getSymbol());

                    Utility.AnimRotate(imgRotate);



                    spinnerFirst.setSelection(cryptoFirst.getPosition());
                    spinnerSecond.setSelection(cryptoSecond.getPosition());


                }else{
                    Toast.makeText(getBaseContext(), "plz Select Crypto !", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void initSecondSpinner() {



        spinnerSecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                cryptoSecond=cryptoList.get(position);
                cryptoSecond.setPosition(position);
                secondSymbol.setText(cryptoSecond.getSymbol());
                String sTextFromET = edtFirstAmount.getText().toString();
                if(sTextFromET!=null && !sTextFromET.equals("")){
                    int nIntFromET = new Integer(sTextFromET).intValue();
                    CallApiConvert(nIntFromET,cryptoFirst.getIds(),cryptoSecond.getIds());
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, cryptoList);
        spinnerSecond.setAdapter(adapter);


    }



    private void CallApiConvert(int firstAmount,int id,int convert_id) {

        try {

            edtFirstAmount.setText(firstAmount+"");


            CryptoFavRepository.getInstance().GetCryptoFavForApi();
            Call<CryptoSingle> call2 = apiInterface.PriceConversion(firstAmount+"",id+"",convert_id+"");

            call2.enqueue(new Callback<CryptoSingle>() {
                @Override
                public void onResponse(Call<CryptoSingle> call, Response<CryptoSingle> response) {
                    CryptoSingle list = response.body();

                    if (list.getData().getQuote().size()>0) {
                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(list.getData().getQuote().toString());
                            Iterator<String> iter = jObject.keys();

                            while(iter.hasNext()){
                                String key = iter.next();
                                if(jObject.get(key) instanceof JSONObject) {
                                    JSONObject innerJObject = jObject.getJSONObject(key);
                                    String price = innerJObject.getString("price");
                                    String last_updated = innerJObject.getString("last_updated");
                                    Float nIntFromET = new Float(price).floatValue();

                                    secondAmount.setText((String.format("%.7f", nIntFromET))+"");

                                    Log.e("details", "id = " + key + ", " + "price = " + price + ", " + "points = " + last_updated);
                                } else if (jObject.get(key) instanceof String){
                                    String value = jObject.getString("type");
                                    Log.e("key = type", "value = " + value);
                                }

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CryptoSingle> call, Throwable t) {
                    Toast.makeText(CryptocurencyConverterCalculator.this, "onFailure", Toast.LENGTH_SHORT).show();
                    Log.d("XXXX", t.getLocalizedMessage());
                    call.cancel();
                }
            });

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "getCoinList: " + e.getMessage());
        }
    }

    private void initFirstSpinner() {
        spinnerFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                cryptoFirst=cryptoList.get(position);
                cryptoFirst.setPosition(position);
                firstSymbol.setText(cryptoFirst.getSymbol());

                String sTextFromET = edtFirstAmount.getText().toString();
                if(sTextFromET!=null && !sTextFromET.equals("")){
                    int nIntFromET = new Integer(sTextFromET).intValue();
                    CallApiConvert(nIntFromET,cryptoFirst.getIds(),cryptoSecond.getIds());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, cryptoList);
        spinnerFirst.setAdapter(adapter);


    }


}