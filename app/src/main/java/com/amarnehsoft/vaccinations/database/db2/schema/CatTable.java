package com.amarnehsoft.vaccinations.database.db2.schema;

import com.amarnehsoft.vaccinations.database.schema.VacinationTable;

/**
 * Created by alaam on 2/11/2018.
 */

public class CatTable {
    public static final String TBL_NAME = "cat_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.NAME + " VARCHAR ,"
            + Cols.IMG + " VARCHAR "
            + ")";

public static final class Cols
{
    public static final String CODE = "TXT_CODE";
    public static final String NAME = "TXT_NAME";
    public static final String IMG="TXT_IMG";
}
}
