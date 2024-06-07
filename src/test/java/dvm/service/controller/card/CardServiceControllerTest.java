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
//class CardServiceControllerTest {
//
//    private CardServiceController controller;
//
//    @BeforeEach
//    public void setUp() {
//        controller = new CardServiceController();
//    }
//
//    @Test
//    public void testProceedPayment_insufficientBalance_abcd() {
//        controller.proceedPayment("abcd", 5000);
//        assertEquals(0, getBalance("abcd"));
//    }
//
//    @Test
//    public void testProceedPayment_success_efgh() {
//        controller.proceedPayment("efgh", 1000);
//        assertEquals(1000, getBalance("efgh"));
//    }
//
//    @Test
//    public void testProceedPayment_insufficientBalance_efgh() {
//        controller.proceedPayment("efgh", 3000);
//        assertEquals(2000, getBalance("efgh"));
//    }
//
//    @Test
//    public void testProceedPayment_success_ijkl() {
//        controller.proceedPayment("ijkl", 1000);
//        assertEquals(1500, getBalance("ijkl"));
//    }
//
//    @Test
//    public void testProceedPayment_insufficientBalance_ijkl() {
//        controller.proceedPayment("ijkl", 3000);
//        assertEquals(2500, getBalance("ijkl"));
//    }
//
//    @Test
//    public void testProceedPayment_success_mnop() {
//        controller.proceedPayment("mnop", 500);
//        assertEquals(500, getBalance("mnop"));
//    }
//
//    @Test
//    public void testProceedPayment_insufficientBalance_mnop() {
//        controller.proceedPayment("mnop", 2000);
//        assertEquals(1000, getBalance("mnop"));
//    }
//
//    @Test
//    public void testProceedPayment_success_qrst() {
//        controller.proceedPayment("qrst", 5000);
//        assertEquals(7000, getBalance("qrst"));
//    }
//
//    @Test
//    public void testProceedPayment_success_uvwx() {
//        controller.proceedPayment("uvwx", 10000);
//        assertEquals(5000, getBalance("uvwx"));
//    }
//
//    @Test
//    public void testProceedPayment_success_yzab() {
//        controller.proceedPayment("yzab", 2000);
//        assertEquals(1000, getBalance("yzab"));
//    }
//
//    @Test
//    public void testProceedPayment_success_cdef() {
//        controller.proceedPayment("cdef", 4000);
//        assertEquals(4000, getBalance("cdef"));
//    }
//
//    @Test
//    public void testProceedPayment_success_ghij() {
//        controller.proceedPayment("ghij", 3000);
//        assertEquals(1000, getBalance("ghij"));
//    }
//
//    @Test
//    public void testProceedPayment_success_klmn() {
//        controller.proceedPayment("klmn", 9500);
//        assertEquals(0, getBalance("klmn"));
//    }
//
//    private int getBalance(String cardNum) {
//        try (BufferedReader reader = new BufferedReader(new FileReader("card_info.txt"))) {
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
//}