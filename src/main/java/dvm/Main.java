package dvm;

import UI.MainUI;
import dvm.service.controller.network.JsonServer;
import dvm.service.controller.network.JsonSocketService;
import dvm.service.controller.network.JsonSocketServiceImpl;

import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.setVisible(true);
    }
}
