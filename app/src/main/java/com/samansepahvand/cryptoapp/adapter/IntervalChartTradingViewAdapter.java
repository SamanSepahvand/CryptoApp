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
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;

import java.util.List;

public class IntervalChartTradingViewAdapter extends RecyclerView.Adapter<IntervalChartTradingViewAdapter.MyViewHolder>  {

    private List<IntervalChartTradingViewData> petsList;
    private Context context;
    private IntervalChartTradingViewAdapter.ItemClickListenerIntervalChartTradingView itemClickListenerIntervalChartTradingView;
    private static int lastClickedPosition = -1;
    private int selectedItem;

    public IntervalChartTradingViewAdapter(Context context, List<IntervalChartTradingViewData> petsList) {
        this.context = context;
        this.petsList = petsList;
        selectedItem = 0;
        itemClickListenerIntervalChartTradingView=(ItemClickListenerIntervalChartTradingView) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interval_chart_tradingview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setTag(petsList.get(position));
        holder.name.setText(petsList.get(position).getName());

        holder.view1.setBackgroundColor(context.getResources().getColor(R.color.newTransparent));

        if (selectedItem == position) {
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousItem = selectedItem;
                selectedItem = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);


                itemClickListenerIntervalChartTradingView.onItemClickIntervalChartTradingView(petsList.get(position));
            }
        });



    }


    @Override
    public int getItemCount() {
        return petsList.size();
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListenerIntervalChartTradingView {
        void onItemClickIntervalChartTradingView(IntervalChartTradingViewData datum);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public View view1;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.item_interval_name);
            view1= (View) view.findViewById(R.id.item_view_selected);

        }



    }


}

