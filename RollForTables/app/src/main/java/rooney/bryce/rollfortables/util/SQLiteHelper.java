package rooney.bryce.rollfortables.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Brycycle on 11/26/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    // Table name
    public static final String TABLE_ROLL_TABLES = "rollTables";

    // Table columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DIE = "die";
    public static final String COLUMN_NUM_RESULTS = "numResults";
    public static final String COLUMN_RESULTS_LIST = "resultsList";
    public static final String COLUMN_RANGES_FOR_RESULTS = "rangesForResults";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_TAGS = "tags";

    // Database name
    private static final String DATABASE_NAME = "rollTable.db";

    // Increment this number to clear everything in database
    private static final int DATABASE_VERSION = 1;

    /**
     * Returns an instance of this helper object given the activity
     * @param context
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TABLE = "CREATE TABLE events (" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE +" TEXT NOT NULL," +
                COLUMN_DESCRIPTION +"TEXT" +
                COLUMN_DIE +"" +
                COLUMN_NUM_RESULTS +"INTEGER NOT NULL"+
                COLUMN_RESULTS_LIST + "TEXT"+
                COLUMN_RANGES_FOR_RESULTS + ""+
                COLUMN_SOURCE + "INTEGER NOT NULL"+
                COLUMN_TAGS + " )";


        db.execSQL(SQL_CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLL_TABLES);
        onCreate(db);
    }
}
