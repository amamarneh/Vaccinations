package com.amarnehsoft.vaccinations.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.controllers.SPController;

public class SettingsActivity extends Base {

    private EditText txtTitle;

    public static Intent newIntent(Context context){
        Intent intent =  new Intent(context,SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(SPController.getInstance(this).getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            SPController.getInstance(this).setTitle(txtTitle.getText().toString());
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
