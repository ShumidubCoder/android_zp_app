package shum.ru.myzp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/9/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ItemViewHolder> {



    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView cv;


        TextView tvIdFromDB;
        TextView tvForMounth;
        TextView tvDate;
        TextView tvValue;
        TextView tvStsDate;
        TextView tvStsValue;
        LinearLayout llZP;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tvIdFromDB = (TextView)itemView.findViewById(R.id.id_from_database);
            tvForMounth = (TextView)itemView.findViewById(R.id.x_mounth);
            tvDate = (TextView)itemView.findViewById(R.id.date_of_paying);
            tvValue = (TextView)itemView.findViewById(R.id.value_of_paying);
            tvStsDate = (TextView)itemView.findViewById(R.id.sts_date_of_paying);
            tvStsValue = (TextView)itemView.findViewById(R.id.sts_value_of_paying);
            llZP = (LinearLayout)itemView.findViewById(R.id.ll_zp);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ItemViewHolder itemHolder, int i) {


        String setId = String.valueOf(myZPItems.get(i).intId);
        itemHolder.tvIdFromDB.setText(setId);


        itemHolder.tvForMounth.setText(myZPItems.get(i).forMounth + "_" + myZPItems.get(i).type);
        itemHolder.tvDate.setText(myZPItems.get(i).date);
        itemHolder.tvValue.setText(myZPItems.get(i).value);

        itemHolder.tvStsDate.setText(myZPItems.get(i).stsDate);
        itemHolder.tvStsValue.setText(myZPItems.get(i).stsValue);


        if ((myZPItems.get(i).type).contains("ZP")) itemHolder.llZP.setBackgroundColor(Color.WHITE);
//        else if (!(myZPItems.get(i).type).contains("ZP")) return;
        else if (!(myZPItems.get(i).type).contains("ZP")) itemHolder.llZP.setBackgroundColor(Color.parseColor("#9269d2f5"));






    }





    @Override
    public int getItemCount() {
        return myZPItems.size();
    }


    private List<MyZPItem> myZPItems;

    RVAdapter(List<MyZPItem> myZPItems){
        this.myZPItems = myZPItems;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

