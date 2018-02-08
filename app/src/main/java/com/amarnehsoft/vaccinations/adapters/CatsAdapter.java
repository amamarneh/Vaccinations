package com.amarnehsoft.vaccinations.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.bumptech.glide.Glide;

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
        private ImageView img;

        public Holder(View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            img=itemView.findViewById(R.id.img);
        }

        @Override
        public void onClicked(View v) {
            listener.onCatClicked(mItem.getCode());
        }

        @Override
        public void bind(Cat item) {
            super.bind(item);
            txtName.setText(item.getName());
            if(mItem.getImg() != null){
                Glide.with(itemView).load(mItem.getImg()).into(img);
            }
        }
    }

    public interface Listener{
        void onCatClicked(String catCode);
    }
}
