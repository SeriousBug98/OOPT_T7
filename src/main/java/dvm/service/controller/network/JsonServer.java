
package dvm.service.controller.network;

import com.google.gson.Gson;
import dvm.domain.network.Message;
import dvm.domain.network.MsgType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsonServer {
    private static final int PORT = 9090; // 포트 번호 설정
    private static final int MAX_THREADS = 10; // 최대 처리 스레드 수

    private RequestFromServiceController requestFromServiceController;
    private Gson gson; // JSON 변환/파싱을 위한 Gson 객체
    private ExecutorService executorService; // 클라이언트 요청 처리를 위한 스레드 풀

    public JsonServer(RequestFromServiceController requestFromServiceController) {
        this.requestFromServiceController = requestFromServiceController;
        this.gson = new Gson();
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // 클라이언트 요청 처리를 위한 스레드 실행
                System.out.println("excute전 ");

                executorService.execute(() -> handleClientRequest(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            // 클라이언트로부터 메시지 수신
            String json = in.readLine();
            Message receivedMessage = gson.fromJson(json, Message.class);

            System.out.println("call");
            System.out.println("Received Message: " + receivedMessage);
            System.out.println("Received MessageType: " + receivedMessage.toString());




            // RequestFromServiceController의 메시지 처리 메서드 호출
            Message responseMessage = null;
            if (receivedMessage.getType() == MsgType.req_stock) {
                System.out.println("StockRequest!");
                responseMessage =  requestFromServiceController.receiveStockRequestFrom(receivedMessage);

            } else if (receivedMessage.getType() == MsgType.req_prepay) {
                System.out.println("Prepay!");
                responseMessage =  requestFromServiceController.receivePrepayRequestFrom(receivedMessage);
            } else {
                System.out.println("Unsupported message type: " + receivedMessage.getType());
            }

            // 응답 메시지를 JSON 형태로 클라이언트에게 전송
            out.write(gson.toJson(responseMessage));
            out.newLine();
            out.flush();

            System.out.println("Message processed. Waiting for next message...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
