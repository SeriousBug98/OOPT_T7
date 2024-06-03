package dvm.domain.network;

public class MsgContent {
    private int item_code;
    private int item_num;
    private int coor_x;
    private int coor_y;
    private String cert_code;
    private boolean availability;

    public MsgContent(int item_code, int item_num) {
        this.item_code = item_code;
        this.item_num = item_num;
    }

    public MsgContent(int item_code, int item_num, int coor_x, int coor_y) {
        this.item_code = item_code;
        this.item_num = item_num;
        this.coor_x = coor_x;
        this.coor_y = coor_y;
    }

    public MsgContent(int item_code, int item_num, String cert_code) {
        this.item_code = item_code;
        this.item_num = item_num;
        this.cert_code = cert_code;
    }

    public MsgContent(int item_code, int item_num, boolean availability) {
        this.item_code = item_code;
        this.item_num = item_num;
    }
}
