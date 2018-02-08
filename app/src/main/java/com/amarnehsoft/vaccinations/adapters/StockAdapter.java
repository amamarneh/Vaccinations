package com.amarnehsoft.vaccinations.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Stock;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by jcc on 1/26/2018.
 */

public class StockAdapter extends Adapter<Stock>{

    public StockAdapter(List<Stock> items) {
        super(items);
    }

    @Override
    public int getLayoutId() {
        return R.layout.row_stock;
    }

    @Override
    public Holder getNewHolder(View v) {
        return new Holder(v);
    }

    class Holder extends com.amarnehsoft.vaccinations.adapters.Holder<Stock> {
        private TextView txtName,txtDesc,txtPrice;
        private ImageView img;

        public Holder(View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtDesc=itemView.findViewById(R.id.txtDesc);
            img = itemView.findViewById(R.id.img);
        }

        @Override
        public void onClicked(View v) {
            if (txtDesc.getVisibility()==View.VISIBLE){
                txtDesc.setVisibility(View.GONE);
            }else {
                txtDesc.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void bind(Stock item) {
            super.bind(item);
            txtName.setText(item.getName());
            txtPrice.setText(item.getPrice()+"");
            txtDesc.setText(item.getDesc());
            if(mItem.getImg() != null){
                Glide.with(itemView).load(mItem.getImg()).into(img);
            }
        }
    }
}
