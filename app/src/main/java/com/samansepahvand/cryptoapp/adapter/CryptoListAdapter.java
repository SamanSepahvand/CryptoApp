package com.samansepahvand.cryptoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.samansepahvand.cryptoapp.R;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CryptoListAdapter extends RecyclerView.Adapter<CryptoListAdapter.ViewHolder> {

    private List<Datum> mData;
    private ItemClickListener mClickListener;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    // data is passed into the constructor
  public   CryptoListAdapter(List<Datum> data,Context context) {
        this.mData = data;
        this.context=context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.crypto_list_item_new, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Datum datum = mData.get(position);

//        // Set item views based on your views and data model
//        TextView name = holder.name;
//        name.setText(datum.getName() + " (" + datum.getSymbol() + ")");
//
//        TextView price = holder.price;
//        price.setText("Price: $" + String.format("%,f", datum.getQuote().getUSD().getPrice()));
//
//        TextView marketCap = holder.marketCap;
//        marketCap.setText("Market Cap: $" + String.format("%,d", Math.round(datum.getQuote().getUSD().getMarketCap())));
//
//        TextView volume24h = holder.volume24h;
//        volume24h.setText("Volume/24h: $" + String.format("%,d", Math.round(datum.getQuote().getUSD().getVolume24h())));
//
//        TextView textView1h = holder.textView1h;
//        textView1h.setText(String.format("1h: %.2f", datum.getQuote().getUSD().getPercentChange1h()) + "%");
//
//      //  +$1,256 (0.8%)
//
//        TextView textView24h = holder.textView24h;
//        textView24h.setText(String.format("24h: %.2f", datum.getQuote().getUSD().getPercentChange24h()) + "%");
//
//        TextView textView7d = holder.textView7d;
//        textView7d.setText(String.format("7d: %.2f", datum.getQuote().getUSD().getPercentChange7d()) + "%");


        TextView name = holder.name;
        name.setText(datum.getName() + " (" + datum.getSymbol() + ")");

        TextView price = holder.price;
        price.setText("$" + String.format("%,f", datum.getQuote().getUSD().getPrice()));


        TextView textView1h = holder.textView1h;
        textView1h.setText(String.format("1h: %.2f", datum.getQuote().getUSD().getPercentChange1h()) + "%");

        loadCryptoLogo(holder.imgLogo,datum);
        loadSparkLines(holder.itemImgSparklines,datum);

        ImagePercentStatus(holder,datum);

    }
    private  void loadCryptoLogo(ImageView imgLogo,Datum datum) {
    //    String imageUri = "https://coinicons-api.vercel.app/api/icon/" + datum.getSymbol().toLowerCase(Locale.ROOT); // better qualify
//        String imageUri = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + datum.getId()+".png";
        String imageUri = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + datum.getId()+".png";

        try {


            /* another library for load image */
//            Uri uri = Uri.parse(imageUri);
//            holder.draweeView.setImageURI(uri);
            /* end */

            // initialize image loader before using
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));

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

            imageLoader.displayImage(imageUri, imgLogo, options,
                    null);

        } catch (Exception e) {
            imgLogo.setImageResource(R.drawable.noiamge);
        }


    }
    private  void loadSparkLines(ImageView imgLoadSparkLines,Datum datum){
        try {
            String imageUri = "https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/"+datum.getId()+".png";


            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder()
                    .showStubImage(
                            R.drawable.sds)
                    .showImageForEmptyUri(
                            R.drawable.sds)
                    .cacheInMemory()
                    .cacheOnDisc()

                    .build();

            imageLoader.displayImage(imageUri, imgLoadSparkLines, options,
                    null);

        } catch (Exception e) {
            imgLoadSparkLines.setImageResource(R.drawable.noiamge);
        }
    }
    private void ImagePercentStatus(ViewHolder holder, Datum datum) {

            if(datum.getQuote().getUSD().getPercentChange1h()>0){
                holder.imgPercentStatus.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                holder.textView1h.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }else if (datum.getQuote().getUSD().getPercentChange1h()==0){

                holder.textView1h.setTextColor(ContextCompat.getColor(context, R.color.newBlack));
            }else{
                holder.imgPercentStatus.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                holder.textView1h.setTextColor(ContextCompat.getColor(context, R.color.newRedDark));
            }
        }

    public void UpdateList(List<Datum> newList) {
        mData = new ArrayList<>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }


    // Returns the total count of items in the list
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView name;
        TextView price;
        TextView marketCap;
        TextView volume24h;
        TextView textView1h;
        TextView textView24h;
        TextView textView7d;
        ImageView imgPercentStatus;
        ImageView imgLogo;
        ImageView itemImgSparklines;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


//            marketCap = itemView.findViewById(R.id.marketCap);
//            volume24h = itemView.findViewById(R.id.volume24h);
//            textView1h = itemView.findViewById(R.id.textView1h);
//            textView24h = itemView.findViewById(R.id.textView24h);
//            textView7d = itemView.findViewById(R.id.textView7d);

            name = itemView.findViewById(R.id.item_txt_title);
            price = itemView.findViewById(R.id.item_txt_price);
            textView1h = itemView.findViewById(R.id.item_txt_1h);

            imgPercentStatus=itemView.findViewById(R.id.item_img_precent_status);
            imgLogo = itemView.findViewById(R.id.item_img_logo);
            itemImgSparklines = itemView.findViewById(R.id.item_img_sparklines);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
   public  Datum getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
  public  void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}