package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public static final int VERSION = 3;
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
        db.execSQL("drop table if EXISTS " + CatTable.TBL_NAME);
        db.execSQL("drop table if EXISTS " + CorCatTable.TBL_NAME);
        db.execSQL("drop table if EXISTS " + CorporationTable.TBL_NAME);
        db.execSQL("drop table if EXISTS " + KindergartenTable.TBL_NAME);
        db.execSQL("drop table if EXISTS " + StockTable.TBL_NAME);
        db.execSQL("drop table if EXISTS " + VacinationTable.TBL_NAME);
        db.execSQL("drop table if EXISTS " + AdTable.TBL_NAME);
        onCreate(db);
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