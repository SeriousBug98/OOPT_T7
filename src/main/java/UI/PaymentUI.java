package UI;

import javax.swing.*;
import java.awt.*;

public class PaymentUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel paymentSuccessScreen;
    private JPanel paymentFailureScreen;

    public PaymentUI(boolean isSuccess, Runnable goToMainMenu, Runnable goToBeverageSelection) {
        setTitle("Team 7 - Distributed Vending Machine - Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        paymentSuccessScreen = createPaymentSuccessScreen(goToMainMenu);
        paymentFailureScreen = createPaymentFailureScreen(goToBeverageSelection);

        mainPanel.add(paymentSuccessScreen, "PaymentSuccessScreen");
        mainPanel.add(paymentFailureScreen, "PaymentFailureScreen");

        add(mainPanel);
        if (isSuccess) {
            cardLayout.show(mainPanel, "PaymentSuccessScreen");
        } else {
            cardLayout.show(mainPanel, "PaymentFailureScreen");
        }
    }

    private JPanel createPaymentSuccessScreen(Runnable goToMainMenu) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("결제 완료", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel1 = new JLabel("<html><center><br>결제가 성공적으로 진행되었습니다.</center></html>", JLabel.CENTER);
        JLabel messageLabel2 = new JLabel("<html><center><br>이용해주셔서 감사합니다.</center></html>", JLabel.CENTER);
        messageLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel1.setBounds(50, 100, 500, 100);
        messageLabel2.setBounds(50, 130, 500, 100);
        panel.add(messageLabel1);
        panel.add(messageLabel2);

        JButton mainMenuButton = new JButton("메인 화면으로 돌아가기");
        styleButton(mainMenuButton);
        mainMenuButton.setBounds(200, 250, 200, 50);
        mainMenuButton.addActionListener(e -> {
            MainUI mainUI = new MainUI();
            mainUI.setVisible(true);
        });
        panel.add(mainMenuButton);

        return panel;
    }

    private JPanel createPaymentFailureScreen(Runnable goToBeverageSelection) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel1 = new JLabel("<html><center>⚠️<br>해당 카드의 잔액이 부족합니다.</center></html>", JLabel.CENTER);
        JLabel messageLabel2 = new JLabel("<html><center><br>음료 선택 화면으로 돌아갑니다.</center></html>", JLabel.CENTER);
        messageLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel1.setBounds(50, 100, 500, 100);
        messageLabel2.setBounds(50, 130, 500, 100);
        panel.add(messageLabel1);
        panel.add(messageLabel2);

        JButton retryButton = new JButton("음료 선택 화면");
        styleButton(retryButton);
        retryButton.setBounds(200, 250, 200, 50);
        retryButton.addActionListener(e -> {
            goToBeverageSelection.run();
            dispose();
        });
        panel.add(retryButton);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0x3B5998));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 1));
    }

    public static void main(String[] args) {
        // Test the PaymentUI with dummy actions
        Runnable goToMainMenu = () -> System.out.println("Go to main menu");
        Runnable goToBeverageSelection = () -> System.out.println("Go to beverage selection");

        SwingUtilities.invokeLater(() -> {
            PaymentUI paymentUI = new PaymentUI(true, goToMainMenu, goToBeverageSelection);
            paymentUI.setVisible(true);
        });
    }
}
