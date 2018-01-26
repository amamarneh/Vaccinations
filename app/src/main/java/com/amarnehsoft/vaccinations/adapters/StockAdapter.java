package com.amarnehsoft.vaccinations.adapters;

import android.view.View;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Stock;

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
        private TextView txtName;
        private TextView txtPrice;
        private TextView txtDesc;

        public Holder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClicked(View v) {

        }
    }
}