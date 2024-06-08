package dvm.service.controller.card;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardServiceControllerTest {

    private CardServiceController cardServiceController;
    private static final String CARD_FILE_PATH = "card_info.txt";
    private List<String> originalCardFileContent;

    @BeforeEach
    public void setUp() throws IOException {
        // 원래 파일 내용을 저장
        originalCardFileContent = Files.readAllLines(Paths.get(CARD_FILE_PATH));

        // 테스트 데이터를 추가
        try (FileWriter writer = new FileWriter(CARD_FILE_PATH, true)) {
            writer.write("1234567890,1000\n");
            writer.write("0987654321,500\n");
        }

        // CardServiceController 인스턴스 생성
        cardServiceController = new CardServiceController();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // 원래 파일 내용을 복원
        Files.write(Paths.get(CARD_FILE_PATH), originalCardFileContent);
    }

    @Test
    // 유효한 카드 번호를 확인하는 isCardValid 메서드를 테스트합니다.
    public void testIsCardValid_ValidCard() {
        assertTrue(cardServiceController.isCardValid("1234567890"));
    }

    @Test
    // 유효하지 않은 카드 번호를 확인하는 isCardValid 메서드를 테스트합니다.
    public void testIsCardValid_InvalidCard() {
        assertFalse(cardServiceController.isCardValid("1111111111"));
    }

    @Test
    // 잔액이 충분한 경우를 확인하는 isBalanceSufficient 메서드를 테스트합니다.
    public void testIsBalanceSufficient_SufficientBalance() {
        assertTrue(cardServiceController.isBalanceSufficient("1234567890", 500));
    }

    @Test
    // 잔액이 부족한 경우를 확인하는 isBalanceSufficient 메서드를 테스트합니다.
    public void testIsBalanceSufficient_InsufficientBalance() {
        assertFalse(cardServiceController.isBalanceSufficient("0987654321", 600));
    }

    @Test
    // 결제가 성공하는 경우를 테스트합니다.
    public void testProceedPayment_Success() {
        assertTrue(cardServiceController.requestPayment("1234567890", 500));
    }

    @Test
    // 결제가 실패하는 경우를 테스트합니다.
    public void testProceedPayment_Failure() {
        assertFalse(cardServiceController.requestPayment("0987654321", 600));
    }

    @Test
    // 환불이 성공하는 경우를 테스트합니다.
    public void testProceedRefund_Success() throws IOException {
        // 임의의 가격으로 환불 테스트
        cardServiceController.proceedRefund("1234567890", 500);
        // 파일 내용을 읽어서 확인
        List<String> lines = Files.readAllLines(Paths.get(CARD_FILE_PATH));
        assertTrue(lines.stream().anyMatch(line -> line.contains("1234567890") && line.contains("1500")));
    }

    @Test
    // 환불이 실패하는 경우를 테스트합니다.
    public void testProceedRefund_Failure() throws IOException {
        // 임의의 가격으로 환불 테스트
        cardServiceController.proceedRefund("0987654321", 600);
        // 파일 내용을 읽어서 확인
        List<String> lines = Files.readAllLines(Paths.get(CARD_FILE_PATH));
        assertTrue(lines.stream().anyMatch(line -> line.contains("0987654321") && line.contains("500")));
    }
}
