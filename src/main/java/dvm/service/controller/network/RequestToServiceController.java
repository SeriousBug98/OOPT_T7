package dvm.service.controller.network;

import dvm.domain.authentication.AuthenticationCodeRepository;
import dvm.domain.item.ItemRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;

import java.net.Socket;

public class RequestToServiceController {

    private String host = "localhost";
    private int port = 8080;
    private ItemRepository itemRepository = ItemRepository.getInstance();
    private AuthenticationCodeRepository authenticationCodeRepository = AuthenticationCodeRepository.getInstance();

    // 다른 모든 DVM들에게 재고확인 요청을 보냄
    public void sendStockRequestTo() {

        // 메시지 생성
        MsgType msgType = MsgType.req_stock;
        MsgContent msgContent = new MsgContent(10,10);
        String src_id = "Team7";
        String dst_id = "0";
        Message msg = new Message(msgType,src_id,dst_id,msgContent);

        for (int i = 0; i < 9; i++) {
            try (Socket socket = new Socket(host, port+i)) {
                JsonSocketServiceImpl service = new JsonSocketServiceImpl(socket);
                service.start();

                service.sendMessage(msg);

                String response = service.receiveMessage(String.class);
                System.out.println("response = " + response);

                service.stop();
            } catch (Exception e) {
                System.out.println("Client exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
