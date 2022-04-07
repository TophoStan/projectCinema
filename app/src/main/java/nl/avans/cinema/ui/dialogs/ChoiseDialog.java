package nl.avans.cinema.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.calls.MakeListRequest;
import nl.avans.cinema.databinding.ActivityLoginBinding;
import nl.avans.cinema.databinding.DialogMakeListChoiceBinding;
import nl.avans.cinema.ui.LoginActivity;
import nl.avans.cinema.ui.MainActivity;

public class ChoiseDialog extends DialogFragment {
    private MakeListDialogFragment.NoticeDialogListener listener;
    private DialogMakeListChoiceBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getContext());
        // Inflate and set the layout for the dialog
        final View inflator = linf.inflate(R.layout.dialog_make_list_choice, null);
        // Pass null as the parent view because its going in the dialog layout
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(inflator);
        binding = DialogMakeListChoiceBinding.inflate(getLayoutInflater());


        final Button make = (Button) inflator.findViewById(R.id.make_list);
        final Button add = (Button) inflator.findViewById(R.id.add_shared);

        MakeListDialogFragment makeDialog = new MakeListDialogFragment();
        FragmentManager fragmentManager = getFragmentManager();
        binding.makeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog.show(fragmentManager, "MakeListDialog");
            }
        });

        // Add action button
        alert.setNegativeButton(R.string.popup_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ChoiseDialog.this.getDialog().cancel();
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
            listener = (MakeListDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }


    public interface NoticeDialogListener {
        void onDialogPositiveClick(MakeListRequest request);
    }
}
