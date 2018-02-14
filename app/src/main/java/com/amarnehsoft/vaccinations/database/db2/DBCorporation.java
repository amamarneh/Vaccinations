package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.database.db2.schema.CorporationTable;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 2/11/2018.
 */

public class DBCorporation<B extends Corporation, T extends CorporationTable> extends DBHelper2<Corporation>{
    private static DBCorporation instance;
    public static DBCorporation getInstance(Context context) {
        if (instance == null) {
            instance = new DBCorporation(context);
        }
        return instance;
    }

    public DBCorporation(Context context) {
        super(context,Corporation.class);
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

    public List<B> search(String str){

        SQLiteDatabase db = getReadableDatabase();
        List<B> list = new ArrayList<>();
        String selection=T.Cols.NAME + " like '%"+str+"%' ";
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

    public boolean addBean(B bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T.Cols.CODE,bean.getCode());
        contentValues.put(T.Cols.NAME,bean.getName());
        contentValues.put(T.Cols.IMG,bean.getImg());
        contentValues.put(T.Cols.ADDRESS,bean.getAddress());
        contentValues.put(T.Cols.DESC,bean.getDesc());
        contentValues.put(T.Cols.CONTACT,bean.getContact());

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

    private void fillValuesFromBean(B bean, ContentValues contentValues) {
        contentValues.put(T.Cols.CODE,bean.getCode());
        contentValues.put(T.Cols.NAME,bean.getName());
        contentValues.put(T.Cols.IMG,bean.getImg());
        contentValues.put(T.Cols.ADDRESS,bean.getAddress());
        contentValues.put(T.Cols.DESC,bean.getDesc());
        contentValues.put(T.Cols.CONTACT,bean.getContact());
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
    protected void fillBeanFromCursor(Cursor rs, Corporation bean) {
        bean.setCode(rs.getString(rs.getColumnIndex(T.Cols.CODE)));
        bean.setName(rs.getString(rs.getColumnIndex(T.Cols.NAME)));
        bean.setImg(rs.getString(rs.getColumnIndex(T.Cols.IMG)));
        bean.setAddress(rs.getString(rs.getColumnIndex(T.Cols.ADDRESS)));
        bean.setContact(rs.getString(rs.getColumnIndex(T.Cols.CONTACT)));
        bean.setDesc(rs.getString(rs.getColumnIndex(T.Cols.DESC)));
    }

}
