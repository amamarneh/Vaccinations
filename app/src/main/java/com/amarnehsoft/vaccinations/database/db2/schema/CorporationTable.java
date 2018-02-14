package com.amarnehsoft.vaccinations.database.db2.schema;

/**
 * Created by alaam on 2/11/2018.
 */

public class CorporationTable {
    public static final String TBL_NAME = "cor_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.NAME + " VARCHAR ,"
            + Cols.IMG + " VARCHAR ,"
            + Cols.ADDRESS + " VARCHAR ,"
            + Cols.DESC + " VARCHAR ,"
            + Cols.CONTACT + " VARCHAR "
            + ")";

    public static final class Cols
    {
        public static final String CODE = "TXT_CODE";
        public static final String NAME = "TXT_NAME";
        public static final String IMG="TXT_IMG";
        public static final String ADDRESS="TXT_ADDRESS";
        public static final String DESC="TXT_DESC";
        public static final String CONTACT = "TXT_CONTACT";
    }
}
