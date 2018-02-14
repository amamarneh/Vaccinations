package com.amarnehsoft.vaccinations.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.controllers.SPController;
import com.amarnehsoft.vaccinations.fragments.dialogs.DatePickerFragment;
import com.amarnehsoft.vaccinations.utils.DateUtils;

import java.util.Date;

public class SettingsActivity extends Base implements TimePickerDialog.OnTimeSetListener {

    private EditText txtTitle;
    private Button btnTime;
    private int H,M;
    public static Intent newIntent(Context context){
        Intent intent =  new Intent(context,SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txtTitle = findViewById(R.id.txtTitle);
        btnTime = findViewById(R.id.btnTime);
        txtTitle.setText(SPController.getInstance(this).getTitle());

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this,SettingsActivity.this,SPController.getInstance(SettingsActivity.this).getHourOfDay()
                        ,SPController.getInstance(SettingsActivity.this).getMinute(),false);
                timePickerDialog.show();
            }
        });


        Date date = new Date();
        date.setHours(SPController.getInstance(this).getHourOfDay());
        date.setMinutes(SPController.getInstance(this).getMinute());
        String time = DateUtils.formatTime(date);
        btnTime.setText(getString(R.string.notifyTime)+time);

        setTitle(getString(R.string.settings));

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
            SPController.getInstance(this).saveHourOfDay(H);
            SPController.getInstance(this).saveMinute(M);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        H= hourOfDay;
        M= minute;
        Date date = new Date();
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        String time = DateUtils.formatTime(date);
        btnTime.setText(getString(R.string.notifyTime)+time);
    }
}
