package com.amarnehsoft.vaccinations.database.schema;

/**
 * Created by jcc on 8/18/2017.
 */

public class VacinationTable {
    public static final String TBL_NAME = "Vacination_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.NAME + " VARCHAR ,"
            + Cols.DESC + " VARCHAR ,"
            + Cols.AGE + " integer ,"
            + Cols.ARG_NEW_AGE + " integer ,"
            + Cols.TYPE + " integer ,"
            + Cols.ARG_MANUALLY_SET + " integer "
            + ")";

    public static final class Cols
    {
        public static final String CODE = "TXT_CODE";
        public static final String NAME = "TXT_NAME";
        public static final String DESC="TXT_DESC";
        public static final String AGE="INT_AGE";
        public static final String ARG_MANUALLY_SET="BOL_MANUALLY_SET";
        public static final String ARG_NEW_AGE="INT_NEW_AGE";
        public static final String TYPE="INT_TYPE";
    }
}
