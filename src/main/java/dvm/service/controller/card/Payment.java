package dvm.service.controller.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Payment {

    private static final String CARD_FILE_PATH = "card_info.txt";

    private boolean status=false;

    public void sendPayment(String cardNum, int price) {
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(CARD_FILE_PATH));
            String line;
            int newBalance = -1;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String storedCardNum = tokens[0];
                int storedBalance = Integer.parseInt(tokens[1]);

                if (cardNum.equals(storedCardNum)) {
                    if (storedBalance >= price) {
                        newBalance = storedBalance - price;
                        line = storedCardNum + "," + newBalance;
                        System.out.println("결제가 완료되었습니다. 잔액: " + newBalance);
                    } else {
                        System.out.println("잔액이 부족하여 결제를 완료할 수 없습니다.");
                    }
                }
                lines.add(line);
            }
            reader.close();

            FileWriter writer = new FileWriter(CARD_FILE_PATH);
            for (String l : lines) {
                writer.write(l + System.lineSeparator());
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("잔고를 업데이트하는 도중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
