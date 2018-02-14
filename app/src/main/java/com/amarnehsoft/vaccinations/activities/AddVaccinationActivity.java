package com.amarnehsoft.vaccinations.activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.VacChild;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.controllers.AlarmsController;
import com.amarnehsoft.vaccinations.controllers.NotifyVaccinationsController;
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB;
import com.amarnehsoft.vaccinations.database.sqlite.DBVacChild;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.fragments.dialogs.ConfirmDialog;
import com.amarnehsoft.vaccinations.fragments.dialogs.DatePickerFragment;
import com.amarnehsoft.vaccinations.utils.DateUtils;
import com.amarnehsoft.vaccinations.utils.NumberUtils;

import java.util.Date;
import java.util.UUID;

public class AddVaccinationActivity extends Base implements DatePickerFragment.IDatePickerFragment ,TimePickerDialog.OnTimeSetListener{

    private EditText txtTitle;
    private Button btnChange;
    private TextView txtDate,txtName;
    private Vaccination mVaccination;
    private Child mChild;
    private Date selectedDate;

    public static Intent newIntent(Context context, Vaccination vaccination,Child child){
        Intent intent = new Intent(context,AddVaccinationActivity.class);
        intent.putExtra("bean",vaccination);
        intent.putExtra("child",child);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccination);

        mVaccination = getIntent().getParcelableExtra("bean");
        mChild = getIntent().getParcelableExtra("child");

        txtTitle = findViewById(R.id.txtTitle);
        txtDate = findViewById(R.id.txtDate);
        btnChange = findViewById(R.id.btnChange);
        txtName = findViewById(R.id.txtName);

        if (mChild != null){
            txtName.setText(mChild.getName());
        }

        if (mVaccination != null){
            txtTitle.setText(mVaccination.getName());
//            selectedDate = DateUtils.incrementDateByDays(mVaccination.getAge()- DateUtils.getAgeInDays(mChild.getBirthDate()));
            selectedDate = mVaccination.getDate();
            txtDate.setText(DateUtils.formatDateWithoutTime(selectedDate));
        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment.newInstance(DateUtils.getCalendarFromDate(DateUtils.incrementDateByDays(1)),1,AddVaccinationActivity.this,false,true).show(getSupportFragmentManager(),DatePickerFragment.TAG);
            }
        });

        setTitle(getString(R.string.dateForYourChild));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mVaccination == null){
            getMenuInflater().inflate(R.menu.menu_save,menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_save_delete,menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            if (txtTitle.getText().toString().isEmpty()){
                Toast.makeText(this,getString(R.string.pleaseEnterTheTitle),Toast.LENGTH_SHORT).show();
            }else if (selectedDate == null){
                Toast.makeText(this,getString(R.string.pleaseChooseTheDate),Toast.LENGTH_SHORT).show();
            }else {
                if (mVaccination == null) {
                    mVaccination = new Vaccination();
                    mVaccination.setType(Vaccination.TYPE_DATE);
                    mVaccination.setCode(UUID.randomUUID().toString());
                }
                mVaccination.setName(txtTitle.getText().toString());
                mVaccination.setAge(DateUtils.getDiffDays(selectedDate, new Date()) + DateUtils.getAgeInDays(mChild.getBirthDate()));
                mVaccination.setDate(selectedDate);


                VacChild vacChild = new VacChild();
                vacChild.setVacCode(mVaccination.getCode());
                vacChild.setChildCode(mChild.getCode());
                vacChild.setDate(selectedDate);
                vacChild.setManualSet(1);

                DBVacChild.getInstance(this).addVacChild(vacChild);
//                NotifyVaccinationsController.notifyDate(this, mVaccination, mChild);
                VacinationDB.getInstance(this).saveBean(mVaccination);

                AlarmsController.addFixedAlarm(this,vacChild,0,0,false);
                finish();
            }
        }
        if (item.getItemId() == R.id.action_delete){
            new ConfirmDialog(this) {
                @Override
                public String title() {
                    return getString(R.string.deleteDate);
                }

                @Override
                public String msg() {
                    return getString(R.string.areYouSureYouWantToDeleteThisDate);
                }

                @Override
                public void onPositive() {
                    VacinationDB.getInstance(AddVaccinationActivity.this).deleteBean(mVaccination.getCode());
                    finish();
                }
            }.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(int reqCode, int year, int month, int day) {
        selectedDate = DateUtils.getDate(year,month,day);
        new TimePickerDialog(this,this,1,1,false).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selectedDate.setMinutes(minute);
        selectedDate.setHours(hourOfDay);
        txtDate.setText(DateUtils.formatDate(selectedDate));
    }
}
