package dvm.service.controller.network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.out;

public class JsonSocketServiceImpl implements JsonSocketService{

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private final Gson gson = new Gson();

    public JsonSocketServiceImpl(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void start() {
        out.println("Starting JSON socket service...");
        try {
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            this.reader = new BufferedReader((new InputStreamReader(socket.getInputStream(), "UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException("Error initializing streams", e);
        }
    }

    @Override
    public void stop() {
        out.println("Stopping JSON socket service...");
        try {
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException("Error closing streams", e);
        }
    }

    @Override
    public void sendMessage(Object message) {
        writer.println(gson.toJson(message));
        String json = gson.toJson(message);
//        out.println(json);
        out.println("Sent: " + json);
    }

    @Override
    public <T> T receiveMessage(Class<T> clazz) {

        try {
            return gson.fromJson(reader.readLine(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error receiving message", e);
        }
    }
}
