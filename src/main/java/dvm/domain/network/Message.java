package dvm.domain.network;

public class Message {
    private MsgType type;
    private String srcId;
    private String dstId;
    private MsgContent content;

    public Message(String srcId) {
        this.srcId = srcId;
    }

    public Message(MsgType type, int srcId, int dstId, MsgContent content) {
        this.type = type;
        this.srcId = srcId;
        this.dstId = dstId;
        this.content = content;
    }
}
