package rooney.bryce.rollfortables.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import rooney.bryce.rollfortables.R;
/**
 * Created by Bryce Rooney on 11/10/2017.
 */

//TODO figure out how to do UI for editing. One display to edit basic details
// and one view to edit table entries?
//should this be one two separate fragments to change between?
public class EditRollTableEntriesFragment extends Fragment {

    EditText etTitle, etDescriptioin;

    editTableListener activityCommanderEditTableView;

    public interface editTableListener{

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCommanderEditTableView = (editTableListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_roll_table_entries, null);




        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
