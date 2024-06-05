package dvm.controller.network;

import dvm.service.controller.network.JsonClient;
import dvm.service.controller.network.JsonServer;

public class RequestFromServiceControllerTest {
    private static final int PORT = 9090;
    static JsonServer jsonServer = JsonServer.getInstance();
    static JsonClient jsonClient = JsonClient.getInstance();

    public static void main(String[] args) {
        // 서버 스레드 실행
        Thread serverThread = new Thread(() -> {
            jsonServer.startServer();
        });
        serverThread.start();

        // 첫 번째 클라이언트 스레드 실행
        Thread clientThread = new Thread(() -> {
            jsonClient.startClient();
        });
        clientThread.start();

        // 두 번째 클라이언트 스레드 실행
        Thread clientThread2 = new Thread(() -> {
            jsonClient.startClient2();
        });
        clientThread2.start();

        // 스레드 종료 대기
        try {
            serverThread.join();
            clientThread.join();
            clientThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
