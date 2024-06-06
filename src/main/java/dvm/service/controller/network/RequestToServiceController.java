package dvm.service.controller.network;

import dvm.domain.authentication.AuthenticationCodeRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;
import dvm.service.controller.card.CardServiceController;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RequestToServiceController {

    private List<Message> stockResponseMessages = new ArrayList<>();
    private List<Message> availableDVMMessages = new ArrayList<>();

    private AuthenticationCodeRepository authenticationCodeRepository = AuthenticationCodeRepository.getInstance();
    private CardServiceController cardServiceController = new CardServiceController();

    // 다른 모든 DVM들에게 재고확인 요청을 보냄
    public void sendStockRequest(int item_code, int item_num) {

        // 송신할 서버 IP와 Port 설정
        String host = "localhost";
        int port = 8080;

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
                stockResponseMessages.add(response);

                service.stop();
            } catch (Exception e) {
                System.out.println("Client exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean checkAvailableDVM(int item_code, int item_num, String card_num, int price) {

        boolean isChecked = false;
        for (int i = 0; i < 9; i++) {
            Message message = stockResponseMessages.get(i);
            MsgContent msgContent = message.getContent();
            int otherDVMItemNum = msgContent.getItem_num();
            int otherDVMItemCode = msgContent.getItem_code();

            if (item_num > otherDVMItemNum || item_code != otherDVMItemCode) continue;

            availableDVMMessages.add(message);
            isChecked = true;
        }
        if (isChecked == true) cardServiceController.proceedPayment(card_num,price);

        return isChecked;
    }

    // UI한테 넘길 리턴값 조정해야됨
    public int getNearestDVMIndex(int idx) {

        if(idx != -1) availableDVMMessages.remove(idx);

        double minDistance = 999;
        int index = -1;
        for (int i = 0; i < availableDVMMessages.size(); i++) {
            Message message = availableDVMMessages.get(i);
            MsgContent msgContent = message.getContent();

            int dvmX = msgContent.getCoor_x();
            int dvmY = msgContent.getCoor_y();
            double distance = Math.sqrt(dvmX*dvmX + dvmY*dvmY);

            if(minDistance >= distance) {
                minDistance = distance;
                index = i;
            }
        }

        return index;
    }

    public Message sendPrepayRequest(int item_code, int item_num, String card_num, int price) {

        // 송신할 서버의 IP와 포트 설정
        String host = "localhost";
        int port = 8080;

        // 인증코드 생성
        String cert_code = authenticationCodeRepository.createAuthenticationCode();
        authenticationCodeRepository.saveAuthenticationCode(cert_code);

        // 메시지 생성
        MsgType msgType = MsgType.req_prepay;
        MsgContent msgContent = new MsgContent(item_code,item_num,cert_code); // 아이템 코드랑 item_num은 넘겨받아야됨
        String src_id = "Team7";
        int selectIndex = getNearestDVMIndex(-1);
        String dst_id = availableDVMMessages.get(selectIndex).getSrcId();
        Message request = new Message(msgType,src_id,dst_id,msgContent);

        // 메시지 송신
        Message response = null;
        int iterLength = availableDVMMessages.size();
        for (int i = 0; i < iterLength; i++) {

            // port를 request의 dst_id에 맞게 수정하는 작업 필요
            try (Socket socket = new Socket(host, port)) {
                JsonSocketServiceImpl service = new JsonSocketServiceImpl(socket);
                service.start();

                service.sendMessage(request);
                response = service.receiveMessage(Message.class);
                if (response.getContent().isAvailability()) {
                    service.stop();
                    return response;
                } else {
                    selectIndex = getNearestDVMIndex(selectIndex);
                    request.setDstId(availableDVMMessages.get(selectIndex).getSrcId());
                }

            } catch (Exception e) {
                System.out.println("Client exception: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 여기까지 오면 선결제 가능한 DVM이 없다는 뜻이다.
        cardServiceController.proceedRefund(card_num,price);
        return null;
    }

    public void setStockResponseMessages(List<Message> stockResponseMessages) {
        this.stockResponseMessages = stockResponseMessages;
    }

    public List<Message> getStockResponseMessages() {
        return stockResponseMessages;
    }

    public AuthenticationCodeRepository getAuthenticationCodeRepository() {
        return authenticationCodeRepository;
    }

    // availableDVMMessages 리스트를 반환하는 메서드 추가해도되나..
    public List<Message> getAvailableDVMMessages() {
        return availableDVMMessages;
    }
}

