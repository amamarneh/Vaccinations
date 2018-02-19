package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Ad;
import com.amarnehsoft.vaccinations.database.DBVersions;
import com.amarnehsoft.vaccinations.database.db2.schema.AdTable;
import com.amarnehsoft.vaccinations.database.db2.schema.CatTable;
import com.amarnehsoft.vaccinations.database.db2.schema.CorCatTable;
import com.amarnehsoft.vaccinations.database.db2.schema.CorporationTable;
import com.amarnehsoft.vaccinations.database.db2.schema.KindergartenTable;
import com.amarnehsoft.vaccinations.database.db2.schema.StockTable;
import com.amarnehsoft.vaccinations.database.schema.ChildTable;
import com.amarnehsoft.vaccinations.database.schema.VacinationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 2/11/2018.
 */

public abstract class DBHelper2<T> extends SQLiteOpenHelper
{

    protected Class<T> entityClass;

    protected Context mContext;
    private int mNumberOfItems = 100;

    public static final int VERSION = DBVersions.CURRENT_VERSION.value();
    public static final String DATABASE_NAME = "vaccinations2.db";

    protected DBHelper2(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    protected DBHelper2(Context context, Class<T> entityClass){
        this(context);
        this.entityClass = entityClass;
    }

    protected T newBean(){
        T bean = null;
        try {
            bean = (T)entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public void setNumberOfItems(int numberOfItems){
        mNumberOfItems = numberOfItems;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CatTable._CREATE_TABLE);
        db.execSQL(CorCatTable._CREATE_TABLE);
        db.execSQL(CorporationTable._CREATE_TABLE);
        db.execSQL(KindergartenTable._CREATE_TABLE);
        db.execSQL(StockTable._CREATE_TABLE);
        db.execSQL(VacinationTable._CREATE_TABLE);
        db.execSQL(AdTable._CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (oldVersion < DBVersions.Versoin.VERSOIN_ADD_DATES_TO_AD.value()){
            try {
                db.execSQL("alter table " + AdTable.TBL_NAME + " add column " + AdTable.Cols.FROM_DATE + " integer");
                db.execSQL("alter table " + AdTable.TBL_NAME + " add column " + AdTable.Cols.TO_DATE + " integer");
                Log.e("Amarneh","upgraded successfully, oldVersion="+oldVersion);
            }catch (Exception e) {
                Log.e("Amarneh", "error while upgrading, oldVersion=" + oldVersion);
            }
        }

        if (oldVersion < DBVersions.Versoin.VERSOIN_ADD_SECONDS_TO_AD.value()){
            try {
                db.execSQL("alter table " + AdTable.TBL_NAME + " add column " + AdTable.Cols.SECONDS+ " integer default 5");
                Log.e("Amarneh","upgraded successfully, oldVersion="+oldVersion);
            }catch (Exception e) {
                Log.e("Amarneh", "error while upgrading, oldVersion=" + oldVersion);
            }
        }
    }

    public int getNoOfBeans(){
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToFirst())
                count = cursor.getInt(0);

        }finally {
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return count;
    }

    public List<T> getAll()
    {
        SQLiteDatabase db = getReadableDatabase();
        List<T> list = new ArrayList<>();
        String selection=null;
        String[] args = null;

        Cursor rs = null;
        try {
            rs = db.query(getTableName(), null
                    , selection, args , null, null, orderBy());

            if (rs.moveToFirst())
            {
                while (!rs.isAfterLast()) {
                    T bean = (T)newBean();
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

    protected String orderBy(){
        return null;
    }

    public int deleteAll()
    {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(getTableName(), null, null);
        return result;
    }


    public abstract T getBeanById(String id);
    protected abstract String getTableName();
    protected abstract void fillBeanFromCursor(Cursor rs,T bean);
}