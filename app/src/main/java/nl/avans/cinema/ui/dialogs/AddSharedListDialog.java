package nl.avans.cinema.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.ListResult;
import nl.avans.cinema.dataacces.api.calls.MakeListRequest;
import nl.avans.cinema.domain.Movie;

public class AddSharedListDialog extends DialogFragment {
    private AddSharedListDialog.NoticeDialogListener listener;
    private TextView listId;
    private ContentViewModel contentViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getContext());
        // Inflate and set the layout for the dialog
        final View inflator = linf.inflate(R.layout.dialog_add_shared_list, null);
        // Pass null as the parent view because its going in the dialog layout
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(inflator);

        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        final EditText et1 = (EditText) inflator.findViewById(R.id.add_list_code);
        // Add action buttons
        alert.setPositiveButton(R.string.create_list_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText listIdInput = inflator.findViewById(R.id.add_list_code);
                int listId = Integer.valueOf(listIdInput.getText().toString());
                Log.d("listId", listIdInput.getText().toString());
                contentViewModel.getListById(listId).observe(getActivity(), listResult -> {
                    MakeListRequest request = new MakeListRequest();
                    request.setName(listResult.getName());
                    request.setIso_639_1("en");
                    request.setPublic(true);
                    request.setDescription(listResult.getDescription());
                    listener.onDialogPositiveClickShare(request, listResult.getResults());
            });
            }
        });
        alert.setNegativeButton(R.string.popup_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddSharedListDialog.this.getDialog().cancel();
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
            listener = (AddSharedListDialog.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }


    public interface NoticeDialogListener {
        void onDialogPositiveClickShare(MakeListRequest request, List<Movie> movies);
    }
}

