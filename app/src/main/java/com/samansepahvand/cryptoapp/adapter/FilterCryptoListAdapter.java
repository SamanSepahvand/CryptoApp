package com.samansepahvand.cryptoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samansepahvand.cryptoapp.R;
import com.samansepahvand.cryptoapp.metamodel.IntervalChartTradingViewData;

import java.util.List;

public class FilterCryptoListAdapter extends RecyclerView.Adapter<FilterCryptoListAdapter.MyViewHolder>  {

    private List<IntervalChartTradingViewData> petsList;
    private Context context;
    private FilterCryptoListAdapter.ItemClickListenerCryptoList itemClickListenerCryptoList;
    private static int lastClickedPosition = -1;
    private int selectedItem;

    public FilterCryptoListAdapter(Context context, List<IntervalChartTradingViewData> petsList) {
        this.context = context;
        this.petsList = petsList;
        selectedItem = 0;
        this.itemClickListenerCryptoList=(FilterCryptoListAdapter.ItemClickListenerCryptoList)context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_filter_crypto_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setTag(petsList.get(position));
        holder.name.setText(petsList.get(position).getName());

        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        holder.name.setTextColor(context.getResources().getColor(R.color.newBlack));

        if (selectedItem == position) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.name.setTextColor(context.getResources().getColor(R.color.white));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousItem = selectedItem;
                selectedItem = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);

              itemClickListenerCryptoList.onItemClickCryptoList(petsList.get(position).getKeyValue());
            }
        });



    }


    @Override
    public int getItemCount() {
        return petsList.size();
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListenerCryptoList {
        void onItemClickCryptoList(String apiName);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.item_filter_name);
            cardView = (CardView) view.findViewById(R.id.cardview);

        }



    }


}

