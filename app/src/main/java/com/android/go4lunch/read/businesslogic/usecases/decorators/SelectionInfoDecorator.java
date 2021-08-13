package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;

public abstract class SelectionInfoDecorator<T> {

    protected SelectionQuery selectionQuery;

    public abstract T decor();

}
