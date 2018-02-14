package com.amarnehsoft.vaccinations.database.db2.schema;

/**
 * Created by alaam on 2/11/2018.
 */

public class KindergartenTable {
    public static final String TBL_NAME = "KINDER_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.NAME + " VARCHAR ,"
            + Cols.IMG + " VARCHAR ,"
            + Cols.ADDRESS + " VARCHAR ,"
            + Cols.DESC + " VARCHAR ,"
            + Cols.CONTACT + " VARCHAR ,"
            + Cols.EXTRA + " VARCHAR ,"
            + Cols.FROM_DAY + " INTEGER ,"
            + Cols.FROM_TIME + " VARCHAR ,"
            + Cols.FROM_YEAR + " INTEGER ,"
            + Cols.TO_DAY + " INTEGER ,"
            + Cols.TO_TIME + " VARCHAR ,"
            + Cols.TO_YEAR + " INTEGER "
            + ")";

    public static final class Cols
    {
        public static final String CODE = "TXT_CODE";
        public static final String NAME = "TXT_NAME";
        public static final String IMG="TXT_IMG";
        public static final String ADDRESS="TXT_ADDRESS";
        public static final String DESC="TXT_DESC";
        public static final String CONTACT = "TXT_CONTACT";
        public static final String EXTRA = "EXTRA";

        public static final String FROM_DAY = "FROM_DAY";
        public static final String FROM_TIME = "FROM_TIME";
        public static final String FROM_YEAR = "FROM_YEAR";
        public static final String TO_DAY = "TO_DAY";
        public static final String TO_TIME = "TO_TIME";
        public static final String TO_YEAR = "TO_YEAR";
    }
}
