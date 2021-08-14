package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;

public abstract class SelectionInfoDecorator<T> implements Decorator {

    protected SelectionQuery selectionQuery;

}
