package dvm.service.controller.network;

import com.google.gson.Gson;
import dvm.domain.item.ItemRepository;
import dvm.domain.network.Message;
import dvm.domain.network.MsgType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JsonServer {

    private static int port = 18080;
    private static final JsonServer instance = new JsonServer(port);
    public static JsonServer getInstance() {return instance;}

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

                Message message = service.receiveMessage(Message.class);
                System.out.println("Received message msg_type: " + message.msg_type);
                System.out.println("Received message src_id: " + message.src_id);
                System.out.println("Received message dst_id: " + message.dst_id);
                System.out.println("Received message item_code: " + message.msg_content.item_code);
                System.out.println("Received message item_num: " + message.msg_content.item_num);
                System.out.println();

                if (message.msg_type == MsgType.req_stock){
                    RequestFromServiceController requestFromServiceController = new RequestFromServiceController();
                    Message ServerToClientMessage = requestFromServiceController.sendStockRequestFrom(message);
                    service.sendMessage(ServerToClientMessage);

                } else if (message.msg_type == MsgType.req_prepay) {

                }else service.stop();

            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
