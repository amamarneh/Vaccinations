package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.db2.schema.CatTable;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;

/**
 * Created by alaam on 2/11/2018.
 */

public class DBCats<B extends Cat,T extends CatTable> extends DBHelper2<Cat>{
    private static DBCats instance;
    public static DBCats getInstance(Context context) {
        if (instance == null) {
            instance = new DBCats(context);
        }
        return instance;
    }

    public DBCats(Context context) {
        super(context,Cat.class);
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

    public boolean addBean(B bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T.Cols.CODE,bean.getCode());
        contentValues.put(T.Cols.NAME,bean.getName());
        contentValues.put(T.Cols.IMG,bean.getImg());

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
    protected void fillBeanFromCursor(Cursor rs, Cat bean) {
        bean.setCode(rs.getString(rs.getColumnIndex(T.Cols.CODE)));
        bean.setName(rs.getString(rs.getColumnIndex(T.Cols.NAME)));
        bean.setImg(rs.getString(rs.getColumnIndex(T.Cols.IMG)));
    }

}
