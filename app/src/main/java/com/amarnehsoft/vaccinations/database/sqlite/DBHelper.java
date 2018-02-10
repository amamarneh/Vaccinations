package com.amarnehsoft.vaccinations.database.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.database.DBVersions;
import com.amarnehsoft.vaccinations.database.schema.ChildTable;
import com.amarnehsoft.vaccinations.database.schema.VacinationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amarneh on 07/08/2016.
 */
public abstract class DBHelper<T> extends SQLiteOpenHelper
{

    protected Class<T> entityClass;

    protected Context mContext;
    private int mNumberOfItems = 100;

    public static final int VERSION = DBVersions.CURRENT_VERSION.value();
    public static final String DATABASE_NAME = "vaccinations.db";

    protected DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    protected DBHelper(Context context, Class<T> entityClass){
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
        db.execSQL(ChildTable._CREATE_TABLE);
        db.execSQL(VacinationTable._CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if EXISTS " + ChildTable.TBL_NAME);
        db.execSQL("drop table if EXISTS " + VacinationTable.TBL_NAME);
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