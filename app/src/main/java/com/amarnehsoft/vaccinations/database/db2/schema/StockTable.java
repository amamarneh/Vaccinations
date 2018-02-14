package com.amarnehsoft.vaccinations.database.db2.schema;

/**
 * Created by alaam on 2/11/2018.
 */

public class StockTable {
    public static final String TBL_NAME = "stock_TBL";

    public static final String _CREATE_TABLE  = "CREATE TABLE IF NOT EXISTS "
            + TBL_NAME
            + " ("
            + Cols.CODE + " VARCHAR ,"
            + Cols.NAME + " VARCHAR ,"
            + Cols.IMG + " VARCHAR ,"
            + Cols.CAT_CODE + " VARCHAR ,"
            + Cols.DESC + " VARCHAR ,"
            + Cols.CORPORATION_CODE + " VARCHAR ,"

            + Cols.PRICE + " real "
            + ")";

    public static final class Cols
    {
        public static final String CODE = "TXT_CODE";
        public static final String NAME = "TXT_NAME";
        public static final String IMG="TXT_IMG";
        public static final String DESC="TXT_DESC";
        public static final String CAT_CODE = "CAT_CODE";

        public static final String CORPORATION_CODE = "CORPORATION_CODE";
        public static final String PRICE = "PRICE";
    }
}
