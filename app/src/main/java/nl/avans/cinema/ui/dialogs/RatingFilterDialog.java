package nl.avans.cinema.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.calls.GenreListResult;
import nl.avans.cinema.domain.Genre;

public class RatingFilterDialog extends DialogFragment {
    private GenreListResult mListResult;
    private DialogTransferData mListener;

    public RatingFilterDialog(DialogTransferData mListener) {
        this.mListener = mListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater linf = LayoutInflater.from(getContext());
        final View inflator = linf.inflate(R.layout.dialog_rating_filter, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(inflator);

        alert.setPositiveButton(R.string.create_list_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText minField = (EditText) inflator.findViewById(R.id.min_rating_field);
                EditText maxField = (EditText) inflator.findViewById(R.id.max_rating_field);
                mListener.onDialogPositiveClick(Integer.parseInt(minField.getText().toString()), Integer.parseInt(maxField.getText().toString()));

            }
        });
        alert.setNegativeButton(R.string.popup_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                RatingFilterDialog.this.getDialog().cancel();
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
            mListener = (DialogTransferData) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

}
