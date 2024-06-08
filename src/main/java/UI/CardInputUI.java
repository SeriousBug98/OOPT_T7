package UI;

import dvm.service.controller.card.CardServiceController;

import javax.swing.*;
import java.awt.*;

public class CardInputUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel cardInputScreen;
    private JPanel invalidCardScreen;
    private JPanel exceedAttemptsScreen;
    private JPanel insufficientBalanceScreen;
    private CardServiceController cardServiceController;
    private int attemptsLeft = 3;
    private int price;

    public CardInputUI(int price, Runnable onSuccess, Runnable onRetry) {
        this.price = price;
        cardServiceController = createCardServiceController();

        setTitle("Team 7 - Distributed Vending Machine");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        cardInputScreen = createCardInputScreen(onSuccess, onRetry);
        invalidCardScreen = createInvalidCardScreen();
        insufficientBalanceScreen = createInsufficientBalanceScreen();
        exceedAttemptsScreen = createExceedAttemptsScreen(onRetry);

        mainPanel.add(cardInputScreen, "CardInputScreen");
        mainPanel.add(invalidCardScreen, "InvalidCardScreen");
        mainPanel.add(insufficientBalanceScreen, "InsufficientBalanceScreen");
        mainPanel.add(exceedAttemptsScreen, "ExceedAttemptsScreen");

        add(mainPanel);
        cardLayout.show(mainPanel, "CardInputScreen");
    }

    protected CardServiceController createCardServiceController() {
        return new CardServiceController();
    }

    private JPanel createCardInputScreen(Runnable onSuccess, Runnable onRetry) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("결제 창", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel instructionLabel = new JLabel("카드 정보 입력", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        instructionLabel.setBounds(150, 100, 300, 30);
        panel.add(instructionLabel);

        JTextField cardInputField = new JTextField();
        cardInputField.setName("cardInputField"); // 이름 설정
        cardInputField.setBounds(150, 150, 300, 30);
        panel.add(cardInputField);

        JLabel attemptsLabel = new JLabel("남은 입력 횟수: 0" + attemptsLeft + "/03", JLabel.CENTER);
        attemptsLabel.setName("attemptsLabel"); // 이름 설정
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        attemptsLabel.setBounds(150, 200, 300, 30);
        panel.add(attemptsLabel);

        JButton okButton = new JButton("OK");
        okButton.setName("okButton"); // 이름 설정
        styleButton(okButton);
        okButton.setBounds(250, 250, 100, 30);
        okButton.addActionListener(e -> {
            String cardNumber = cardInputField.getText();
            boolean isValidPayment = cardServiceController.requestPayment(cardNumber, price);

            if (isValidPayment) {
                onSuccess.run();
                dispose();
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    cardLayout.show(mainPanel, "InvalidCardScreen");
                } else {
                    cardLayout.show(mainPanel, "ExceedAttemptsScreen");
                }
                cardInputField.setText("");
                attemptsLabel.setText("남은 입력 횟수: 0" + attemptsLeft + "/03");
                onRetry.run(); // ensure the retry callback is called
            }
        });
        panel.add(okButton);

        return panel;
    }

    private JPanel createInvalidCardScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel1 = new JLabel("<html><br>유효하지 않은 카드 정보이거나 잔액이 충분하지 않습니다.</html>", JLabel.CENTER);
        JLabel messageLabel2 = new JLabel("<html><br>확인 후 재입력해주세요.</html>", JLabel.CENTER);
        messageLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel1.setBounds(50, 100, 500, 100);
        messageLabel2.setBounds(50, 130, 500, 100);
        panel.add(messageLabel1);
        panel.add(messageLabel2);

        JButton retryButton = new JButton("카드 정보 재입력");
        retryButton.setName("retryButton"); // 이름 설정
        styleButton(retryButton);
        retryButton.setBounds(200, 250, 200, 50);
        retryButton.addActionListener(e -> cardLayout.show(mainPanel, "CardInputScreen"));
        panel.add(retryButton);

        return panel;
    }

    private JPanel createInsufficientBalanceScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html><br>잔액이 부족합니다.<br>다시 시도해주세요.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton retryButton = new JButton("카드 정보 재입력");
        retryButton.setName("retryButton"); // 이름 설정
        styleButton(retryButton);
        retryButton.setBounds(200, 250, 200, 50);
        retryButton.addActionListener(e -> cardLayout.show(mainPanel, "CardInputScreen"));
        panel.add(retryButton);

        return panel;
    }

    private JPanel createExceedAttemptsScreen(Runnable onRetry) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html><br>입력 가능 횟수를 초과했습니다.<br>음료 선택 화면으로 돌아갑니다.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton retryButton = new JButton("음료 선택 화면");
        retryButton.setName("retryButton"); // 이름 설정
        styleButton(retryButton);
        retryButton.setBounds(200, 250, 200, 50);
        retryButton.addActionListener(e -> {
            onRetry.run();
            dispose();
        });
        panel.add(retryButton);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0x3B5998));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 1));
    }
}
