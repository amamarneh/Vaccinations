package com.amarnehsoft.vaccinations.database.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.CorCat;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.database.db2.schema.CatTable;
import com.amarnehsoft.vaccinations.database.db2.schema.CorCatTable;
import com.amarnehsoft.vaccinations.database.db2.schema.CorporationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 2/11/2018.
 */

public class DBCorCat<B extends CorCat,T extends CorCatTable> extends DBHelper2<CorCat> {
    private static DBCorCat instance;
    public static DBCorCat getInstance(Context context) {
        if (instance == null) {
            instance = new DBCorCat(context);
        }
        return instance;
    }

    protected DBCorCat(Context context) {
        super(context);
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

    @Override
    protected void fillBeanFromCursor(Cursor rs, CorCat bean) {
        bean.setCode(rs.getString(rs.getColumnIndex(T.Cols.CODE)));
        bean.setCatCode(rs.getString(rs.getColumnIndex(T.Cols.CATE_CODE)));
        bean.setCorporationCode(rs.getString(rs.getColumnIndex(T.Cols.CORPORATION_CODE)));
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
        contentValues.put(T.Cols.CATE_CODE,bean.getCatCode());
        contentValues.put(T.Cols.CORPORATION_CODE,bean.getCorporationCode());
    }

    public boolean deleteBean(String key){

            //delete
            String selection = T.Cols.CODE + " = ? ";
            String[] selectionArgs = new String[]{key + ""};
            SQLiteDatabase db = getWritableDatabase();
            db.delete(T.TBL_NAME, selection, selectionArgs);
        return true;
    }
    public boolean deleteBean(String catCode,String corCode){

        //delete
        String selection = T.Cols.CATE_CODE + " = ? and " + T.Cols.CORPORATION_CODE + " =?";
        String[] selectionArgs = new String[]{catCode,corCode};
        SQLiteDatabase db = getWritableDatabase();
        db.delete(T.TBL_NAME, selection, selectionArgs);
        return true;
    }
    public boolean addBean(String code, String catCode, String corporationCode){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T.Cols.CODE,code);
        contentValues.put(T.Cols.CATE_CODE,catCode);
        contentValues.put(T.Cols.CORPORATION_CODE,corporationCode);

        return db.insert(T.TBL_NAME, null, contentValues) > 0;
    }

    public List<Corporation> getCorporationsByCat(String catCode, String name, String address){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Corporation> corporations = new ArrayList<>();
        Cursor cursor = null;
        String q = "select * from "+ CorporationTable.TBL_NAME+ " where 1=1 " ;
              if(catCode != null){
                  q = q +   " and " + CorporationTable.Cols.CODE+
                          " in (select " + T.Cols.CORPORATION_CODE +" from " + T.TBL_NAME+
                          " where " + T.Cols.CATE_CODE + " = '" + catCode +"' )";

              }

        if(!TextUtils.isEmpty(name)){
            q = q +
                    " and ( " + CorporationTable.Cols.NAME + " like '%"+ name+"%' or  " + CorporationTable.Cols.ADDRESS + " like'%" + name
                    + "%' )";
        }
        try{
            cursor = db.rawQuery(q
                    ,null);
            if(cursor.moveToFirst()){
                do{
                    Corporation corporation = new Corporation();
                    fillCorporationFromCursor(cursor,corporation);
                    corporations.add(corporation);
                }while (cursor.moveToNext());

                return corporations;
            }else
                return corporations;
        }catch (Exception e){
            e.printStackTrace();
            return corporations;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

    }
    public List<Cat> getCatFromCorporation(String corporationCode){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Cat> cats = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("select * from "+ CatTable.TBL_NAME+
                            " where " + CatTable.Cols.CODE+
                            " in (select " + T.Cols.CATE_CODE +" from " + T.TBL_NAME+
                            " where " + T.Cols.CORPORATION_CODE + " = '" + corporationCode +"')"

                    ,null);
            if(cursor.moveToFirst()){
                do{
                    Cat cat = new Cat();
                    fillCatFromCursor(cursor,cat);
                    cats.add(cat);
                }while (cursor.moveToNext());

                return cats;
            }else
                return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

    }

    protected void fillCorporationFromCursor(Cursor rs, Corporation bean){
        bean.setCode(rs.getString(rs.getColumnIndex(CorporationTable.Cols.CODE)));
        bean.setName(rs.getString(rs.getColumnIndex(CorporationTable.Cols.NAME)));
        bean.setImg(rs.getString(rs.getColumnIndex(CorporationTable.Cols.IMG)));
        bean.setAddress(rs.getString(rs.getColumnIndex(CorporationTable.Cols.ADDRESS)));
        bean.setContact(rs.getString(rs.getColumnIndex(CorporationTable.Cols.CONTACT)));
        bean.setDesc(rs.getString(rs.getColumnIndex(CorporationTable.Cols.DESC)));
    }
    protected void fillCatFromCursor(Cursor rs, Cat bean) {
        bean.setCode(rs.getString(rs.getColumnIndex(CatTable.Cols.CODE)));
        bean.setName(rs.getString(rs.getColumnIndex(CatTable.Cols.NAME)));
        bean.setImg(rs.getString(rs.getColumnIndex(CatTable.Cols.IMG)));
    }
}
