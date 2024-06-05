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
    void sendStockRequestFromTest() {

        Message message = new Message(MsgType.req_prepay, "team1", "team7", new MsgContent(1, 1));

        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
        requestFromServiceController.sendStockRequestFrom(message);

    }

    @Test
    void receiveStockRequestFromTest() {
        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
        requestFromServiceController.receiveStockRequestFrom();

    }

    @Test
    void receivePrepayRequestFromTest() {
        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
        requestFromServiceController.receivePrepayRequestFrom();

    }

    @Test
    void checkItemNumTest() {
        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();

        Assertions.assertTrue(requestFromServiceController.checkItemNum(1, 1));
        Assertions.assertFalse(requestFromServiceController.checkItemNum(1, 11));
    }

}