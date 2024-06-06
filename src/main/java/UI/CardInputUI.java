//package UI;
//
//import dvm.service.controller.card.CardServiceController;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class CardInputUI extends JFrame {
//    private CardLayout cardLayout;
//    private JPanel mainPanel;
//    private JPanel cardInputScreen;
//    private JPanel invalidCardScreen;
//    private JPanel exceedAttemptsScreen;
//    private CardServiceController cardServiceController;
//    private int attemptsLeft = 3;
//    private int price;
//
//    public CardInputUI(int price, Runnable onSuccess, Runnable onInsufficientBalance, Runnable onRetry) {
//        this.price = price;
//        cardServiceController = new CardServiceController();
//
//        setTitle("Team 7 - Distributed Vending Machine - Card Input");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setSize(600, 400);
//        setLocationRelativeTo(null);
//
//        cardLayout = new CardLayout();
//        mainPanel = new JPanel(cardLayout);
//
//        cardInputScreen = createCardInputScreen(onSuccess, onInsufficientBalance, onRetry);
//        invalidCardScreen = createInvalidCardScreen();
//        exceedAttemptsScreen = createExceedAttemptsScreen(onRetry);
//
//        mainPanel.add(cardInputScreen, "CardInputScreen");
//        mainPanel.add(invalidCardScreen, "InvalidCardScreen");
//        mainPanel.add(exceedAttemptsScreen, "ExceedAttemptsScreen");
//
//        add(mainPanel);
//        cardLayout.show(mainPanel, "CardInputScreen");
//    }
//
//    private JPanel createCardInputScreen(Runnable onSuccess, Runnable onInsufficientBalance, Runnable onRetry) {
//        JPanel panel = new JPanel();
//        panel.setLayout(null); // Absolute positioning
//
//        JLabel titleLabel = new JLabel("결제 창", JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setOpaque(true);
//        titleLabel.setBackground(new Color(0x3B5998));
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setBounds(0, 0, 600, 50);
//        panel.add(titleLabel);
//
//        JLabel instructionLabel = new JLabel("카드 정보 입력", JLabel.CENTER);
//        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        instructionLabel.setBounds(150, 100, 300, 30);
//        panel.add(instructionLabel);
//
//        JTextField cardInputField = new JTextField();
//        cardInputField.setBounds(150, 150, 300, 30);
//        panel.add(cardInputField);
//
//        JLabel attemptsLabel = new JLabel("남은 입력 횟수: 0" + attemptsLeft + "/03", JLabel.CENTER);
//        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        attemptsLabel.setBounds(150, 200, 300, 30);
//        panel.add(attemptsLabel);
//
//        JButton okButton = new JButton("OK");
//        styleButton(okButton);
//        okButton.setBounds(250, 250, 100, 30);
//        okButton.addActionListener(e -> {
//            String cardNumber = cardInputField.getText();
//            boolean isValidCard = cardServiceController.proceedPayment(cardNumber, price);
//
//            if (isValidCard) {
//                onSuccess.run();
//                dispose();
//            } else {
//                attemptsLeft--;
//                if (attemptsLeft > 0) {
//                    cardLayout.show(mainPanel, "InvalidCardScreen");
//                } else {
//                    cardLayout.show(mainPanel, "ExceedAttemptsScreen");
//                }
//                cardInputField.setText("");
//                attemptsLabel.setText("남은 입력 횟수: 0" + attemptsLeft + "/03");
//            }
//        });
//        panel.add(okButton);
//
//        return panel;
//    }
//
//    private JPanel createInvalidCardScreen() {
//        JPanel panel = new JPanel();
//        panel.setLayout(null); // Absolute positioning
//
//        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setOpaque(true);
//        titleLabel.setBackground(new Color(0x3B5998));
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setBounds(0, 0, 600, 50);
//        panel.add(titleLabel);
//
//        JLabel messageLabel1 = new JLabel("<html><br>유효하지 않은 카드 정보입니다.</html>", JLabel.CENTER);
//        JLabel messageLabel2 = new JLabel("<html><br>다시 입력해주세요.</html>", JLabel.CENTER);
//        messageLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
//        messageLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
//        messageLabel1.setBounds(50, 100, 500, 100);
//        messageLabel2.setBounds(50, 130, 500, 100);
//        panel.add(messageLabel1);
//        panel.add(messageLabel2);
//
//        JButton retryButton = new JButton("카드 정보 재입력");
//        styleButton(retryButton);
//        retryButton.setBounds(200, 250, 200, 50);
//        retryButton.addActionListener(e -> {
//            cardLayout.show(mainPanel, "CardInputScreen");
//        });
//        panel.add(retryButton);
//
//        return panel;
//    }
//
//    private JPanel createExceedAttemptsScreen(Runnable onRetry) {
//        JPanel panel = new JPanel();
//        panel.setLayout(null); // Absolute positioning
//
//        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setOpaque(true);
//        titleLabel.setBackground(new Color(0x3B5998));
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setBounds(0, 0, 600, 50);
//        panel.add(titleLabel);
//
//        JLabel messageLabel1 = new JLabel("<html><br>입력 가능 횟수를 초과했습니다.</html>", JLabel.CENTER);
//        JLabel messageLabel2 = new JLabel("<html><br><br>음료 선택 화면으로 돌아갑니다.</html>", JLabel.CENTER);
//        messageLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
//        messageLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
//        messageLabel1.setBounds(50, 100, 500, 100);
//        messageLabel2.setBounds(50, 130, 500, 100);
//        panel.add(messageLabel1);
//        panel.add(messageLabel2);
//
//        JButton retryButton = new JButton("음료 선택 화면");
//        styleButton(retryButton);
//        retryButton.setBounds(200, 250, 200, 50);
//        retryButton.addActionListener(e -> {
//            onRetry.run();
//            dispose();
//        });
//        panel.add(retryButton);
//
//        return panel;
//    }
//
//    private void styleButton(JButton button) {
//        button.setFont(new Font("Arial", Font.BOLD, 16));
//        button.setBackground(Color.WHITE);
//        button.setForeground(new Color(0x3B5998));
//        button.setFocusPainted(false);
//        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 2));
//    }
//
////    public static void main(String[] args) {
////        // Test the CardInputUI with dummy actions
////        Runnable onSuccess = () -> {
////            PaymentUI paymentUI = new PaymentUI(true, () -> {}, () -> {});
////            paymentUI.setVisible(true);
////        };
////        Runnable onInsufficientBalance = () -> {
////            PaymentUI paymentUI = new PaymentUI(false, () -> {}, () -> {});
////            paymentUI.setVisible(true);
////        };
////        Runnable onRetry = () -> {
////            ChooseItemUI chooseItemUI = new ChooseItemUI();
////            chooseItemUI.setVisible(true);
////        };
////
////        SwingUtilities.invokeLater(() -> {
////            CardInputUI cardInputUI = new CardInputUI(1000, onSuccess, onInsufficientBalance, onRetry);
////            cardInputUI.setVisible(true);
////        });
////    }
//}

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
    private CardServiceController cardServiceController;
    private int attemptsLeft = 3;
    private int price;

    public CardInputUI(int price, Runnable onSuccess, Runnable onInsufficientBalance, Runnable onRetry) {
        this.price = price;
        cardServiceController = new CardServiceController();

        setTitle("Team 7 - Distributed Vending Machine");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        cardInputScreen = createCardInputScreen(onSuccess, onInsufficientBalance, onRetry);
        invalidCardScreen = createInvalidCardScreen(onRetry);
        exceedAttemptsScreen = createExceedAttemptsScreen(onRetry);

        mainPanel.add(cardInputScreen, "CardInputScreen");
        mainPanel.add(invalidCardScreen, "InvalidCardScreen");
        mainPanel.add(exceedAttemptsScreen, "ExceedAttemptsScreen");

        add(mainPanel);
        cardLayout.show(mainPanel, "CardInputScreen");
    }

    private JPanel createCardInputScreen(Runnable onSuccess, Runnable onInsufficientBalance, Runnable onRetry) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("결제 창", JLabel.LEFT);
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
        cardInputField.setBounds(150, 150, 300, 30);
        panel.add(cardInputField);

        JLabel attemptsLabel = new JLabel("남은 입력 횟수: " + attemptsLeft + "/03", JLabel.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        attemptsLabel.setBounds(150, 200, 300, 30);
        panel.add(attemptsLabel);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(250, 250, 100, 30);
        okButton.addActionListener(e -> {
            String cardNumber = cardInputField.getText();
            boolean isValidCard = cardServiceController.isCardValid(cardNumber);
            //카드 isCardValid 메서드 사용하여 카드번호만 가지고 카드 정보 확인

            if (isValidCard) {
                boolean hasSufficientBalance = cardServiceController.isBalanceSufficient(cardNumber, price);
                //isBalanceSufficient메서드 사용하아 카드 정보와, 가격을 통해 카드 잔액확인
                if (hasSufficientBalance) {
                    cardServiceController.proceedPayment(cardNumber, price);
                    //결제 진행
                    onSuccess.run();
                    dispose();
                } else {
                    onInsufficientBalance.run();
                    dispose();
                }
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    cardLayout.show(mainPanel, "InvalidCardScreen");
                } else {
                    cardLayout.show(mainPanel, "ExceedAttemptsScreen");
                }
                cardInputField.setText("");
                attemptsLabel.setText("남은 입력 횟수: " + attemptsLeft + "/03");
            }
        });
        panel.add(okButton);

        return panel;
    }

    private JPanel createInvalidCardScreen(Runnable onRetry) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("Message", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html>⚠️<br>유효하지 않은 카드 정보입니다.<br>다시 입력해주세요.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton retryButton = new JButton("카드 정보 재입력");
        styleButton(retryButton);
        retryButton.setBounds(200, 250, 200, 50);
        retryButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "CardInputScreen");
        });
        panel.add(retryButton);

        return panel;
    }

    private JPanel createExceedAttemptsScreen(Runnable onRetry) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("Message", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html>⚠️<br>입력 가능 횟수를 초과했습니다.<br>음료 선택 화면으로 돌아갑니다.</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton retryButton = new JButton("음료 선택 화면");
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
        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 2));
    }

    public static void main(String[] args) {
        // Test the CardInputUI with dummy actions
        Runnable onSuccess = () -> {
            PaymentUI paymentUI = new PaymentUI(true, () -> {}, () -> {});
            paymentUI.setVisible(true);
        };
        Runnable onInsufficientBalance = () -> {
            PaymentUI paymentUI = new PaymentUI(false, () -> {}, () -> {});
            paymentUI.setVisible(true);
        };
        Runnable onRetry = () -> {
            ChooseItemUI chooseItemUI = new ChooseItemUI();
            chooseItemUI.setVisible(true);
        };

        SwingUtilities.invokeLater(() -> {
            CardInputUI cardInputUI = new CardInputUI(1000, onSuccess, onInsufficientBalance, onRetry);
            cardInputUI.setVisible(true);
        });
    }
}
