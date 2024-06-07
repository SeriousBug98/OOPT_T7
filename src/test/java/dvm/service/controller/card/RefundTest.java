//package dvm.service.controller.card;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RefundTest {
//
//    private Refund refund;
//
//    @BeforeEach
//    public void setUp() {
//        refund = new Refund();
//    }
//
//    @Test
//    public void testProceedRefund_success() {
//        refund.proceedRefund("efgh", 1000);
//        assertEquals(3000, getBalance("efgh"));
//    }
//
//    @Test
//    public void testProceedRefund_anotherCard() {
//        refund.proceedRefund("ijkl", 500);
//        assertEquals(3000, getBalance("ijkl"));
//    }
//
//    @Test
//    public void testProceedRefund_fullAmount() {
//        refund.proceedRefund("mnop", 2000);
//        assertEquals(3000, getBalance("mnop"));
//    }
//
//    private int getBalance(String cardNum) {
//        try (var reader = new BufferedReader(new FileReader("card_info.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] tokens = line.split(",");
//                if (tokens[0].equals(cardNum)) {
//                    return Integer.parseInt(tokens[1]);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return -1; // 카드가 없으면 -1 반환
//    }
//
//}