package dvm.service.controller.card;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class CardCheckTest {

    private CardCheck cardCheck;
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

        // CardCheck 인스턴스 생성
        cardCheck = new CardCheck();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // 원래 파일 내용을 복원
        Files.write(Paths.get(CARD_FILE_PATH), originalCardFileContent);
    }

    @Test
    // 카드 번호가 일치하는 경우를 테스트합니다.
    public void testCheckCardNum_ValidCard() {
        assertTrue(cardCheck.checkCardNum("1234567890"));
    }

    @Test
    // 카드 번호가 일치하지 않는 경우를 테스트합니다.
    public void testCheckCardNum_InvalidCard() {
        assertFalse(cardCheck.checkCardNum("1111111111"));
    }

    @Test
    // 잔액이 충분한 경우를 테스트합니다.
    public void testCheckCardBalance_SufficientBalance() {
        assertTrue(cardCheck.checkCardBalance("1234567890", 500));
    }

    @Test
    // 잔액이 부족한 경우를 테스트합니다.
    public void testCheckCardBalance_InsufficientBalance() {
        assertFalse(cardCheck.checkCardBalance("0987654321", 600));
    }
}
