package com.samansepahvand.cryptoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.samansepahvand.cryptoapp.R;
import com.samansepahvand.cryptoapp.db.model.Crypto;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<Crypto> listCryptos =new ArrayList<>();
    private LayoutInflater inflter;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public CustomSpinnerAdapter(Context applicationContext, List<Crypto> listCrypto) {
        this.context = applicationContext;
        this.listCryptos = listCrypto;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listCryptos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup viewGroup) {
        itemView = inflter.inflate(R.layout.custom_spinner_items, null);

        TextView name = itemView.findViewById(R.id.item_txt_title);

        ImageView imgLogo = itemView.findViewById(R.id.item_img_logo);

        loadCryptoLogo(imgLogo,listCryptos.get(position));

    //    imgLogo.setImageResource(listCryptos.get(position).getLogo());


       // symbol.setText(listCryptos.get(position).getSymbol());
        name.setText(listCryptos.get(position).getName()+'-'+listCryptos.get(position).getIds());
        return itemView;
    }


    private  void loadCryptoLogo(ImageView imgLogo, Crypto datum) {
       // String imageUri = "https://coinicons-api.vercel.app/api/icon/" + datum.getSymbol().toLowerCase(Locale.ROOT); // better qualify
        String imageUri = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + datum.getId()+".png";

        try {

            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder()

                    .showStubImage(
                            R.drawable.loading)
                    .showImageForEmptyUri(
                            R.drawable.placeholder)

                    .cacheInMemory()
                    .cacheOnDisc()

                    .build();

            imageLoader.displayImage(imageUri, imgLogo, options,
                    null);

        } catch (Exception e) {
            imgLogo.setImageResource(R.drawable.noiamge);
        }


    }


}