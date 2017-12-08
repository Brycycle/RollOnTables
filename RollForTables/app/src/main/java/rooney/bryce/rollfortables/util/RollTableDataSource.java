package rooney.bryce.rollfortables.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
                                     int numResults) {

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
            values.put(SQLiteHelper.COLUMN_DIE_0, Integer.toString(die[0]));
        }
        else{
            values.put(SQLiteHelper.COLUMN_DIE_0, "");
        }
        if(description != null){
            values.put(SQLiteHelper.COLUMN_DIE_1, Integer.toString(die[1]));
        }
        else{
            values.put(SQLiteHelper.COLUMN_DIE_1, "");
        }
            values.put(SQLiteHelper.COLUMN_NUM_RESULTS, Integer.toString(numResults));


        values.put(SQLiteHelper.COLUMN_RESULTS_LIST, "");
        values.put(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS, "");
        values.put(SQLiteHelper.COLUMN_SOURCE, Integer.toString(3));
        values.put(SQLiteHelper.COLUMN_TAGS, "");

        // Insert ContentValues into row in events table and obtain row ID
        long id = database.insert(SQLiteHelper.TABLE_ROLL_TABLES, null, values);

        RollTable newRollTable = getRollTable(id);
        return newRollTable;
    }

    public RollTable updateRollTable(RollTable rollTable){
        String id_string = "" + rollTable.getId();


        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, rollTable.getTitle());
        if(rollTable.getDescription() != null){
            values.put(SQLiteHelper.COLUMN_DESCRIPTION, rollTable.getDescription());
        }
        else{
            values.put(SQLiteHelper.COLUMN_DESCRIPTION, "");
        }
        values.put(SQLiteHelper.COLUMN_DIE_0, Integer.toString(rollTable.getDie()[0]) );
        values.put(SQLiteHelper.COLUMN_DIE_1, Integer.toString(rollTable.getDie()[1]));
        values.put(SQLiteHelper.COLUMN_NUM_RESULTS, Integer.toString(rollTable.getNumResults()));
        values.put(SQLiteHelper.COLUMN_SOURCE, Integer.toString(rollTable.getSource()));

        if(rollTable.getResultsList() != null){
            values.put(SQLiteHelper.COLUMN_RESULTS_LIST, rollTable.getResultsListJSONString());
        }
        else{
            values.put(SQLiteHelper.COLUMN_RESULTS_LIST, "");
        }

        if(rollTable.getRangesForResults() != null){
            values.put(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS, rollTable.getRangesForResultsJSONString());
        }
        else{
            values.put(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS, "");
        }
        if(rollTable.getTags() != null){
            values.put(SQLiteHelper.COLUMN_TAGS, rollTable.getTagsJSONString());
        }
        else{
            values.put(SQLiteHelper.COLUMN_TAGS, "");
        }

        // Insert ContentValues into row in events table and obtain row ID
        long id = database.update(SQLiteHelper.TABLE_ROLL_TABLES, values, "?=?", new String[] {SQLiteHelper.COLUMN_ID, id_string});



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
    public ArrayList<RollTable> getAllRollTables(@Nullable ArrayList<String> tags) {
        ArrayList<RollTable> rollTables = new ArrayList<>();
        Cursor cursor = database.query(SQLiteHelper.TABLE_ROLL_TABLES, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        // Create RollTable objects for each item in list
        while (!cursor.isAfterLast()) {
            RollTable rollTable = cursorToRollTable(cursor);
            rollTables.add(rollTable);
            cursor.moveToNext();
        }
        cursor.close();

        ArrayList<RollTable> matchedRollTables = new ArrayList<>();
        int numTables = rollTables.size();

        if(tags != null) {
            int numTags = tags.size();
            //TODO filter rollTables List
            for(int i = 0; i < numTables; i++){
                RollTable r = rollTables.get(i);
                List<String> rTags = r.getTags();
                if(rTags != null){
                    if(rTags.containsAll(tags)){
                        matchedRollTables.add(r);
                    }
                }
            }


            return matchedRollTables;
        }
        else{
            return rollTables;
        }

    }

    /*
     * Helper method to convert row data into Event
     */
    private RollTable cursorToRollTable(Cursor cursor) {
        RollTable rollTable = new RollTable();

        if(cursor != null && cursor.moveToFirst()) {
            rollTable.setTitle(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TITLE)));
            rollTable.setDescription(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_DESCRIPTION)));

            int[] die = new int[2];
            die[0] = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_DIE_0));
            die[1] = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_DIE_1));
            rollTable.setDie(die);
            rollTable.setNumResults(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_NUM_RESULTS)));
            rollTable.setSource(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_SOURCE)));
            rollTable.setRangesForResultsFromJSONString(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RANGES_FOR_RESULTS)));
            rollTable.setResultsListFromJSONString(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_RESULTS_LIST)));
            rollTable.setTagsFromJSONString(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TAGS)));
        }
        else{

        }

        return rollTable;
    }

    public void deleteRollTable(RollTable rollTable){
        long id = rollTable.getId();
        String id_string = "" + id;
        database.delete(SQLiteHelper.TABLE_ROLL_TABLES, "? = ?",
                new String[] { SQLiteHelper.COLUMN_ID, id_string});
    }
}
