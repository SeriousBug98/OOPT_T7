package dvm.service.controller.network;

import dvm.domain.network.Message;

import java.net.Socket;

public class RequestToServiceController {

    private String host = "localhost";
    private int port = 8080;

    public void startRequest() {
        try (Socket socket = new Socket(host, port)) {
            JsonSocketServiceImpl service = new JsonSocketServiceImpl(socket);
            service.start();

            // 서버로 메시지를 보내고 응답을 받습니다.
            Message message = new Message()


            service.sendMessage("Client hi");
            String response = service.receiveMessage(String.class);
            System.out.println("response = " + response);

            service.stop();
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
