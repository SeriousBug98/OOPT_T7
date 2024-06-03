package dvm.domain.network;

public class Message {
    public MsgType type;
    public int srcId;
    public int dstId;
    public MsgContent content;

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
