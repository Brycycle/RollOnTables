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

public class SingleTableActivity extends Activity implements CreateTableFragment.createTableListener,
        TableViewFragment.tableViewListener {


    RollTable currentRollTable;


    @Override
    public void goToEditTableView() {

    }

    @Override
    public int rollOnTable() {

        return 0;
    }

    @Override
    public void createTableFromView(String title, @Nullable String description, int numResults, int[] die) {

    }
}
