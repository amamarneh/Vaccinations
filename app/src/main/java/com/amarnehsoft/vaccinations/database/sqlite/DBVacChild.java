package com.amarnehsoft.vaccinations.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.VacChild;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.db2.DBVaccination;
import com.amarnehsoft.vaccinations.database.schema.ChildTable;
import com.amarnehsoft.vaccinations.database.schema.VacChildTable;
import com.amarnehsoft.vaccinations.database.schema.VacinationTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alaam on 2/12/2018.
 */

public class DBVacChild<B extends VacChild,T extends VacChildTable> extends DBHelper<VacChild> {

    private static DBVacChild instance;
    public static DBVacChild getInstance(Context context){
        if(instance == null){
            instance = new DBVacChild(context);
        }
        return instance;
    }

    private DBVacChild(Context context) {
        super(context,VacChild.class);
    }

    @Override
    public VacChild getBeanById(String id) {
        return null;
    }


    public int saveBean(B bean)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        fillValuesFromBean(bean, values);

        if(this.getBeanById(bean.getId()) != null)
        {
            db.update(T.TBL_NAME, values, T.Cols.ID + " = ?",
                    new String[]{bean.getId()+""});
        }
        else
            db.insert(T.TBL_NAME, null, values);

        return 1;
    }


    public int updateVacChild(String childCode, String vacCode, Date notifyDate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(T.Cols.DATE,notifyDate.getTime());

             return  db.update(T.TBL_NAME, values, T.Cols.CHILD_CODE+ "=? and " + T.Cols.VAC_CODE + " =? ",
                    new String[]{childCode,vacCode});

    }
    public int updateVacChildAndSetManually(String childCode, String vacCode, Date notifyDate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(T.Cols.DATE,notifyDate.getTime());
        values.put(T.Cols.MANUAL_SET,1);

        return  db.update(T.TBL_NAME, values, T.Cols.CHILD_CODE+ "=? and " + T.Cols.VAC_CODE + " =? ",
                new String[]{childCode,vacCode});

    }
    public int addVacChild(B bean)
    {
        Log.d("tag","addVacChild, date="+bean.getDate().toString());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        fillValuesFromBean(bean, values);

        if(this.getBeanByVacChild(bean.getChildCode(),bean.getVacCode()) != null)
        {
            if(bean.getManualSet() !=0)
            updateVacChild(bean.getChildCode(),bean.getVacCode(),bean.getDate());
        }
        else
            db.insert(T.TBL_NAME, null, values);

        return 1;
    }

    public List<B> getAllByChild(String childCode){
        SQLiteDatabase db = getReadableDatabase();
        List<B> list = new ArrayList<>();
        Cursor rs= null;
        try {
            rs = db.rawQuery("SELECT * FROM " + T.TBL_NAME +
                            " where " + T.Cols.CHILD_CODE + " = '" + childCode +"' order by " + T.Cols.DATE
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
    public List<B> getAllNotManualSet(){
        SQLiteDatabase db = getReadableDatabase();
        List<B> list = new ArrayList<>();
        Cursor rs= null;
        try {
            rs = db.query(T.TBL_NAME,null,T.Cols.MANUAL_SET + " = 0",null,null,null,null,null);

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

    @Override
    protected String orderBy() {
        return T.Cols.DATE;
    }
    //    public List<B> getAllVaccinations()
//    {
//        SQLiteDatabase db = getReadableDatabase();
//        List<B> list = new ArrayList<>();
//        String selection=null;
//        String[] args = null;
//
//        Cursor rs = null;
//        try {
//            rs = db.query(getTableName(), null
//                    , selection, args , null, null, orderBy());
//
//            if (rs.moveToFirst())
//            {
//                while (!rs.isAfterLast()) {
//                    B bean = (B)newBean();
//                    fillBeanVaccinationFromCursor(rs, bean);
//                    list.add(bean);
//                    rs.moveToNext();
//                }
//            }
//        }finally {
//            if (rs != null)
//                rs.close();
//        }
//        return list;
//    }
//
//    private void fillBeanVaccinationFromCursor(Cursor rs, B bean) {
//        bean.setId(rs.getInt(rs.getColumnIndex(T.Cols.ID)));
//        bean.setChildCode(rs.getString(rs.getColumnIndex(T.Cols.CHILD_CODE)));
//        bean.setVacCode(rs.getString(rs.getColumnIndex(T.Cols.VAC_CODE)));
//        int set = rs.getInt(rs.getColumnIndex(T.Cols.MANUAL_SET));
//
//
//        int type = rs.getInt(rs.getColumnIndex(T.Cols.TYPE));
//        if(type == 0){
//
//
//            if(set == 0){
//
//                int days = DBVaccination.getInstance(mContext).getBeanById(bean.getVacCode()).getAge();
//                Date date = ChildDB.getInstance(mContext).getBeanById(bean.getChildCode()).getBirthDate();
//
//                date.setTime(date.getTime() + days*24*3600*1000);
//
//                bean.setDate(date);
//
//            }else{
//                bean.setDate(new Date(rs.getLong(rs.getColumnIndex(T.Cols.DATE))));
//            }
//
//
//        }else{
//
//            bean.setDesc( rs.getString(rs.getColumnIndex(T.Cols.DESC)) );
//            bean.setDate(new Date(rs.getLong(rs.getColumnIndex(T.Cols.DATE))));
//        }
//
//        bean.setManualSet(rs.getInt(rs.getColumnIndex(T.Cols.MANUAL_SET)));
//
//
//        bean.setType(rs.getInt(rs.getColumnIndex(T.Cols.TYPE)));
//    }

    public B getBeanById(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        B bean = null;
        Cursor rs = null;
        try {
            rs = db.query(T.TBL_NAME, null
                    , T.Cols.ID + "=?", new String[]{id+""}, null, null, null);
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

    public B getBeanByVacChild(String childCode,String vacCode)
    {
        SQLiteDatabase db = getReadableDatabase();
        B bean = null;
        Cursor rs = null;
        try {
            rs = db.query(T.TBL_NAME, null
                    , T.Cols.CHILD_CODE+ "=? and " + T.Cols.VAC_CODE + " =? ", new String[]{childCode,vacCode}, null, null, null);
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
    protected void fillBeanFromCursor(Cursor rs, VacChild bean) {
        bean.setId(rs.getInt(rs.getColumnIndex(T.Cols.ID)));
        bean.setChildCode(rs.getString(rs.getColumnIndex(T.Cols.CHILD_CODE)));
        bean.setVacCode(rs.getString(rs.getColumnIndex(T.Cols.VAC_CODE)));
        bean.setDesc(rs.getString(rs.getColumnIndex(T.Cols.DESC)));
        bean.setDate(new Date(rs.getLong(rs.getColumnIndex(T.Cols.DATE))));
        bean.setManualSet(rs.getInt(rs.getColumnIndex(T.Cols.MANUAL_SET)));
        bean.setType(rs.getInt(rs.getColumnIndex(T.Cols.TYPE)));

    }

    public int updateBean(B bean)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        fillValuesFromBean(bean, values);
        db.update(T.TBL_NAME, values, T.Cols.ID + " = ?",
                new String[]{bean.getId()+""});

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
        values.put(T.Cols.VAC_CODE, bean.getVacCode());
        values.put(T.Cols.CHILD_CODE,bean.getChildCode());
        values.put(T.Cols.MANUAL_SET,bean.getManualSet());
        values.put(T.Cols.TYPE,bean.getType());
        values.put(T.Cols.DESC,bean.getDesc());
        values.put(T.Cols.DATE,bean.getDate().getTime());

    }

    public boolean deleteBean(int id){
        B person = getBeanById(id);
        if(person != null){
            //delete
            String selection = T.Cols.ID + " = ? ";
            String[] selectionArgs = new String[]{id + ""};
            SQLiteDatabase db = getWritableDatabase();
            db.delete(T.TBL_NAME, selection, selectionArgs);
        }
        return person != null;
    }

    public boolean deleteBeansNotManuallySet(){

            //delete
            String selection = T.Cols.MANUAL_SET + " = ? ";
            String[] selectionArgs = new String[]{"0"};
            SQLiteDatabase db = getWritableDatabase();
            return db.delete(T.TBL_NAME, selection, selectionArgs)>0;

    }


    public void deleteList(List<B> list){
        for (B bean : list){
            deleteBean(bean.getId());
        }
    }

}
