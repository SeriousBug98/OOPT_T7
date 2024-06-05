package dvm.controller.network;

import com.google.gson.Gson;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;
import dvm.service.controller.network.JsonClient;
import dvm.service.controller.network.JsonServer;
import dvm.service.controller.network.RequestFromServiceController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketSample {
    private static final int PORT = 19090;
    static JsonServer jsonServer = JsonServer.getInstance();
    static JsonClient jsonClient = JsonClient.getInstance();
    public static void main(String[] args) {
        // 서버 스레드 실행
        Thread serverThread = new Thread(() -> {
            jsonServer.startServer();
        });
        serverThread.start();

        // 클라이언트 스레드 실행
        Thread clientThread = new Thread(() -> {
            jsonClient.startClient();
        });
        clientThread.start();

        // 스레드 종료 대기
        try {
            serverThread.join();
            clientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClientRequest(clientSocket);
            }
        }
    }

    private static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // 클라이언트로부터 메시지 수신
            String json = in.readLine();
            Message message = new Gson().fromJson(json, Message.class);
            System.out.println("Received message msg_type: " + message.msg_type);
            System.out.println("Received message src_id: " + message.src_id);
            System.out.println("Received message dst_id: " + message.dst_id);
            System.out.println("Received message item_code: " + message.msg_content.item_code);
            System.out.println("Received message item_num: " + message.msg_content.item_num);
            System.out.println();

            // 응답 메시지 생성 및 전송
            RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
            Message response = requestFromServiceController.sendStockRequestFrom(message);

            String responseJson = new Gson().toJson(response);
            out.println(responseJson);

        } catch (IOException e) {
            System.out.println("Error handling client request: " + e.getMessage());
        }
    }

    private static void startClient() throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", PORT);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 클라이언트가 서버에 메시지 전송
            Message message = new Message(MsgType.req_stock, "team1", "team7", new MsgContent(1, 1));
            String json = new Gson().toJson(message);
            out.write(json);
            out.newLine();
            out.flush();

            // 서버로부터 응답 메시지 수신
            String responseJson = in.readLine();

            Message response = new Gson().fromJson(responseJson, Message.class);
            System.out.println("Send message msg_type: " + response.msg_type);
            System.out.println("Send message src_id: " + response.src_id);
            System.out.println("Send message dst_id: " + response.dst_id);
            System.out.println("Send message item_code: " + response.msg_content.item_code);
            System.out.println("Send message item_num: " + response.msg_content.item_num);
            System.out.println("Send message dvmY: " + response.msg_content.dvmY);
            System.out.println("Send message dvmX: " + response.msg_content.dvmX);
            System.out.println();

        }
    }
}
