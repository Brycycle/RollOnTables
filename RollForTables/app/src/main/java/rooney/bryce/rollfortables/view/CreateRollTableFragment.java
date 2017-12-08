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
import rooney.bryce.rollfortables.R;
import rooney.bryce.rollfortables.Common.Constants;

/**
 * Created by Bryce Rooney on 11/10/2017.
 */

public class CreateRollTableFragment extends Fragment {

    EditText etTitle, etDescription, etNumResults;
    Spinner sNumDie, sDieType;
    Button bCreate;

    createTableListener activityCommanderCreateTableFrag;

    public interface createTableListener{
        void createTableFromView(String title, @Nullable String description, int numResults,
                                 int[] die);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCommanderCreateTableFrag = (createTableListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_roll_table_view, null);

        etTitle = view.findViewById(R.id.editTextTitle);
        etDescription = view.findViewById(R.id.editTextDiscription);
        etNumResults = view.findViewById(R.id.editTextNumResults);
        sNumDie = view.findViewById(R.id.spinnerDieType);
        sDieType = view.findViewById(R.id.spinnerNumDie);

        bCreate = view.findViewById(R.id.buttonCreate);
        bCreate.setOnClickListener(
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

    public void sendData() {
        String descriptionText = null;

        //Get description text if not null
        if(!TextUtils.isEmpty(etDescription.getText())){
            descriptionText = etDescription.getText().toString();
        }

        //get die values
        int die[] = new int[2];
        die[0] = Integer.parseInt(sNumDie.getSelectedItem().toString());
        die[1] = Integer.parseInt(sDieType.getSelectedItem().toString());

        //submit info to activity
        activityCommanderCreateTableFrag.createTableFromView(
                etTitle.getText().toString(),
                descriptionText,
                Integer.parseInt(etNumResults.getText().toString()),
                die
        );




    }


}
