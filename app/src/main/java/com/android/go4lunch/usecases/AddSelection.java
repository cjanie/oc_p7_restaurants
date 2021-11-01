package com.android.go4lunch.usecases;

import com.android.go4lunch.SelectionRepository;
import com.android.go4lunch.models.Selection;

public class AddSelection {

    private SelectionRepository selectionRepository;

    public AddSelection(SelectionRepository selectionRepository) {
        this.selectionRepository = selectionRepository;
    }

    public void add(Selection selection) {
        this.selectionRepository.add(selection);
    }
}
