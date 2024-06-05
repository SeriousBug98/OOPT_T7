package dvm.service.controller.network;

import dvm.domain.authentication.AuthenticationCodeRepository;
import dvm.domain.item.ItemRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;
import dvm.service.controller.item.ItemCheck;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class RequestFromServiceController {

    private int port = 9090; //일단 임시 8080 (임의 추가)
    private List<Message> messages;
    private AuthenticationCodeRepository authenticationCodeRepository = AuthenticationCodeRepository.getInstance();
    private ItemRepository itemRepository = ItemRepository.getInstance();


    //usecase 8
    //other DVM에게 받은 재고확인 요청에 대한 처리를 하고 응답을 보냄
    //sd와 class diagram 에서 인자 다름 (일단 클다 보고 함)
    public Message sendStockRequestFrom(Message msg){
        int dvmX = 0;
        int dvmY = 0;

        MsgType msgType = msg.msg_type;
        String src_id = "team7"; //우리 DVM의 id
        String dst_id = msg.src_id; //요청이 왔던 DVM의 id
        int itemCode = msg.msg_content.item_code;

        if (msgType != MsgType.req_stock){
            System.out.println("메시지 타입 에러 : 재고 확인 요청 메시지가 아닙니다.");
            return null;
        }

        int itemCount = itemRepository.countItem(itemCode);
        MsgContent msgContent = new MsgContent(itemCode, itemCount, dvmX, dvmY);
        Message message = new Message(MsgType.resp_stock, src_id, dst_id, msgContent);


        
        return message;
    }

    //other DVM에게 선결제 확인 메시지를 보냄
    //SD에 없음...
    public void sendPrepayRequestFrom(Message msg){

    }

    //usecase 12
    //other DVM에서 온 재고 확인 요청을 받음
    public void receiveStockRequestFrom(){
        //메시지 받기 - JsonServer
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                JsonSocketServiceImpl service = new JsonSocketServiceImpl(clientSocket);
                service.start();

                Message msg = service.receiveMessage(Message.class);


                int dvmX = 0;
                int dvmY = 0;

                MsgType msgType = msg.msg_type;
                String src_id = "team7"; //우리 DVM의 id
                String dst_id = msg.src_id; //요청이 왔던 DVM의 id
                int itemCode = msg.msg_content.item_code;

                if (msgType != MsgType.req_stock){
                    System.out.println("메시지 타입 에러 : 재고 확인 요청 메시지가 아닙니다.");
                    return;
                }

                //itemCheck.process 를 쓰면 재고 count 정보를 알 수 없음
                int itemCount = itemRepository.countItem(itemCode);
                MsgContent msgContent = new MsgContent(itemCode, itemCount, dvmX, dvmY);
                Message message = new Message(MsgType.resp_stock, src_id, dst_id, msgContent);

                service.sendMessage(message);

                service.stop();
            }

        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //usecase 12
    //other DVM에서 온 선결제 확인 메시지를 받음
    public void receivePrepayRequestFrom(){
        //메시지 받기 - JsonServer
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                JsonSocketServiceImpl service = new JsonSocketServiceImpl(clientSocket);
                service.start();

                Message msg = service.receiveMessage(Message.class);
                MsgType msgType = msg.msg_type;
                String src_id = "team7"; //우리 DVM의 id
                String dst_id = msg.src_id; //요청이 왔던 DVM의 id
                int itemCode = msg.msg_content.item_code;
                int itemNum = msg.msg_content.item_num;

                boolean availability = true;

                if (msgType != MsgType.req_prepay){
                    System.out.println("메시지 타입 에러 : 재고 확인 요청 메시지가 아닙니다.");
                    return;
                }

                //우리 DVM에서 재고가 부족할때 -> 선결제 불가로 응답
                if (!checkItemNum(itemCode, itemNum)){
                    System.out.println("해당 음료에 대한 재고가 부족합니다.");
                    availability = false;
                }

                //itemCheck.process 로직 중복...

                int itemCount = itemRepository.countItem(itemCode);
                MsgContent msgContent = new MsgContent(itemCode, itemCount, availability);
                Message message = new Message(MsgType.resp_prepay, src_id, dst_id, msgContent);

                service.sendMessage(message);

                service.stop();
            }
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }

    }

    //usecase 12
    //우리 DVM에 있는 음료의 개수를 초과해서 선결제 요청을 보낸다면 false
    //인자 iemCode 추가 (없으면 불가)
    public boolean checkItemNum(int itemCode, int itemNum){
        ItemCheck itemCheck = new ItemCheck();

        if (itemCheck.process(itemCode, itemNum))
            return true;
        else return false;
    }
}
