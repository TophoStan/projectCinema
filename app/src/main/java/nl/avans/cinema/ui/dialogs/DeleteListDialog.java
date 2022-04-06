package nl.avans.cinema.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.ui.ListsActivity;

public class DeleteListDialog extends DialogFragment {

    private ContentViewModel mContentView;
    private int listId;
    private Context activity;

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setActivity(Context activity) {
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mContentView = new ViewModelProvider(this).get(ContentViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure to delete this list?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mContentView.deleteList(listId, mContentView.getUsers().getAccess_token()).observe(DeleteListDialog.this, deleteListResult -> {
                            if (deleteListResult.isSuccess()) {
                                Toast.makeText(activity, "List has been removed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(activity, ListsActivity.class));
                            } else {
                                Toast.makeText(activity, "List has not been removed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("deleteList", "remove has been cancelled");
                    }
                });
        return builder.create();
    }
}
