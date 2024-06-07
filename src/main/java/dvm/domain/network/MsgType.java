package dvm.domain.network;

public enum MsgType {
    req_stock,
    resp_stock,
    req_prepay,
    resp_prepay;

    public String toString() {
        return this.name();
    }
}
