package com.amarnehsoft.vaccinations.activities.itemDetail;

import android.content.Context;
import android.content.Intent;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.fragments.itemDetail.CorporationDetailFragment;
import com.amarnehsoft.vaccinations.fragments.itemDetail.ItemDetailFragment;


/**
 * Created by jcc on 6/3/2017.
 */

public class CorporationsDetailActivity extends ItemDetailActivity<Corporation> {

    public static Intent getIntent(Context ctx, Corporation bean){
        Intent intent = new Intent(ctx,CorporationsDetailActivity.class);
        intent.putExtra("bean",bean);
        return intent;
    }

    @Override
    protected void onFabClicked() {
//        startActivityForResult(AddEditPeronCatActivity.getIntent(this,mKey),REQ_EDIT);
    }

    @Override
    protected ItemDetailFragment getFragment()
    {
        ItemDetailFragment fragment = CorporationDetailFragment.newInstance(mBean);
        return fragment;
    }

    @Override
    protected String getBarTitle()
    {
        String name = getString(R.string.not_found);
        if (mBean != null)
            name = mBean.getName();
        return name;
    }

    @Override
    protected void refreshView() {
        String name = getString(R.string.not_found);
        if (mBean != null)
            name = mBean.getName();
        mAppBarLayout.setTitle(name);
    }

    @Override
    protected void onDeleteMenuItemClicked(Corporation bean) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT){
            if (resultCode == RESULT_OK){
                mBean = data.getParcelableExtra("data");
                refreshView();
                setResult(RESULT_OK,data);
            }
        }
    }

}
