
package dvm.service.controller.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CardCheck {

    private static final String CARD_FILE_PATH = "card_info.txt";

    public boolean checkCardNum(String cardNum) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CARD_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String storedCardNum = tokens[0];

                if (cardNum!= null && cardNum.equals(storedCardNum)) {
                    return true; // 카드 번호가 일치하는 경우
                }
            }
        } catch (IOException e) {
            System.out.println("카드 번호를 확인하는 도중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return false; // 카드 번호가 일치하지 않는 경우
    }

    public boolean checkCardBalance(String cardNum, int price) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CARD_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String storedCardNum = tokens[0];
                int storedBalance = Integer.parseInt(tokens[1]);

                if (cardNum.equals(storedCardNum) && price <= storedBalance) {
                    return true; // 잔액이 충분한 경우
                }
            }
        } catch (IOException e) {
            System.out.println("카드 잔액을 확인하는 도중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return false; // 잔액이 부족한 경우
    }
}
