package dvm.service.controller.network;

import dvm.domain.item.ItemRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;
import dvm.service.controller.authenticaiton.AuthenticationCodeSave;
import dvm.service.controller.item.ItemCheck;

//other DVM에서 요청으로 받은 메시지를 처리해서 응답 메시지를 만듦
public class RequestFromServiceController {
    private ItemRepository itemRepository = ItemRepository.getInstance();
    private AuthenticationCodeSave authenticationCodeSave = new AuthenticationCodeSave();

    //usecase 8 / 12
    //other DVM에게 보낼 재고 확인 응답 메시지 생성
    //8과 12에서 똑같은 일을 하는 것 같아서 12번 receiveStockRequestFrom만 남겼습니다.
    public Message receiveStockRequestFrom(Message msg){
        int dvmX = 0;
        int dvmY = 0;

        MsgType msgType = msg.getType();
        String src_id = "team7"; //우리 DVM의 id
        String dst_id = msg.getSrcId(); //요청이 왔던 DVM의 id
        int itemCode = msg.getContent().getItem_code();

        if (msgType != MsgType.req_stock){
            System.out.println("메시지 타입 에러 : 재고 확인 요청 메시지가 아닙니다.");
            return null;
        }

        //itemCheck.process 를 쓰면 재고 개수를 알 수 없음
        // -> itemRepository 의 countItem 사용했습니다.
        int itemCount = itemRepository.countItem(itemCode);
        MsgContent msgContent = new MsgContent(itemCode, itemCount, dvmX, dvmY);
        Message message = new Message(MsgType.resp_stock, src_id, dst_id, msgContent);

        return message;
    }

    //usecase 12
    //other DVM에게 보낼 선결제 응답 메시지 생성
    public Message receivePrepayRequestFrom(Message msg){
        MsgType msgType = msg.getType();
        String src_id = msg.getDstId(); //우리 DVM의 id
        String dst_id = msg.getSrcId(); //요청이 왔던 DVM의 id
        int itemCode = msg.getContent().getItem_code();
        int itemNum = msg.getContent().getItem_num();
        String cert_code = msg.getContent().getCert_code();

        boolean availability = true;

        if (!msg.getDstId().equals("team7")){
            System.out.println("메시지 수신 에러 : 도착지가 현재 DVM이 아닙니다.");
            return null;
        }
        if (msgType != MsgType.req_prepay){
            System.out.println("메시지 타입 에러 : 재고 확인 요청 메시지가 아닙니다.");
            return null;
        }

        //우리 DVM에서 재고가 부족할때 -> 선결제 불가로 응답
        if (!checkItemNum(itemCode, itemNum)){
            System.out.println("해당 음료에 대한 재고가 부족합니다.");
            availability = false;
        }//재고가 충분하면 재고 감소 시킴
        else{
            itemRepository.updateItemStock(itemCode, 0-itemNum);
        }
        
        //인증코드 저장
        authenticationCodeSave.process(cert_code);

        //checkItemNum 로직 사용 -> itemCheck.process 필요없음. 삭제
        //itemRepository.countItem 이용해서 재고 개수 가져왔습니다.
        int itemCount = itemRepository.countItem(itemCode);
        MsgContent msgContent = new MsgContent(itemCode, itemCount, availability);
        Message message = new Message(MsgType.resp_prepay, src_id, dst_id, msgContent);

        return message;
    }

    //usecase 12
    //선결제 요청받은 개수가 우리 DVM에 있는 음료의 개수를 초과하면 false
    //인자 itemCode 추가 (없으면 불가)
    public boolean checkItemNum(int itemCode, int itemNum){
        ItemCheck itemCheck = new ItemCheck();

        if (itemCheck.process(itemCode, itemNum))
            return true;
        else return false;
    }
}
