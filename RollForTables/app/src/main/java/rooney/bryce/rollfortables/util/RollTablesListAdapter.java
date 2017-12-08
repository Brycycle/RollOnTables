package rooney.bryce.rollfortables.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rooney.bryce.rollfortables.Classes.RollTable;
import rooney.bryce.rollfortables.R;

/**
 * Created by Danny on 12/7/2017.
 */

public class RollTablesListAdapter extends ArrayAdapter<RollTable>{
    public RollTablesListAdapter(Context context, ArrayList<RollTable> rollTables){
        super(context, 0, rollTables);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RollTable rollTable = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_roll_tables_list,parent,false);
        }
        TextView tableTitleText = (TextView) convertView.findViewById(R.id.tableTitleText);
        TextView tableDescriptionText = (TextView) convertView.findViewById(R.id.tableDescriptionText);
        TextView tableSourceText = (TextView) convertView.findViewById(R.id.tableSourceText);

        tableTitleText.setText(rollTable.getTitle());
        if(rollTable.getDescription() != null) {
            tableDescriptionText.setText(rollTable.getDescription());
        }
        else{
            tableDescriptionText.setText("");
        }
        tableSourceText.setText(Integer.toString(rollTable.getSource()));

        return convertView;
    }
}
