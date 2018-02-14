package com.amarnehsoft.vaccinations.admin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.adapters.Adapter;
import com.amarnehsoft.vaccinations.beans.Stock;
import com.amarnehsoft.vaccinations.database.db2.DBStock;
import com.amarnehsoft.vaccinations.database.firebase.FBStocks;
import com.amarnehsoft.vaccinations.database.firebase.FirebaseHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class StocksListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String corporationCode;
    private String catCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks_list);

        corporationCode = getIntent().getStringExtra("corporationCode");
        catCode = getIntent().getStringExtra("catCode");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
    private void refresh(){
        Log.d("Amarneh","refreah");
//        FBStocks fbStocks = new FBStocks(this,catCode,corporationCode);
        final StockAdapter adapter = new StockAdapter(DBStock.getInstance(this).getAll(catCode,corporationCode));
//        fbStocks.setListener(new FirebaseHelper.Listiner() {
//            @Override
//            public void onChildAdded(Object bean, String code) {
//                adapter.notifyDataSetChanged();
//            }
//        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            add();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void add() {
        Intent i = new Intent(this,AddEditStockActivity.class);
        i.putExtra("corporationCode",corporationCode);
        i.putExtra("catCode",catCode);

        startActivity(i);
    }
    public class StockAdapter extends Adapter<Stock> {

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

        public class Holder extends com.amarnehsoft.vaccinations.adapters.Holder<Stock> {
            private TextView txtName,txtDesc,txtPrice;
            private ImageView img;

            public Holder(View itemView) {
                super(itemView);
                txtName=itemView.findViewById(R.id.txtName);
                txtPrice=itemView.findViewById(R.id.txtPrice);
                txtDesc=itemView.findViewById(R.id.txtDesc);
                img = itemView.findViewById(R.id.img);

                txtDesc.setVisibility(View.VISIBLE);
            }

            @Override
            public void onClicked(View v) {
                Intent i = new Intent(StocksListActivity.this,AddEditStockActivity.class);
                i.putExtra("bean",mItem);
                i.putExtra("corporationCode",mItem.getCorporationCode());
                i.putExtra("catCode",mItem.getCatCode());
                startActivity(i);

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

}
