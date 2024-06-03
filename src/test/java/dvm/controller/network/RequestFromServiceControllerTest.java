package dvm.controller.network;

import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;
import dvm.service.controller.network.JsonServer;
import dvm.service.controller.network.RequestFromServiceController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestFromServiceControllerTest {
    @BeforeEach
    public void beforeEach() {
    }
    @AfterEach
    public void afterEach() {

    }
    @Test
    void process() {
        Message message = new Message(MsgType.REQ_PREPAY, 1, 0, new MsgContent(1, 1));

        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
        requestFromServiceController.sendStockRequestFrom(message);

    }

}