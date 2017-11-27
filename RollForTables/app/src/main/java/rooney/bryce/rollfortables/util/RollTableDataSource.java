package rooney.bryce.rollfortables.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rooney.bryce.rollfortables.Classes.RollTable;

/**
 * Created by Brycycle on 11/26/2017.
 */

public class RollTableDataSource {
    /**
     * Singleton instance of AgendaDataSource
     */
    private static RollTableDataSource dsInstance = null;

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    /**
     * Array of all column titles in Events table
     */
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_TITLE,
            SQLiteHelper.COLUMN_DESCRIPTION,
            SQLiteHelper.COLUMN_DIE,
            SQLiteHelper.COLUMN_NUM_RESULTS,
            SQLiteHelper.COLUMN_RESULTS_LIST
            SQLiteHelper.COLUMN_RANGES_FOR_RESULTS,
            SQLiteHelper.COLUMN_SOURCE,
            SQLiteHelper.COLUMN_TAGS};

    /**
     * Returns an instance of AgendaDataSource if it exists, otherwise creates
     * a new AgendaDataSource object and returns it
     * @param context
     * The Activity that called this method
     * @return
     * An instance of AgendaDataSource
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
     * Opens the Agenda database for writing
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes the Agenda database
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Creates new row in database and stores all of the event's details. Then creates
     * an Event object from the details stored in the database and returns it.
     * @param title
     * @param location
     * @param start
     * @param end
     * @param details
     * @return
     * Event object that was created
     */
    public RollTable createRollTable(String title, ) {

        // TODO

        // Put keys (row columns) and values (parameters) into ContentValues object
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, title);
        values.put(SQLiteHelper.COLUMN_DESCRIPTION, location);
        values.put(SQLiteHelper.COLUMN_DIE, start);
        values.put(SQLiteHelper.COLUMN_NUM_RESULTS, end);
        values.put(SQLiteHelper.COLUMN_RESULTS_LIST, details);
        values.put(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS, start);
        values.put(SQLiteHelper.COLUMN_SOURCE, end);
        values.put(SQLiteHelper.COLUMN_TAGS, details)

        // Insert ContentValues into row in events table and obtain row ID
        // HINT: database.insert(...) returns the id of the row you insert
        long id = database.insert(SQLiteHelper.TABLE_ROLL_TABLES, null, values);

        // Query database for event row just added using the getEvent(...) method
        // NOTE: You need to write a query to get an event by id at the to-do marker
        //		 in the getEvent(...) method
        RollTable newRollTable = getRollTable(id);

        return newRollTable;
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
        List<RollTable> rollTables = new ArrayList<RollTable>();

        // Query of all events
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
        rollTable.setDie(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_DIE)));
        rollTable.setNumResults(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_NUM_RESULTS)));
        rollTable.setRangesForResults(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS)));
        rollTable.setSource(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_SOURCE)));
        rollTable.setRangesForResults(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS)));
//        rollTable.setTa(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TAGS)));


        return rollTable;
    }

    public void deleteRollTable(RollTable rollTable){
        long id = rollTable.getId();
        String id_string = "" + id;
        database.delete(SQLiteHelper.TABLE_ROLL_TABLES, "? = ?",
                new String[] { SQLiteHelper.COLUMN_ID, id_string});
    }
}
