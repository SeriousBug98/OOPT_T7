package dvm.domain.network;

public enum MsgType {
    req_stock{
        public void processMessage(Message message, int item_code, int item_num) {
            // REQ_STOCK에서 item_code, item_num 필드 사용
            message.setContent(new MsgContent(item_code,item_num));
        }
    },
    resp_sotck {
        public void processMessage(Message message, int item_code, int item_num, int coor_x, int coor_y) {
            // RESP_STOCK에서 item_code, item_num, coor_x, coor_y 사용
            message.setContent(new MsgContent(item_code,item_num,coor_x,coor_y));
        }
    },
    req_prepay {
        public void processMessage(Message message, int item_code, int item_num, String cert_code) {
            // REQ_PREPAY에서 item_code, item_num, cert_code 사용
            message.setContent(new MsgContent(item_code,item_num,cert_code));
        }
    },
    resp_prepay {
        public void processMessage(Message message, int item_code, int item_num, boolean avilability) {
            // RESP_PREPAY에서 item_code, item_num, availability 사용
            message.setContent(new MsgContent(item_code,item_num,avilability));
        }
    };
}
