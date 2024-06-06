package dvm.service.controller.network;

import dvm.domain.authentication.AuthenticationCodeRepository;
import dvm.domain.item.ItemRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RequestToServiceController {

    private String host = "localhost";
    private int port = 8080;
    private int index = -1;
    private List<Message> stockResponses = new ArrayList<>();
    private ItemRepository itemRepository = ItemRepository.getInstance();
    private AuthenticationCodeRepository authenticationCodeRepository = AuthenticationCodeRepository.getInstance();

    public AuthenticationCodeRepository getAuthenticationCodeRepository() {
        return authenticationCodeRepository;
    }

    // 다른 모든 DVM들에게 재고확인 요청을 보냄
    public void sendStockRequest(int item_code, int item_num) {

        // 메시지 생성
        MsgType msgType = MsgType.req_stock;
        MsgContent msgContent = new MsgContent(item_code,item_num); // 아이템 코드랑 item_num은 넘겨받아야됨
        String src_id = "Team7";
        String dst_id = "0";
        Message msg = new Message(msgType,src_id,dst_id,msgContent);

        // port 조정해야됨
        for (int i = 0; i < 9; i++) {
            try (Socket socket = new Socket(host, port+i)) {
                JsonSocketServiceImpl service = new JsonSocketServiceImpl(socket);
                service.start();

                service.sendMessage(msg);
                Message response = service.receiveMessage(Message.class);
                stockResponses.add(response);

                service.stop();
            } catch (Exception e) {
                System.out.println("Client exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // UI한테 넘길 리턴값 조정해야됨
    public Message getNearestDVM(int item_code, int item_num) {

        double minDistance = 999;
        this.index = -1;
        for (int i = 0; i < 9; i++) {
            Message message = stockResponses.get(i);
            MsgContent msgContent = message.getContent();
            int otherDVMItemNum = msgContent.getItem_num();
            int otherDVMItemCode = msgContent.getItem_code();

            if (item_num > otherDVMItemNum || item_code != otherDVMItemCode) continue;

            int dvmX = msgContent.getCoor_x();
            int dvmY = msgContent.getCoor_y();
            double distance = Math.sqrt(dvmX*dvmX + dvmY*dvmY);

            if(minDistance >= distance) {
                minDistance = distance;
                this.index = i;
            }
        }

        // 음료 제공이 가능한 DVM이 없는 경우
        if(this.index==-1) return null;
        else return stockResponses.get(this.index);
    }

    public void sendPrepayRequest(int item_code, int item_num) {

        // 인증코드 생성
        String cert_code = authenticationCodeRepository.createAuthenticationCode();
        

        // 메시지 생성
        MsgType msgType = MsgType.req_prepay;
        MsgContent msgContent = new MsgContent(item_code,item_num); // 아이템 코드랑 item_num은 넘겨받아야됨
        String src_id = "Team7";
        String dst_id = stockResponses.get(this.index).getSrcId();
        Message msg = new Message(msgType,src_id,dst_id,msgContent);
    }
}

