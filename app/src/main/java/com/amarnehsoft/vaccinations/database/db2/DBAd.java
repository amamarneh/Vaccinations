package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.amarnehsoft.vaccinations.beans.Ad;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.database.db2.schema.AdTable;
import com.amarnehsoft.vaccinations.database.db2.schema.CatTable;

/**
 * Created by alaam on 2/11/2018.
 */

public class DBAd<B extends Ad,T extends AdTable> extends DBHelper2<Ad>{
    private static DBAd instance;
    public static DBAd getInstance(Context context) {
        if (instance == null) {
            instance = new DBAd(context);
        }
        return instance;
    }

    public DBAd(Context context) {
        super(context,Ad.class);
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

    @Override
    protected String getTableName() {
        return T.TBL_NAME;
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
        contentValues.put(T.Cols.CONTENT,bean.getContent());
        contentValues.put(T.Cols.IMG,bean.getImg());
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
    protected void fillBeanFromCursor(Cursor rs, Ad bean) {
        bean.setCode(rs.getString(rs.getColumnIndex(T.Cols.CODE)));
        bean.setContent(rs.getString(rs.getColumnIndex(T.Cols.CONTENT)));
        bean.setImg(rs.getString(rs.getColumnIndex(T.Cols.IMG)));
    }

}