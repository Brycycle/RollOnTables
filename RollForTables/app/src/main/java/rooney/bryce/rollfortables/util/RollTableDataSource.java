package rooney.bryce.rollfortables.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import rooney.bryce.rollfortables.Classes.RollTable;

/**
 * Created by Bryce Rooney on 11/26/2017.
 */

public class RollTableDataSource {
    /**
     * Singleton instance of RollTableDataSource
     */
    private static RollTableDataSource dsInstance = null;

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    /**
     * Array of all column titles in RollTable table
     */
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_TITLE,
            SQLiteHelper.COLUMN_DESCRIPTION,
            SQLiteHelper.COLUMN_DIE_0,
            SQLiteHelper.COLUMN_DIE_1,
            SQLiteHelper.COLUMN_NUM_RESULTS,
            SQLiteHelper.COLUMN_RESULTS_LIST,
            SQLiteHelper.COLUMN_RANGES_FOR_RESULTS,
            SQLiteHelper.COLUMN_SOURCE,
            SQLiteHelper.COLUMN_TAGS};

    /**
     * Returns an instance of RollTableDataSource if it exists, otherwise creates
     * a new RollTableDataSource object and returns it
     * @param context
     * The Activity that called this method
     * @return
     * An instance of RollTableDataSource
     */
    public static RollTableDataSource getInstance(Context context) {
        if (dsInstance == null) {
            dsInstance = new RollTableDataSource(context.getApplicationContext());
        }
        return dsInstance;
    }

    /**
     * Constructor that should never be called by user
     * @param context
     * The Activity that called this method
     */
    private RollTableDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    /**
     * Opens the rollTable database for writing
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes the rollTable database
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Creates new row in database and stores all of the rollTables's details. Then creates
     * a RollTable object from the details stored in the database and returns it.
     */
    public RollTable createRollTable(@Nullable String title, @Nullable String description, @Nullable int[] die,
                                     @Nullable int numResults) {

        // TODO

        // Put keys (row columns) and values (parameters) into ContentValues object
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, title);
        if(description != null){
            values.put(SQLiteHelper.COLUMN_DESCRIPTION, description);
        }
        else{
            values.put(SQLiteHelper.COLUMN_DESCRIPTION, "");
        }
        if(description != null){
            values.put(SQLiteHelper.COLUMN_DIE_0, die[0]);
        }
        else{
            values.put(SQLiteHelper.COLUMN_DIE_0, "");
        }
        if(description != null){
            values.put(SQLiteHelper.COLUMN_DIE_1, die[1]);
        }
        else{
            values.put(SQLiteHelper.COLUMN_DIE_1, "");
        }
        if(description != null){
            values.put(SQLiteHelper.COLUMN_NUM_RESULTS, numResults);
        }
        else{
            values.put(SQLiteHelper.COLUMN_NUM_RESULTS, "");
        }

        //TODO make sure these are put in properly with how they are stored in DB
        values.put(SQLiteHelper.COLUMN_RESULTS_LIST, "");
        values.put(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS, "");
        values.put(SQLiteHelper.COLUMN_SOURCE, 3);
        values.put(SQLiteHelper.COLUMN_TAGS, "");

        // Insert ContentValues into row in events table and obtain row ID
        long id = database.insert(SQLiteHelper.TABLE_ROLL_TABLES, null, values);

        RollTable newRollTable = getRollTable(id);
        return newRollTable;
    }

    public RollTable updateRollTable(RollTable rollTable){
        int id = rollTable.getId();
        //TODO find table in DB with id, enter all values of rollTable into DB, return


        RollTable updatedRollTable = getRollTable(id);
        return updatedRollTable;
    }

    /**
     * Queries and returns event based on ID
     * @param id
     * ID of event to return
     * @return
     * Event with ID "id"
     */
    public RollTable getRollTable(long id) {
        Cursor cursor = null;
        String id_string = "" + id;
        // TODO: Create query for single event here
        cursor = database.query(SQLiteHelper.TABLE_ROLL_TABLES, allColumns, "? = ?",
                new String[] {SQLiteHelper.COLUMN_ID, id_string}, null, null, null);


        cursor.moveToFirst();
        RollTable toReturn = cursorToRollTable(cursor);
        cursor.close();
        return toReturn;
    }

    /**
     * Queries database for all events stored and creates list of Event objects
     * from returned data.
     * @return
     * List of all Event objects in database
     */
    public List<RollTable> getAllRollTables() {
        List<RollTable> rollTables = new ArrayList<>();

        // TODO Query of all events with tags filters
        Cursor cursor = database.query(SQLiteHelper.TABLE_ROLL_TABLES, allColumns, null,
                null, null, null, null);

        cursor.moveToFirst();

        // Create Event objects for each item in list
        while (!cursor.isAfterLast()) {
            RollTable rollTable = cursorToRollTable(cursor);
            rollTables.add(rollTable);
            cursor.moveToNext();
        }

        cursor.close();
        return rollTables;
    }

    /*
     * Helper method to convert row data into Event
     */
    private RollTable cursorToRollTable(Cursor cursor) {
        RollTable rollTable = new RollTable();

        rollTable.setTitle(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TITLE)));
        rollTable.setDescription(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_DESCRIPTION)));

        int[] die = new int[2];
        die[0] = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_DIE_0));
        die[1] = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_DIE_1));
        rollTable.setDie(die);
        rollTable.setNumResults(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_NUM_RESULTS)));
        rollTable.setSource(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_SOURCE)));

        //TODO make sure types match
//        rollTable.setRangesForResults(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS)));
//        rollTable.setRangesForResults(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS)));
//        rollTable.setTags(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TAGS)));


        return rollTable;
    }

    public void deleteRollTable(RollTable rollTable){
        long id = rollTable.getId();
        String id_string = "" + id;
        database.delete(SQLiteHelper.TABLE_ROLL_TABLES, "? = ?",
                new String[] { SQLiteHelper.COLUMN_ID, id_string});
    }
}
