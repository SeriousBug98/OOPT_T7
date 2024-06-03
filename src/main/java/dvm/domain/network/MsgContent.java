package dvm.domain.network;

public class MsgContent {
    public int itemCode;
    public int itemNum;
    public int dvmX;
    public int dvmY;
    public String authenticationCode;
    public boolean availability;

    public MsgContent(int itemCode, int itemNum) {
        this.itemCode = itemCode;
        this.itemNum = itemNum;
    }

    public MsgContent(int itemCode, int itemNum, int dvmX, int dvmY) {
        this.itemCode = itemCode;
        this.itemNum = itemNum;
        this.dvmX = dvmX;
        this.dvmY = dvmY;
    }

    public MsgContent(int itemCode, int itemNum, String authenticationCode) {
        this.itemCode = itemCode;
        this.itemNum = itemNum;
        this.authenticationCode = authenticationCode;
    }
    public MsgContent(int itemCode, int itemNum, boolean availability) {
        this.itemCode = itemCode;
        this.itemNum = itemNum;
        this.availability = availability;
    }
}
