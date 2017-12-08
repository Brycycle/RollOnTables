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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import rooney.bryce.rollfortables.Classes.RollTable;
import rooney.bryce.rollfortables.Common.Constants;
import rooney.bryce.rollfortables.R;

/**
 * Created by Bryce Rooney on 12/4/2017.
 */

public class EditRollTableDetailsFragment extends Fragment {
    EditText etTitle, etDescription, etNumResults;
    Spinner sNumDie, sDieType;
    Button bToEditEntries;

    RollTable rollTable;

    editTableDetailsListener activityCommanderEditTableDetailsView;

    public interface editTableDetailsListener{
        void goToEditTableEntriesView(RollTable r);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCommanderEditTableDetailsView = (editTableDetailsListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_roll_table_entries, null);

        etTitle = view.findViewById(R.id.editTextTitle);
        etDescription = view.findViewById(R.id.editTextDiscription);
        etNumResults = view.findViewById(R.id.editTextNumResults);
        sNumDie = view.findViewById(R.id.spinnerDieType);
        sDieType = view.findViewById(R.id.spinnerNumDie);

        bToEditEntries = view.findViewById(R.id.buttonCreate);
        bToEditEntries.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        int lowRange = Integer.parseInt(sNumDie.getSelectedItem().toString());
                        int highRange = Integer.parseInt(sDieType.getSelectedItem().toString());

                        //check for null entries
                        if(TextUtils.isEmpty(etTitle.getText()) || TextUtils.isEmpty(etNumResults.getText()) ||
                                ((sNumDie == null) && (sNumDie.getSelectedItem() == null)) || ((sDieType == null) && sDieType.getSelectedItem() == null)){
                            Toast.makeText(getActivity(), Constants.NULL_ENTRIES , Toast.LENGTH_LONG).show();
                        }
                        //check if numResults is proper with one die roll
                        else if( lowRange == 1 && Integer.parseInt(etNumResults.getText().toString()) >
                                highRange){
                            Toast.makeText(getActivity(), Constants.NUM_RESULTS_TOO_LARGE , Toast.LENGTH_LONG).show();
                        }
                        //check if numResults is proper with more than one die roll
                        else if(Integer.parseInt(etNumResults.getText().toString()) >
                                (highRange*lowRange - lowRange +1)){
                            Toast.makeText(getActivity(), Constants.NUM_RESULTS_TOO_LARGE , Toast.LENGTH_LONG).show();
                        }
                        //send data if all is correct
                        else{
                            sendData();
                        }
                    }
                }
        );

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void receiveRollTable(RollTable rollTable){
        this.rollTable = rollTable;
    }

    public void sendData() {
        rollTable.setTitle(etTitle.getText().toString());
        rollTable.setNumResults(Integer.parseInt(etNumResults.getText().toString()));

        //get die values
        int die[] = new int[2];
        die[0] = Integer.parseInt(sNumDie.getSelectedItem().toString());
        die[1] = Integer.parseInt(sDieType.getSelectedItem().toString());
        rollTable.setDie(die);

        //Get description text if not null
        if (!TextUtils.isEmpty(etDescription.getText())) {
            rollTable.setDescription(etDescription.getText().toString());
        }
        else{
            rollTable.setDescription("");
        }

        //submit info to activity
        activityCommanderEditTableDetailsView.goToEditTableEntriesView(rollTable);
    }
}
