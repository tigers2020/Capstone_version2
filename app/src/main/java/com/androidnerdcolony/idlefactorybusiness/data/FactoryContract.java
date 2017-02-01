package com.androidnerdcolony.idlefactorybusiness.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tiger on 1/29/2017.
 */

public class FactoryContract {

    public static final String CONTENT_AUTHORITY = "com.androidnerdcolony.idlefactorybusiness";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FACTORY = "factory";
    public static final String PATH_USER_STATE = "user_states";

    public static final class FactoryEntry implements BaseColumns {
        public static final Uri CONTENT_FACTORY_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FACTORY).build();
        public static final Uri CONTENT_USER_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USER_STATE).build();

        public static final String FACTORY_TABLE_NAME = PATH_FACTORY;
        public static final String USER_TABLE_NAME =  PATH_USER_STATE;


        public static final String COLUMN_DATE                  = "date";
        public static final String COLUMN_BALANCE               = "balance";
        public static final String COLUMN_EXP                   = "exp";
        public static final String COLUMN_PRESTIGE              = "prestige";
        public static final String COLUMN_TOKEN                 = "token";


        public static final String COLUMN_FACTORY_ID        = "factory_id";
        public static final String COLUMN_FACTORY_LINE_ID   = "factory_line_id";
        public static final String COLUMN_IDLE_CASH         = "idle_cash";
        public static final String COLUMN_LEVEL             = "level";
        public static final String COLUMN_LINE_COST         = "line_cost";
        public static final String COLUMN_OPEN_COST         = "open_cost";
        public static final String COLUMN_WORK_CAPACITY     = "work_capacity";
        public static final String COLUMN_WORKING           = "working";
        public static final String COLUMN_OPEN              = "open";
        public static final String COLUMN_QUALITY           = "quality";
        public static final String COLUMN_TIME              = "time";
        public static final String COLUMN_VALUE             = "value";



    }
}
