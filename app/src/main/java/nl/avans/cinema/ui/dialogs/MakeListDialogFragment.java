package nl.avans.cinema.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.calls.MakeListRequest;

public class MakeListDialogFragment extends DialogFragment {
    private NoticeDialogListener listener;
    private TextView listname, listdescription;
    private CheckBox publicbox;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getContext());
        // Inflate and set the layout for the dialog
        final View inflator = linf.inflate(R.layout.dialog_make_list, null);
        // Pass null as the parent view because its going in the dialog layout
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(inflator);

        final EditText et1 = (EditText) inflator.findViewById(R.id.make_list_name);
        final EditText et2 = (EditText) inflator.findViewById(R.id.make_list_description);
        final CheckBox isPublic = (CheckBox) inflator.findViewById(R.id.is_public);
        // Add action buttons
        alert.setPositiveButton(R.string.create_list_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //EditText name = getActivity().findViewById(R.id.make_list_name);
                String nameString = et1.getText().toString();
                String descString = et2.getText().toString();

                MakeListRequest request = new MakeListRequest();
                request.setPublic(isPublic.isChecked());
                request.setName(nameString);
                request.setDescription(descString);
                request.setIso_639_1("en");
                listener.onDialogPositiveClick(request);
            }
        });
        alert.setNegativeButton(R.string.popup_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MakeListDialogFragment.this.getDialog().cancel();
            }
        });
        return alert.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }


    public interface NoticeDialogListener {
        void onDialogPositiveClick(MakeListRequest request);
    }
}
