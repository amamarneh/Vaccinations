package com.amarnehsoft.vaccinations.database.db2.schema;

/**
 * Created by alaam on 2/11/2018.
 */

public class AdTable {
    public static final String TBL_NAME = "ad_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.CONTENT + " VARCHAR ,"
            + Cols.IMG + " VARCHAR "
            + ")";

    public static final class Cols
    {
        public static final String CODE = "TXT_CODE";
        public static final String CONTENT = "CONTENT";
        public static final String IMG="TXT_IMG";
    }
}
