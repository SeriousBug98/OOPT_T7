package dvm.controller.network;

import com.google.gson.Gson;
import dvm.domain.authentication.AuthenticationCodeRepository;
import dvm.domain.item.ItemRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgContent;
import dvm.domain.network.MsgType;
import dvm.service.controller.authenticaiton.AuthenticationCodeFind;
import dvm.service.controller.network.JsonServer;
import dvm.service.controller.network.RequestFromServiceController;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RequestFromServiceControllerTest {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
        JsonServer networkManager = new JsonServer(requestFromServiceController);

        // 서버 스레드 실행
        ExecutorService serverExecutor = Executors.newSingleThreadExecutor();
        serverExecutor.execute(networkManager::startServer);

        // 클라이언트 스레드 실행
        ExecutorService clientExecutor = Executors.newFixedThreadPool(2);
        clientExecutor.execute(() -> runClient1());

        // client1 종료 대기
        clientExecutor.shutdown();
        try {
            clientExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // client2 실행
        clientExecutor = Executors.newFixedThreadPool(1);
        clientExecutor.execute(() -> runClient2());

        // 스레드 종료 대기
        serverExecutor.shutdown();
        clientExecutor.shutdown();
    }



    private static void runClient1() {
        try (Socket socket = new Socket("43.203.97.72", PORT);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 클라이언트 메시지 생성 및 전송
            Message clientMessage = new Message(MsgType.req_stock, "team1", "team7", new MsgContent("19", "1"));
            Gson gson = new Gson();
            String json = gson.toJson(clientMessage);
            out.write(json);
            out.newLine();
            out.flush();

            // 서버 응답 메시지 수신
            String responseJson = in.readLine();
            Message responseMessage = gson.fromJson(responseJson, Message.class);

            System.out.println("Received response getType: " + responseMessage.getType());
            System.out.println("Received response getDstId: " + responseMessage.getDstId());
            System.out.println("Received response getSrcId: " + responseMessage.getSrcId());
            System.out.println("Received response getItem_code: " + responseMessage.getContent().getItem_code());
            System.out.println("Received response getItem_num: " + responseMessage.getContent().getItem_num());
            System.out.println("Received response getCoor_x: " + responseMessage.getContent().getCoor_x());
            System.out.println("Received response getCoor_y: " + responseMessage.getContent().getCoor_y());
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runClient2() {
        try (Socket socket = new Socket("43.203.97.72", PORT);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 클라이언트 메시지 생성 및 전송
            Message clientMessage = new Message(MsgType.req_prepay, "team1", "team7", new MsgContent("19", "1", "12345"));
            Gson gson = new Gson();
            String json = gson.toJson(clientMessage);
            out.write(json);
            out.newLine();
            out.flush();

            // 서버 응답 메시지 수신
            String responseJson = in.readLine();
            Message responseMessage = gson.fromJson(responseJson, Message.class);


            System.out.println("Received response getType: " + responseMessage.getType());
            System.out.println("Received response getDstId: " + responseMessage.getDstId());
            System.out.println("Received response getSrcId: " + responseMessage.getSrcId());
            System.out.println("Received response getItem_code: " + responseMessage.getContent().getItem_code());
            System.out.println("Received response getItem_num: " + responseMessage.getContent().getItem_num());
            System.out.println("Received response isAvailability: " + responseMessage.getContent().isAvailability());
            System.out.println();

            ItemRepository itemRepository = ItemRepository.getInstance();
            System.out.println(itemRepository.countItem(Integer.parseInt(responseMessage.getContent().getItem_code())));
            Assertions.assertEquals(9, itemRepository.countItem(Integer.parseInt(responseMessage.getContent().getItem_code())));


            AuthenticationCodeFind authenticationCodeFind = new AuthenticationCodeFind();
            System.out.println(authenticationCodeFind.process("12345"));
            Assertions.assertTrue(authenticationCodeFind.process("12345"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
