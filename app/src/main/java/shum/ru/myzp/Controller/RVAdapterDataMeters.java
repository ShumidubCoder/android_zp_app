package shum.ru.myzp.Controller;

import android.app.Application;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import shum.ru.myzp.Model.DBDataMeters;
import shum.ru.myzp.Model.ItemDataMeters;
import shum.ru.myzp.R;
import shum.ru.myzp.View.MainActivity;


public class RVAdapterDataMeters extends RecyclerView.Adapter<RVAdapterDataMeters.ItemViewHolder> {


    List<ItemDataMeters> lstItemsDM;


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvDataMeters;



        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDataMeters = (TextView)itemView.findViewById(R.id.tv_data_meters);

        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_data_meters, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }


    @Override
    public void onBindViewHolder(ItemViewHolder itemHolder, int i) {


        itemHolder.tvDataMeters.setText(lstItemsDM.get(i).strValue);

        if (i%2==0) itemHolder.tvDataMeters.setBackgroundColor(Color.GRAY);

    }




    @Override
    public int getItemCount() {

       return lstItemsDM.size();


    }




    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

