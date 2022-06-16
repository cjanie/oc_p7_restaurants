package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.MyPositionGateway;
import com.android.go4lunch.data.gateways_impl.InMemoryMyPositionGatewayImpl;

import org.junit.Test;

public class SaveMyPositionUseCaseTest {

    @Test
    public void saveMyPositionWhenLocationIsAvailable() {
        MyPositionGateway myPositionGateway = new InMemoryMyPositionGatewayImpl();
        SaveMyPositionUseCase saveMyPositionUseCase = new SaveMyPositionUseCase(myPositionGateway);
        saveMyPositionUseCase.handle(1.1, 1.2);
        assert(myPositionGateway.getMyPosition().getLatitude().equals(1.1));
    }
}
