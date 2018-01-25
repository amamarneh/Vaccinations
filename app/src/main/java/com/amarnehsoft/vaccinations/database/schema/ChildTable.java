package com.amarnehsoft.vaccinations.database.schema;

/**
 * Created by jcc on 8/18/2017.
 */

public class ChildTable {
    public static final String TBL_NAME = "Child_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.NAME + " VARCHAR ,"
            + Cols.BIRTH_DATE + " integer "
            + ")";

    public static final class Cols
    {
        public static final String CODE = "TXT_CODE";
        public static final String NAME = "TXT_NAME";
        public static final String BIRTH_DATE="LNG_BIRTH_DATE";
    }
}
