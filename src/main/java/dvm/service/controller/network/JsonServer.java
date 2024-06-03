package dvm.service.controller.network;

import dvm.domain.network.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JsonServer {

    private int port;

    public JsonServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                JsonSocketServiceImpl service = new JsonSocketServiceImpl(clientSocket);
                service.start();

                service.sendMessage("Hello from server");

                Message msg = service.receiveMessage(Message.class);
                System.out.println("msg = " + msg.msg_type);

                service.stop();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
