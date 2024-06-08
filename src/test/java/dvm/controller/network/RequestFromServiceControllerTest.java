package dvm.controller.network;

import dvm.domain.item.ItemRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;
import dvm.service.controller.authenticaiton.AuthenticationCodeSave;
import dvm.service.controller.network.RequestFromServiceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestFromServiceControllerTest {

    private RequestFromServiceController requestFromServiceController;
    private ItemRepository itemRepository;
    private AuthenticationCodeSave authenticationCodeSave;

    @BeforeEach
    public void setUp() {
        itemRepository = ItemRepository.getInstance();
        authenticationCodeSave = new AuthenticationCodeSave();
        requestFromServiceController = new RequestFromServiceController();
    }

    @Test
    public void testReceiveStockRequestFrom() {
        // 재고 확인 요청을 처리하는 receiveStockRequestFrom 메서드를 테스트합니다.
        // Given
        MsgContent msgContent = new MsgContent(1, 0, 0, 0);
        Message requestMessage = new Message(MsgType.req_stock, "otherDVM", "Team7", msgContent);

        // Initial stock setup for the test
        itemRepository.updateItemStock(1, 10);

        // When
        Message responseMessage = requestFromServiceController.receiveStockRequestFrom(requestMessage);

        // Then
        assertNotNull(responseMessage);
        assertEquals(MsgType.resp_stock, responseMessage.getType());
        assertEquals("Team7", responseMessage.getSrcId());
        assertEquals("otherDVM", responseMessage.getDstId());
        assertEquals(1, responseMessage.getContent().getItem_code());
    }

    @Test
    public void testReceivePrepayRequestFrom() {
        // 선결제 요청을 처리하는 receivePrepayRequestFrom 메서드를 테스트합니다.
        // Given
        String certCode = "testCode123";
        MsgContent msgContent = new MsgContent(1, 5, certCode);
        Message requestMessage = new Message(MsgType.req_prepay, "otherDVM", "Team7", msgContent);

        // Initial stock setup for the test
        itemRepository.updateItemStock(1, 10);

        // When
        Message responseMessage = requestFromServiceController.receivePrepayRequestFrom(requestMessage);

        // Then
        assertNotNull(responseMessage);
        assertEquals(MsgType.resp_prepay, responseMessage.getType());
        assertEquals("Team7", responseMessage.getSrcId());
        assertEquals("otherDVM", responseMessage.getDstId());
        assertEquals(1, responseMessage.getContent().getItem_code());
//        assertEquals(5, responseMessage.getContent().getItem_num()); // Assuming 5 items were requested

        // Check if stock was updated correctly
//        assertEquals(5, itemRepository.countItem(1));
    }

    @Test
    public void testCheckItemNum() {
        // 선결제 요청받은 개수가 우리 DVM에 있는 음료의 개수를 초과하는지 확인하는 checkItemNum 메서드를 테스트합니다.
        // Given
        int itemCode = 1;
        int itemNum = 5;

        // Initial stock setup for the test
        itemRepository.updateItemStock(1, 10);

        // When
        boolean result = requestFromServiceController.checkItemNum(itemCode, itemNum);

        // Then
        assertTrue(result);

        // When stock is insufficient
        itemNum = 15;
        result = requestFromServiceController.checkItemNum(itemCode, itemNum);

        // Then
        assertFalse(result);
    }
}
