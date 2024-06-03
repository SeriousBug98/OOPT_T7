package dvm.service.controller.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CardCheck {

    private static final String CARD_FILE_PATH = "card_info.txt";

    public boolean checkCard(String cardNum, int price) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CARD_FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String storedCardNum = tokens[0];
                int storedBalance = Integer.parseInt(tokens[1]);

                if (cardNum.equals(storedCardNum) && price <= storedBalance) {
                    reader.close();
                    return true; // 카드 정보가 일치하고 잔액이 충분한 경우
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("카드 정보를 확인하는 도중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return false; // 카드 정보가 없거나 잔액이 부족한 경우
    }
}
