package dvm.domain.network;

public class Message {
    private MsgType type;
    private int srcId;
    private int dstId;
    private MsgContent content;

    public Message(int srcId) {
        this.srcId = srcId;
    }

    public Message(MsgType type, int srcId, int dstId, MsgContent content) {
        this.type = type;
        this.srcId = srcId;
        this.dstId = dstId;
        this.content = content;
    }
}
