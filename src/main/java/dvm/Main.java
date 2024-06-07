
package dvm;

import UI.MainUI;
import dvm.service.controller.network.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.setVisible(true);

        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
        JsonServer networkManager = new JsonServer(requestFromServiceController);

        // 서버 스레드 실행
        ExecutorService serverExecutor = Executors.newSingleThreadExecutor();
        serverExecutor.execute(networkManager::startServer);


    }
}