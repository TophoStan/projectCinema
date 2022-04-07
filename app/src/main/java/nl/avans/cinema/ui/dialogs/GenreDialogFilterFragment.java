package nl.avans.cinema.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.calls.GenreListResult;
import nl.avans.cinema.domain.Genre;

public class GenreDialogFilterFragment extends DialogFragment {

    private NoticeDialogListener mListener;
    private GenreListResult mListResult;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater linf = LayoutInflater.from(getContext());
        final View inflator = linf.inflate(R.layout.genre_filter_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(inflator);

        alert.setPositiveButton(R.string.create_list_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                List<Genre> genreList = new ArrayList<>();
                ChipGroup group = (ChipGroup) inflator.findViewById(R.id.chip_group_genre_filter);
                for (int i : group.getCheckedChipIds()) {
                    Chip chip = (Chip) inflator.findViewById(i);
                    for (Genre g : mListResult.getGenres()) {
                        if (chip.getText().toString().equals(g.getName())) {
                            Genre genre = new Genre();
                            genre.setId(g.getId());
                            genre.setName(g.getName());
                            genreList.add(genre);
                        }
                    }
                }
                GenreListResult listResult = new GenreListResult();
                listResult.setGenres(genreList);

                mListener.onDialogPositiveClick(listResult);
                genreList.clear();
            }
        });
        alert.setNegativeButton(R.string.popup_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                GenreDialogFilterFragment.this.getDialog().cancel();
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
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

    public GenreDialogFilterFragment(GenreListResult listResult) {
        this.mListResult = listResult;
    }


    public interface NoticeDialogListener {
        void onDialogPositiveClick(GenreListResult genres);
    }
}
