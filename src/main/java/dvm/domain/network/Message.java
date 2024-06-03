package dvm.domain.network;

public class Message {
    public MsgType msg_type;
    public String src_id;
    public String dst_id;
    public MsgContent msg_content;

    public Message(String srcId) {
        this.src_id = srcId;
    }

    public Message(MsgType type, String srcId, String dstId, MsgContent content) {
        this.msg_type = type;
        this.src_id = srcId;
        this.dst_id = dstId;
        this.msg_content = content;
    }
}
