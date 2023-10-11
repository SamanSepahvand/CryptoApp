package com.samansepahvand.cryptoapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class CryptoFavListAdapter extends RecyclerView.Adapter<CryptoFavListAdapter.ViewHolder> {

    private List<Datum> mData;
    private ItemClickListener mClickListener;
    private Context mContext;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public CryptoFavListAdapter(Context mContext, List<Datum> data) {
        this.mContext = mContext;
        this.mData = data;
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


        TextView name = holder.name;
        name.setText(datum.getName() + " (" + datum.getSymbol() + ")");

        TextView price = holder.price;
        price.setText("$" + String.format("%,f", datum.getQuote().getUSD().getPrice()));


        TextView textView1h = holder.textView1h;
        String price1h = datum.getQuote().getUSD().getPercentChange1h().toString();
        if (!price1h.contains("-"))
            textView1h.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        else
            textView1h.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        textView1h.setText(String.format("1h: %.2f", datum.getQuote().getUSD().getPercentChange1h()) + "%");


        ImageView imgViewLogo = holder.imgLogo;
        String imageUri = "https://coinicons-api.vercel.app/api/icon/" + datum.getSymbol().toLowerCase(Locale.ROOT);
        try {


            /* another library for load image */
//            Uri uri = Uri.parse(imageUri);
//            holder.draweeView.setImageURI(uri);
            /* end */

            // initialize image loader before using
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

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
        TextView textView1h;

        ImageView imgLogo;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            //  draweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view); another library
            name = itemView.findViewById(R.id.item_txt_title);
            price = itemView.findViewById(R.id.item_txt_price);
            textView1h = itemView.findViewById(R.id.item_txt_1h);
            imgLogo = itemView.findViewById(R.id.item_img_logo);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Datum getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}