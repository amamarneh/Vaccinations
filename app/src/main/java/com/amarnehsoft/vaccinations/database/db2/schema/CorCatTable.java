package com.amarnehsoft.vaccinations.database.db2.schema;

/**
 * Created by alaam on 2/11/2018.
 */

public class CorCatTable {
    public static final String TBL_NAME = "corcat_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.CATE_CODE + " VARCHAR ,"
            + Cols.CORPORATION_CODE + " VARCHAR "
            + ")";

    public static final class Cols
    {
        public static final String CODE = "TXT_CODE";
        public static final String CATE_CODE = "CATE_CODE";
        public static final String CORPORATION_CODE ="CORPORATION_CODE";
    }
}
