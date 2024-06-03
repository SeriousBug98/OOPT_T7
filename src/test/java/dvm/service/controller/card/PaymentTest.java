package dvm.service.controller.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = new Payment();
    }

    @Test
    public void testProceedPayment_success() {
        payment.proceedPayment("efgh", 1000);
        assertEquals(1000, getBalance("efgh"));
    }

    @Test
    public void testProceedPayment_insufficientBalance() {
        payment.proceedPayment("abcd", 5000);
        assertEquals(0, getBalance("abcd"));
    }

    @Test
    public void testProceedPayment_partialPayment() {
        payment.proceedPayment("ijkl", 1000);
        assertEquals(1500, getBalance("ijkl"));
    }

    @Test
    public void testProceedPayment_exactPayment() {
        payment.proceedPayment("klmn", 9500);
        assertEquals(0, getBalance("klmn"));
    }

    @Test
    public void testProceedPayment_largeAmount() {
        payment.proceedPayment("qrst", 5000);
        assertEquals(7000, getBalance("qrst"));
    }

    private int getBalance(String cardNum) {
        try (var reader = new BufferedReader(new FileReader("card_info.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens[0].equals(cardNum)) {
                    return Integer.parseInt(tokens[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // 카드가 없으면 -1 반환
    }
}