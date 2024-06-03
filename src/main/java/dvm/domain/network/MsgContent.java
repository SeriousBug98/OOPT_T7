package dvm.domain.network;

public class MsgContent {
    public int item_code;
    public int item_num;
    public int dvmX;
    public int dvmY;
    public String cert_code;
    public boolean availability;

    public MsgContent(int itemCode, int itemNum) {
        this.item_code = itemCode;
        this.item_num = itemNum;
    }

    public MsgContent(int itemCode, int itemNum, int dvmX, int dvmY) {
        this.item_code = itemCode;
        this.item_num = itemNum;
        this.dvmX = dvmX;
        this.dvmY = dvmY;
    }

    public MsgContent(int itemCode, int itemNum, String authenticationCode) {
        this.item_code = itemCode;
        this.item_num = itemNum;
        this.cert_code = authenticationCode;
    }
    public MsgContent(int itemCode, int itemNum, boolean availability) {
        this.item_code = itemCode;
        this.item_num = itemNum;
        this.availability = availability;
    }
}
