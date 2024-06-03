package dvm.domain.network;

public class Message {
    private MsgType type;
    private String srcId;
    private String dstId;
    private MsgContent content;


    public Message(MsgType type, String srcId, String dstId, MsgContent content) {
        this.type = type;
        this.srcId = srcId;
        this.dstId = dstId;
        this.content = content;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getDstId() {
        return dstId;
    }

    public void setDstId(String dstId) {
        this.dstId = dstId;
    }

    public MsgContent getContent() {
        return content;
    }

    public void setContent(MsgContent content) {
        this.content = content;
    }
}
