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
        this.availability = availability;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }

    public int getCoor_x() {
        return coor_x;
    }

    public void setCoor_x(int coor_x) {
        this.coor_x = coor_x;
    }

    public int getCoor_y() {
        return coor_y;
    }

    public void setCoor_y(int coor_y) {
        this.coor_y = coor_y;
    }

    public String getCert_code() {
        return cert_code;
    }

    public void setCert_code(String cert_code) {
        this.cert_code = cert_code;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
