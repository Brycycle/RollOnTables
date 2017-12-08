package rooney.bryce.rollfortables.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import rooney.bryce.rollfortables.Classes.RollTable;

/**
 * Created by Bryce Rooney on 11/10/2017.
 *
 * Activity for displaying details of a selected table
 */

public class SingleRollTableActivity extends Activity implements CreateRollTableFragment.createTableListener,
        RollTableViewFragment.tableViewListener {


    RollTable currentActivityRollTable;


    @Override
    public void goToEditTableDetailsView() {
        //pass currentActivityRollTable to editRollTableDetailsFragment and start frag
    }



    @Override
    public void createTableFromView(String title, @Nullable String description, int numResults, int[] die) {
        //createRollTable call for database and get object as return, set as currentActivityRollTable
        //pass currentActivityRollTable object to editRollTableEntriesFragment
        //start editRollTableEntriesFragment
    }

    public void test(){

    }
}
