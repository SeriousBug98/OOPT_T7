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

public class PaymentTest {

    private Payment payment;
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

        // Payment 인스턴스 생성
        payment = new Payment();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // 원래 파일 내용을 복원
        Files.write(Paths.get(CARD_FILE_PATH), originalCardFileContent);
    }

    @Test
    // 결제가 성공하는 경우를 테스트합니다.
    public void testProceedPayment_Success() throws IOException {
        payment.sendPayment("1234567890", 500);

        // 파일 내용을 읽어서 확인
        List<String> lines = Files.readAllLines(Paths.get(CARD_FILE_PATH));
        assertTrue(lines.stream().anyMatch(line -> line.equals("1234567890,500")));
    }

    @Test
    // 잔액이 부족한 경우 결제가 실패하는 것을 테스트합니다.
    public void testProceedPayment_InsufficientBalance() throws IOException {
        payment.sendPayment("0987654321", 600);

        // 파일 내용을 읽어서 확인
        List<String> lines = Files.readAllLines(Paths.get(CARD_FILE_PATH));
        assertTrue(lines.stream().anyMatch(line -> line.equals("0987654321,500")));
    }
}
