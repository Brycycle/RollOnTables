package rooney.bryce.rollfortables.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import rooney.bryce.rollfortables.Classes.RollTable;
import rooney.bryce.rollfortables.R;
import rooney.bryce.rollfortables.util.RollTableDataSource;

/**
 * Created by Bryce Rooney on 11/10/2017.
 *
 * Activity for displaying details of a selected table
 */

public class SingleRollTableActivity extends Activity implements CreateRollTableFragment.createTableListener,
        RollTableViewFragment.tableViewListener, EditRollTableDetailsFragment.editTableDetailsListener {


    RollTable currentActivityRollTable;
    private RollTableDataSource datasource;
    CreateRollTableFragment createRollTableFragment = new CreateRollTableFragment();
    RollTableViewFragment rollTableViewFragment = new RollTableViewFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_roll_table);
//        datasource = RollTableDataSource.getInstance(this);
//        datasource.open();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Determine which rollTable was pressed and what fragment to start
//        Bundle extras = getIntent().getExtras();
//        int fragToStart = extras.getInt("WHICH_FRAG_TO_START", 1);
//        int rollTableId = extras.getInt("ROLLTABLE_ID", 0);
//        currentActivityRollTable = datasource.getRollTable(rollTableId);

//        switch (fragToStart){
//            case 0://table view
//                transaction.replace(R.id.FrameSingleRollTable, rollTableViewFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//                rollTableViewFragment.receiveRollTable(currentActivityRollTable);
//                break;
//            case 1://create table
//                transaction.replace(R.id.FrameSingleRollTable, createRollTableFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }

    //From TableView to editTable
    @Override
    public void goToEditTableDetailsView() {
        //pass currentActivityRollTable to editRollTableDetailsFragment and start frag
    }

    //From Edit Details to Edit Entries
    @Override
    public void goToEditTableEntriesView(RollTable r) {
        //update currentActivityRollTable and pass to EditTableEntriesFrag
        //start EditTableEntriesFrag
        currentActivityRollTable.setDescription(r.getDescription());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameSingleRollTable, createRollTableFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //From Create table to edit Entries
    @Override
    public void createTableFromView(String title, @Nullable String description, int numResults, int[] die) {
        //createRollTable call for database and get object as return, set as currentActivityRollTable
        //pass currentActivityRollTable object to editRollTableEntriesFragment
        //start editRollTableEntriesFragment
//        currentActivityRollTable = RollTableDataSource.
//        currentActivityRollTable.setTitle(title);
        if(description != null){
            currentActivityRollTable.setDescription(description);
        }
        currentActivityRollTable.setNumResults(numResults);
        currentActivityRollTable.setDie(die);
    }


}
