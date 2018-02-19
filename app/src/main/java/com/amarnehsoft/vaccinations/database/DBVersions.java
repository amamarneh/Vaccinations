package com.amarnehsoft.vaccinations.database;

/**
 * Created by Amarneh on 12/1/2017.
 */

public class DBVersions {
    public enum Versoin{
        VERSION_0(6),
        VERSOIN_ADD_DATES_TO_AD(7),
        VERSOIN_ADD_SECONDS_TO_AD(8);

        private int version;
        Versoin(int version){
            this.version=version;
        }
        public int value(){return version;}
    }

    public static final Versoin CURRENT_VERSION = Versoin.VERSOIN_ADD_SECONDS_TO_AD;
}
