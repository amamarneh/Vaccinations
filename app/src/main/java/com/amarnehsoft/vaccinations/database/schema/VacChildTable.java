package com.amarnehsoft.vaccinations.database.schema;

/**
 * Created by alaam on 2/12/2018.
 */

public class VacChildTable {
    public static final String TBL_NAME = "vac_child_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.ID + " integer primary key autoincrement ,"
            + Cols.VAC_CODE + " VARCHAR ,"
            + Cols.CHILD_CODE + " VARCHAR ,"
            + Cols.MANUAL_SET + " VARCHAR DEFAULT 0 ,"
            + Cols.TYPE + " VARCHAR ,"
            + Cols.DESC + " VARCHAR ,"
            + Cols.DATE + " integer "
            + ")";

    public static final class Cols
    {
        public static final String ID = "ID";
        public static final String VAC_CODE = "VAC_CODE";
        public static final String CHILD_CODE ="CHILD_CODE";
        public static final String MANUAL_SET ="MANUAL_SET";
        public static final String TYPE ="TYPE";

        public static final String DESC ="DESC";
        public static final String DATE ="DATE";

    }
}
