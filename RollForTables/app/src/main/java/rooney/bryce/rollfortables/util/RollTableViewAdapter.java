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

public class RollTableViewAdapter extends ArrayAdapter<RollTable>{
    public RollTableViewAdapter(Context context, ArrayList<RollTable> rollTables){
        super(context, 0, rollTables);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RollTable rollTable = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_roll_table_view, parent, false);
        }
        TextView lowRangeText = (TextView) convertView.findViewById(R.id.lowRangeText);
        TextView highRangeText = (TextView) convertView.findViewById(R.id.highRangeText);
        TextView resultsListText = (TextView) convertView.findViewById(R.id.resultsListText);

        int tableSize = rollTable.getResultsList().size();
        int die[] = new int[2]; die = rollTable.getDie();

        if (position == 0) {
            lowRangeText.setText(die[0]);
            highRangeText.setText(rollTable.getRangesForResults().get(position));
        }
        else if (position == tableSize){
            lowRangeText.setText(rollTable.getRangesForResults().get(position-1)+1);
            highRangeText.setText((die[0]*die[1]));
        }
        else {
            lowRangeText.setText(rollTable.getRangesForResults().get(position-1)+1);
            highRangeText.setText(rollTable.getRangesForResults().get(position));
        }


        return convertView;
    }
}
