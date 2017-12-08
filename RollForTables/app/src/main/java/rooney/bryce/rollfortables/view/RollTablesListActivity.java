package rooney.bryce.rollfortables.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rooney.bryce.rollfortables.Classes.RollTable;
import rooney.bryce.rollfortables.Common.Constants;
import rooney.bryce.rollfortables.R;
import rooney.bryce.rollfortables.util.RollTableDataSource;
import rooney.bryce.rollfortables.util.RollTablesListAdapter;
import rooney.bryce.rollfortables.util.SQLiteHelper;

public class RollTablesListActivity extends Activity{

    public RollTableDataSource datasource;
    private ArrayList<String> tagsToSearch = new ArrayList<>();
    public Button bRefresh, bCreateNew;
    public Spinner sTags;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_tables_list);

        datasource = RollTableDataSource.getInstance(this);
        datasource.open();
        addInitialRollTables();
        listView = findViewById(R.id.list);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                onListItemClick(i);
//            }
//        });

        sTags = findViewById(R.id.spinnerTags);
        bRefresh = findViewById(R.id.refreshButton);
        bRefresh.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        onRefreshButtonClick();
                    }
                }
        );
        bCreateNew = findViewById(R.id.createButton);
        bCreateNew.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        onCreateRollTableButtonClick();
                    }
                }
        );

        ArrayList<RollTable> rollTableArrayList = new ArrayList<RollTable>();
        //TODO get getAllRollTables working, cursorTORollTable, setAdapter null reference
        rollTableArrayList = datasource.getAllRollTables(null);
        RollTablesListAdapter adapter = new RollTablesListAdapter(this, rollTableArrayList);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        datasource.open();

//        ListView listView =  findViewById(R.id.list);
//        ArrayList<RollTable> rollTableArrayList = new ArrayList<RollTable>();
//        rollTableArrayList = datasource.getAllRollTables(tagsToSearch);
//        RollTablesListAdapter adapter = new RollTablesListAdapter(this, rollTableArrayList);
//        listView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        datasource.close();
    }

    private void onListItemClick(int position) {
        Toast.makeText(this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
//        //pull selected tags from view
//
        // Get ID of selected event based on position in list
        int _id = datasource.getAllRollTables(null).get(position).getId();

        // Redirect to SingleTableActivity
        Intent SingleTableIntent = new Intent(this, SingleRollTableActivity.class);
        SingleTableIntent.putExtra("WHICH_FRAG_TO_START", 0);
        SingleTableIntent.putExtra("ROLLTABLE_ID", _id);
        startActivity(SingleTableIntent);
    }

    private void onCreateRollTableButtonClick(){
        datasource.close();
        Toast.makeText(this, "Create Table clicked", Toast.LENGTH_SHORT).show();

        Intent SingleTableIntent = new Intent(this.getApplicationContext(), SingleRollTableActivity.class);
//        SingleTableIntent.putExtra("WHICH_FRAG_TO_START", 1);
//        SingleTableIntent.putExtra("ROLLTABLE_ID", 0);
        startActivity(SingleTableIntent);
    }

    private void onRefreshButtonClick(){
        Toast.makeText(this, "Refresh clicked", Toast.LENGTH_SHORT).show();

//        ListView listView = (ListView) findViewById(R.id.rollTablesListView);
//        ArrayList<RollTable> rollTableArrayList = new ArrayList<RollTable>();
//        rollTableArrayList = datasource.getAllRollTables(tagsToSearch);
//        RollTablesListAdapter adapter = new RollTablesListAdapter(this, rollTableArrayList);
//        listView.setAdapter(adapter);
    }


    private void addInitialRollTables(){
        int die10[] = {2,10};
        int die12[] = {1,12};

        RollTable r1 = new RollTable();
        r1 = datasource.createRollTable("Test1", "This is the Description", die10, 10);
        ArrayList<String> results = new ArrayList<>();
        for(int i = 1; i <11; i++) {
            results.add("Result " + i);
        }
        ArrayList<Integer> ranges = new ArrayList<>();
        for(int j = 1; j <11; j++){
            ranges.add(j);
        }
        ArrayList<String> tags = new ArrayList<>();
        r1.setResultsList(results);
        r1.setRangesForResults(ranges);
        tags.add(Constants.TAG_CREATION);
        r1.setTags(tags);
//        datasource.updateRollTable(r1);

        RollTable r2 = new RollTable();
        r2 = datasource.createRollTable("Test2", "This is the Description that is longer than the other one by some degree more", die12, 12);
        ArrayList<String> results2 = new ArrayList<>();
        for(int i = 1; i <13; i++) {
            results2.add("Result " + i + ". Look at these results! hopefully this displays properly.");
        }
        ArrayList<Integer> ranges2 = new ArrayList<>();
        for(int j = 1; j <11; j++){
            ranges2.add(j);
        }
        ArrayList<String> tags2 = new ArrayList<>();
        r2.setResultsList(results2);
        r2.setRangesForResults(ranges2);
        tags2.add(Constants.TAG_EFFECT);
        tags2.add(Constants.TAG_EVENT);
        r2.setTags(tags2);
//        datasource.updateRollTable(r2);
    }
}
