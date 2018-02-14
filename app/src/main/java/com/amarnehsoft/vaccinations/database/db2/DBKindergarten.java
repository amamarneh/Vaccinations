package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.BaseExpandableListAdapter;

import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.database.db2.schema.KindergartenTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 2/11/2018.
 */

public class DBKindergarten<B extends Kindergarten, T extends KindergartenTable> extends DBHelper2<Kindergarten>{
    private static DBKindergarten instance;
    public static DBKindergarten getInstance(Context context) {
        if (instance == null) {
            instance = new DBKindergarten(context);
        }
        return instance;
    }

    public DBKindergarten(Context context) {
        super(context,Kindergarten.class);
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
    public boolean addBean(B bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T.Cols.CODE,bean.getCode());
        contentValues.put(T.Cols.NAME,bean.getName());
        contentValues.put(T.Cols.IMG,bean.getImgUrl());
        contentValues.put(T.Cols.ADDRESS,bean.getAddress());
        contentValues.put(T.Cols.DESC,bean.getDescription());
        contentValues.put(T.Cols.CONTACT,bean.getContactInfo());

        contentValues.put(T.Cols.FROM_DAY,bean.getFromDay());
        contentValues.put(T.Cols.FROM_TIME,bean.getFromTime());
        contentValues.put(T.Cols.FROM_YEAR,bean.getFromYear());
        contentValues.put(T.Cols.TO_DAY,bean.getToDay());
        contentValues.put(T.Cols.TO_TIME,bean.getToTime());
        contentValues.put(T.Cols.TO_YEAR,bean.getToYear());

        return db.insert(T.TBL_NAME, null, contentValues) > 0;
    }

    public int saveBean(B bean)
    {
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
    public List<B> search(String name){

            SQLiteDatabase db = getReadableDatabase();
            List<B> list = new ArrayList<>();
            String selection=T.Cols.NAME + " like '%"+name+"%' ";
            String[] args = null;

            Cursor rs = null;
            try {
                rs = db.query(getTableName(), null
                        , selection, args , null, null, orderBy());

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

    private void fillValuesFromBean(B bean, ContentValues contentValues) {
        contentValues.put(T.Cols.CODE,bean.getCode());
        contentValues.put(T.Cols.NAME,bean.getName());
        contentValues.put(T.Cols.IMG,bean.getImgUrl());
        contentValues.put(T.Cols.ADDRESS,bean.getAddress());
        contentValues.put(T.Cols.DESC,bean.getDescription());
        contentValues.put(T.Cols.CONTACT,bean.getContactInfo());

        contentValues.put(T.Cols.FROM_DAY,bean.getFromDay());
        contentValues.put(T.Cols.FROM_TIME,bean.getFromTime());
        contentValues.put(T.Cols.FROM_YEAR,bean.getFromYear());
        contentValues.put(T.Cols.TO_DAY,bean.getToDay());
        contentValues.put(T.Cols.TO_TIME,bean.getToTime());
        contentValues.put(T.Cols.TO_YEAR,bean.getToYear());
        contentValues.put(T.Cols.EXTRA,bean.getExtra());
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
    @Override
    protected String getTableName() {
        return T.TBL_NAME;
    }

    @Override
    protected void fillBeanFromCursor(Cursor rs, Kindergarten bean) {
        bean.setCode(rs.getString(rs.getColumnIndex(T.Cols.CODE)));
        bean.setName(rs.getString(rs.getColumnIndex(T.Cols.NAME)));
        bean.setImgUrl(rs.getString(rs.getColumnIndex(T.Cols.IMG)));
        bean.setAddress(rs.getString(rs.getColumnIndex(T.Cols.ADDRESS)));
        bean.setContactInfo(rs.getString(rs.getColumnIndex(T.Cols.CONTACT)));
        bean.setDescription(rs.getString(rs.getColumnIndex(T.Cols.DESC)));

        bean.setFromDay(rs.getInt(rs.getColumnIndex(T.Cols.FROM_DAY)));
        bean.setFromTime(rs.getString(rs.getColumnIndex(T.Cols.FROM_TIME)));
        bean.setFromYear(rs.getInt(rs.getColumnIndex(T.Cols.FROM_YEAR)));
        bean.setToDay(rs.getInt(rs.getColumnIndex(T.Cols.TO_DAY)));
        bean.setToTime(rs.getString(rs.getColumnIndex(T.Cols.TO_TIME)));
        bean.setToYear(rs.getInt(rs.getColumnIndex(T.Cols.TO_YEAR)));
        bean.setExtra(rs.getString(rs.getColumnIndex(T.Cols.EXTRA)));
    }

}
