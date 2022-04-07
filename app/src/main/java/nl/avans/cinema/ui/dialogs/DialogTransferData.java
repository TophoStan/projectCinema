package nl.avans.cinema.ui.dialogs;

import nl.avans.cinema.dataacces.api.calls.GenreListResult;

public interface DialogTransferData {
    void onDialogPositiveClick(GenreListResult genres);
    void onDialogPositiveClick(int min, int max);
}
