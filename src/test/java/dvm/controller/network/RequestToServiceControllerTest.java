package dvm.controller.network;

import dvm.domain.network.Message;
import dvm.service.controller.network.RequestToServiceController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestToServiceControllerTest {

    @Test
    public void testSendStockRequest() {
        RequestToServiceController controller = new RequestToServiceController();

        // 실제 서버가 동작하고 있어야 합니다.
        int item_code = 1;
        int item_num = 10;
        controller.sendStockRequest(item_code, item_num);

        List<Message> responses = controller.getStockResponseMessages();
        assertNotNull(responses);
        assertFalse(responses.isEmpty());

        for (Message response : responses) {
            assertEquals(item_code, response.getContent().getItem_code());
        }
    }

    @Test
    public void testCheckAvailableDVM() {
        RequestToServiceController controller = new RequestToServiceController();

        // 서버가 이미 동작 중이고, sendStockRequest가 성공했다고 가정합니다.
        int item_code = 1;
        int item_num = 10;
        controller.sendStockRequest(item_code, item_num);

        // 여기서는 card_num과 price를 가정합니다.
        String card_num = "1234-5678-9012-3456";
        int price = 1000;
        boolean available = controller.checkAvailableDVM(item_code, item_num, card_num, price);

        assertTrue(available);
        List<Message> availableMessages = controller.getAvailableDVMMessages();
        assertNotNull(availableMessages);
        assertFalse(availableMessages.isEmpty());
    }

    @Test
    public void testSendPrepayRequest() {
        RequestToServiceController controller = new RequestToServiceController();

        // 서버가 이미 동작 중이고, sendStockRequest와 checkAvailableDVM이 성공했다고 가정합니다.
        int item_code = 1;
        int item_num = 10;
        String card_num = "1234-5678-9012-3456";
        int price = 1000;

        controller.sendStockRequest(item_code, item_num);
        boolean available = controller.checkAvailableDVM(item_code, item_num, card_num, price);
        assertTrue(available);

        Message response = controller.sendPrepayRequest(item_code, item_num, card_num, price);
        assertNotNull(response);
        assertTrue(response.getContent().isAvailability());
    }
}