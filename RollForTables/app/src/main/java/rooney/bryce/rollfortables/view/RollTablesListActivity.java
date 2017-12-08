package rooney.bryce.rollfortables.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import rooney.bryce.rollfortables.Classes.RollTable;
import rooney.bryce.rollfortables.R;
import rooney.bryce.rollfortables.util.RollTablesListAdapter;

public class RollTablesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_tables_list);

        ArrayList<RollTable> rollTableArrayList = new ArrayList<RollTable>();
        RollTablesListAdapter adapter = new RollTablesListAdapter(this, rollTableArrayList);
        ListView listView = (ListView) findViewById(R.id.rollTablesListView);


    }
}
