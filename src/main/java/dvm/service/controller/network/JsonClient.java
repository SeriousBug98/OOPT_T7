package dvm.service.controller.network;

import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;

import java.net.Socket;

public class JsonClient {

    private String host;
    private int port;
    private static final JsonClient instance = new JsonClient("localhost", 18080);
    //host를 남의 DVM으로
    public static JsonClient getInstance() {return instance;}


    public JsonClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startClient() {
        try (Socket socket = new Socket(host, port)) {
            JsonSocketServiceImpl service = new JsonSocketServiceImpl(socket);
            service.start();

            Message message = new Message(MsgType.req_stock, "team1", "team7", new MsgContent(1, 1));
            service.sendMessage(message);

            Message response = service.receiveMessage(Message.class);
            System.out.println("Send message msg_type: " + response.msg_type);
            System.out.println("Send message src_id: " + response.src_id);
            System.out.println("Send message dst_id: " + response.dst_id);
            System.out.println("Send message item_code: " + response.msg_content.item_code);
            System.out.println("Send message item_num: " + response.msg_content.item_num);
            System.out.println("Send message dvmY: " + response.msg_content.dvmY);
            System.out.println("Send message dvmX: " + response.msg_content.dvmX);
            System.out.println();


        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
