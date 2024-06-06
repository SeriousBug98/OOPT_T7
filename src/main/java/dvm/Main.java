package dvm;

import UI.MainUI;
import dvm.service.controller.network.*;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.setVisible(true);

        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
        RequestToServiceController requestToServiceController = new RequestToServiceController();
        JsonServer networkManager = new JsonServer(requestFromServiceController);

        // 서버 스레드 실행
        ExecutorService serverExecutor = Executors.newSingleThreadExecutor();
        serverExecutor.execute(networkManager::startServer);

        // 클라이언트 스레드 실행
        ExecutorService clientExecutor = Executors.newFixedThreadPool(2);
        clientExecutor.execute(() -> requestToServiceController.sendStockRequest(3, 1));

        // 스레드 종료 대기
        serverExecutor.shutdown();
        clientExecutor.shutdown();

    }
}
