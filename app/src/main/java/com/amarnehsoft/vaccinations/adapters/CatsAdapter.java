package com.amarnehsoft.vaccinations.adapters;

import android.view.View;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Cat;

import java.util.List;

/**
 * Created by jcc on 1/26/2018.
 */

public class CatsAdapter extends Adapter<Cat> {
    private Listener listener;

    public CatsAdapter(List<Cat> items,Listener listener) {
        super(items);
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.row_cat;
    }

    @Override
    public Holder getNewHolder(View v) {
        return new Holder(v);
    }

    class Holder extends com.amarnehsoft.vaccinations.adapters.Holder<Cat> {
        private TextView txtName;

        public Holder(View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
        }

        @Override
        public void onClicked(View v) {
            listener.onCatClicked(mItem.getCode());
        }

        @Override
        public void bind(Cat item) {
            super.bind(item);
            txtName.setText(item.getName());
        }
    }

    public interface Listener{
        void onCatClicked(String catCode);
    }
}
