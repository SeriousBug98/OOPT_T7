package dvm.service.controller.network;

import dvm.domain.network.Message;

import java.net.Socket;

public class JsonClient {

    private String host;
    private int port;


    public JsonClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startClient() {
        try (Socket socket = new Socket(host, port)) {
            JsonSocketServiceImpl service = new JsonSocketServiceImpl(socket);
            service.start();

            // 서버로 메시지를 보내고 응답을 받습니다.
            service.sendMessage("Client hi");
            Message response = service.receiveMessage(Message.class);
            System.out.println("response = " + response);

            service.stop();
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
