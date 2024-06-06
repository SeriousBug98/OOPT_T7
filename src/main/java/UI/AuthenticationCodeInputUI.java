package UI;

import dvm.service.controller.AuthenticationPool;
import dvm.service.controller.authenticaiton.AuthenticationCodeFind;

import javax.swing.*;
import java.awt.*;

public class AuthenticationCodeInputUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel codeInputScreen;
    private JPanel invalidCodeScreen;
    private JPanel exceedAttemptsScreen;
    private AuthenticationPool authenticationPool;
    private int attemptsLeft = 3;

    public AuthenticationCodeInputUI() {
        authenticationPool = new AuthenticationPool();

        setTitle("Team 7 - Distributed Vending Machine");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        codeInputScreen = createCodeInputScreen();
        invalidCodeScreen = createInvalidCodeScreen();
        exceedAttemptsScreen = createExceedAttemptsScreen();

        mainPanel.add(codeInputScreen, "CodeInputScreen");
        mainPanel.add(invalidCodeScreen, "InvalidCodeScreen");
        mainPanel.add(exceedAttemptsScreen, "ExceedAttemptsScreen");

        add(mainPanel);
        cardLayout.show(mainPanel, "CodeInputScreen");
    }

    private JPanel createCodeInputScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("인증 코드 입력", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel instructionLabel = new JLabel("인증코드 10자리를 입력해주세요", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        instructionLabel.setBounds(150, 100, 300, 30);
        panel.add(instructionLabel);

        JTextField codeInputField = new JTextField();
        codeInputField.setBounds(150, 150, 300, 30);
        panel.add(codeInputField);

        JLabel attemptsLabel = new JLabel("남은 입력 횟수: 0" + attemptsLeft + "/03", JLabel.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        attemptsLabel.setBounds(150, 200, 300, 30);
        panel.add(attemptsLabel);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(250, 250, 100, 30);
        okButton.addActionListener(e -> {
            String authCode = codeInputField.getText();
            AuthenticationCodeFind codeFinder = (AuthenticationCodeFind) authenticationPool.getController("FIND");
            boolean isValidCode = codeFinder.process(authCode);

            if (isValidCode) {
                updateStock();
                showSuccessScreen();
                dispose();
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    cardLayout.show(mainPanel, "InvalidCodeScreen");
                } else {
                    cardLayout.show(mainPanel, "ExceedAttemptsScreen");
                }
                codeInputField.setText("");
                attemptsLabel.setText("남은 입력 횟수: 0" + attemptsLeft + "/03");
            }
        });
        panel.add(okButton);

        return panel;
    }

    private JPanel createInvalidCodeScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html><br>올바르지 않은 인증코드입니다.<br>다시 입력해주세요.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton retryButton = new JButton("인증 번호 재입력");
        styleButton(retryButton);
        retryButton.setBounds(200, 250, 200, 50);
        retryButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "CodeInputScreen");
        });
        panel.add(retryButton);

        return panel;
    }

    private JPanel createExceedAttemptsScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html><br>입력 가능 횟수를 초과했습니다.<br>메인 화면으로 돌아갑니다.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton retryButton = new JButton("메인 화면으로 돌아가기");
        styleButton(retryButton);
        retryButton.setBounds(200, 250, 200, 50);
        retryButton.addActionListener(e -> {
            goToMainMenu();
            dispose();
        });
        panel.add(retryButton);

        return panel;
    }

    private void showSuccessScreen() {
        JFrame successFrame = new JFrame("음료 제공");
        successFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        successFrame.setSize(600, 400);
        successFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("음료 제공", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html><br>요청하신 음료 제공드립니다.<br>이용해주셔서 감사합니다.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton mainMenuButton = new JButton("메인 화면으로 돌아가기");
        styleButton(mainMenuButton);
        mainMenuButton.setBounds(200, 250, 200, 50);
        mainMenuButton.addActionListener(e -> {
            goToMainMenu();
            successFrame.dispose();
        });
        panel.add(mainMenuButton);

        successFrame.add(panel);
        successFrame.setVisible(true);
    }

    private void updateStock() {
        // 재고 업데이트 로직을 여기에 구현합니다.
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0x3B5998));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 2));
    }

    private void goToMainMenu() {
        MainUI mainUI = new MainUI();
        mainUI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthenticationCodeInputUI authenticationCodeInputUI = new AuthenticationCodeInputUI();
            authenticationCodeInputUI.setVisible(true);
        });
    }
}
