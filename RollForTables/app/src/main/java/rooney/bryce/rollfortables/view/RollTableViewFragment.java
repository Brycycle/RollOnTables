package rooney.bryce.rollfortables.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rooney.bryce.rollfortables.Classes.RollTable;
import rooney.bryce.rollfortables.R;
import rooney.bryce.rollfortables.Common.Constants;


/**
 * Created by Bryce Rooney on 11/10/2017.
 */

public class RollTableViewFragment extends Fragment {

    public static TextView tvTitle, tvDescription;
    public static ListView lvTableDetails;
    public static Button bRoll, bEdit;
    public RollTable rollTable;

    tableViewListener activityCommanderSingleTableFrag;

    public interface tableViewListener{
        void goToEditTableDetailsView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCommanderSingleTableFrag = (tableViewListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roll_table_view, null);

        tvTitle = view.findViewById(R.id.textViewTitle);
        tvDescription = view.findViewById(R.id.textViewDiscription);
        lvTableDetails = view.findViewById(R.id.listViewDetails);

        bRoll = view.findViewById(R.id.buttonRoll);
        bRoll.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        rollOnTable();
                    }
                }
        );

        bEdit = view.findViewById(R.id.buttonEdit);
        bEdit.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        activityCommanderSingleTableFrag.goToEditTableDetailsView();

                    }
                }
        );



        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void receiveRollTable(RollTable rollTable){
        this.rollTable = rollTable;
    }

    private void rollOnTable(){
        //TODO roll with die values and display toast in current activity
    }
}
