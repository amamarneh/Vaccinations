package com.amarnehsoft.vaccinations.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.fragments.KindergartenInfoFragment;
import com.amarnehsoft.vaccinations.fragments.itemDetail.CorporationDetailFragment;
import com.bumptech.glide.Glide;

public class CorporationInfoActivity extends Base{

    private Corporation mBean;
    private TextView txtName,txtDesc,txtContact,txtAddress;
    private ImageView img;
    private View nameLayout,layout;

    public static Intent newIntent(Context context, Corporation bean){
        Intent intent = new Intent(context,CorporationInfoActivity.class);
        intent.putExtra("bean",bean);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporation_info);
        mBean = getIntent().getParcelableExtra("bean");

        txtName=findViewById(R.id.txtName);
        txtDesc=findViewById(R.id.txtDesc);
        txtAddress=findViewById(R.id.txtAddress);
        txtContact=findViewById(R.id.txtContact);
        img=findViewById(R.id.imgName);
        nameLayout=findViewById(R.id.nameLayout);
        layout=findViewById(R.id.layout);

        if (mBean != null){
            txtName.setText(mBean.getName());
            txtDesc.setText(mBean.getDesc());
            txtContact.setText(mBean.getContact());
            txtAddress.setText(mBean.getAddress());

            if(mBean.getImg() != null){
                Glide.with(this).load(mBean.getImg()).into(img);
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CorporationDetailFragment.newInstance(mBean)).commit();

            nameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layout.getVisibility()==View.VISIBLE){
                        layout.setVisibility(View.GONE);
                    }else {
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
