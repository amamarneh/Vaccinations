package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.schema.VacinationTable;
import com.amarnehsoft.vaccinations.database.sqlite.DBHelper;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 2/11/2018.
 */

public class DBVaccination <B extends Vaccination,T extends VacinationTable> extends DBHelper2<Vaccination> {

    private static DBVaccination instance;
    public static DBVaccination getInstance(Context context){
        if(instance == null){
            instance = new DBVaccination(context);
        }
        return instance;
    }

    private DBVaccination(Context context) {
        super(context,Vaccination.class);
    }

    public List<B> getSearchQueryResult(String query)
    {
        SQLiteDatabase db = getReadableDatabase();
        List<B> list = new ArrayList<>();

        Cursor rs= null;
        try {
            rs = db.rawQuery("SELECT * FROM " + T.TBL_NAME +
                            " WHERE " + T.Cols.NAME + " LIKE '%" + query + "%' "
                    , null);

            if (rs.moveToFirst())
            {
                while (!rs.isAfterLast()) {
                    B bean = (B)newBean();
                    fillBeanFromCursor(rs, bean);
                    list.add(bean);
                    rs.moveToNext();
                }
            }
        }finally {
            if (rs != null)
                rs.close();
        }
        return list;
    }


    public int saveBean(B bean)
    {
        Log.d("tag","saveBean, date="+bean.getDate()+", age="+bean.getAge());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        fillValuesFromBean(bean, values);

        if(this.getBeanById(bean.getCode()) != null)
        {
            db.update(T.TBL_NAME, values, T.Cols.CODE + " = ?",
                    new String[]{bean.getCode()});
        }
        else
            db.insert(T.TBL_NAME, null, values);

        return 1;
    }

    public B getBeanById(String id)
    {
        SQLiteDatabase db = getReadableDatabase();
        B bean = null;
        Cursor rs = null;
        try {
            rs = db.query(T.TBL_NAME, null
                    , T.Cols.CODE + "=?", new String[]{id+""}, null, null, null);
            if (rs.moveToFirst()) {
                bean = (B)newBean();
                fillBeanFromCursor(rs, bean);
            }
        }finally {
            if (rs != null)
                rs.close();
        }
        return bean;
    }

    public List<B> getUpCommingVaccinationsForAge(int age)
    {
        return getUpCommingVaccinationsForAge(age,0);
    }

    public List<B> getUpCommingVaccinationsForAge(int age,int limit)
    {
        SQLiteDatabase db = getReadableDatabase();
        List<B> list = new ArrayList<>();
        String selection = T.Cols.AGE + " >= ?";
        String[] args = new String[]{age+""};
        String orderBy = orderBy();
        String limitStr = ""+limit;
        if (limit==0) limitStr=null;

        Cursor rs = null;
        try {
            rs = db.query(T.TBL_NAME, null
                    , selection, args, null, null, orderBy,limitStr);
            if (rs.moveToFirst()) {
                while (!rs.isAfterLast()){
                    B bean = (B)newBean();
                    fillBeanFromCursor(rs, bean);
                    list.add(bean);
                    rs.moveToNext();
                }
            }
        }finally {
            if (rs != null)
                rs.close();
        }
        return list;
    }
    public B getBeanByName(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        B bean = null;
        Cursor rs = null;
        try {
            rs = db.query(T.TBL_NAME, null
                    , T.Cols.NAME+ "=?", new String[]{name+""}, null, null, null);
            if (rs.moveToFirst()) {
                bean = (B)newBean();
                fillBeanFromCursor(rs, bean);
            }
        }finally {
            if (rs != null)
                rs.close();
        }
        return bean;
    }

    @Override
    protected String getTableName() {
        return T.TBL_NAME;
    }

    @Override
    protected void fillBeanFromCursor(Cursor rs, Vaccination bean) {
        bean.setCode(rs.getString(rs.getColumnIndex(T.Cols.CODE)));
        bean.setName(rs.getString(rs.getColumnIndex(T.Cols.NAME)));
        bean.setAge(rs.getInt(rs.getColumnIndex(T.Cols.AGE)));
        bean.setNewAge(rs.getInt(rs.getColumnIndex(T.Cols.ARG_NEW_AGE)));
        bean.setManuallySet(rs.getInt(rs.getColumnIndex(T.Cols.ARG_MANUALLY_SET)));
        bean.setDesc(rs.getString(rs.getColumnIndex(T.Cols.DESC)));
        bean.setType(rs.getInt(rs.getColumnIndex(T.Cols.TYPE)));
    }

    public int updateBean(B bean)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        fillValuesFromBean(bean, values);
        db.update(T.TBL_NAME, values, T.Cols.CODE + " = ?",
                new String[]{bean.getCode()});

        return 1;
    }

    public int saveList(List<B> list, boolean deleteBeforeInsert)
    {
        if (deleteBeforeInsert)
            deleteAll();
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        for (B bean : list) {
            ContentValues values = new ContentValues();
            fillValuesFromBean(bean, values);
            db.insert(T.TBL_NAME, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }

    private void fillValuesFromBean(B bean, ContentValues values) {
        values.put(T.Cols.CODE, bean.getCode());
        values.put(T.Cols.NAME, bean.getName());
        values.put(T.Cols.AGE,bean.getAge());
        values.put(T.Cols.ARG_NEW_AGE,bean.getNewAge());
        values.put(T.Cols.ARG_MANUALLY_SET,bean.getManuallySet());
        values.put(T.Cols.DESC,bean.getDesc());
        values.put(T.Cols.TYPE,bean.getType());
    }


    public boolean deleteBean(String key){
        B person = getBeanById(key);
        if(person != null){
            //delete
            String selection = T.Cols.CODE + " = ? ";
            String[] selectionArgs = new String[]{key + ""};
            SQLiteDatabase db = getWritableDatabase();
            db.delete(T.TBL_NAME, selection, selectionArgs);
        }
        return person != null;
    }

    public void deleteList(List<B> list){
        for (B bean : list){
            deleteBean(bean.getCode());
        }
    }

    @Override
    protected String orderBy() {
        return T.Cols.AGE;
    }
}
