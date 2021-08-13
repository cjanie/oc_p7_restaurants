package com.android.go4lunch.write.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

public interface SelectionCommand {

    void toggle(Selection selection);
}
