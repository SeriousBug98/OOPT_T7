package UI;

import dvm.service.controller.card.CardServiceController;

import javax.swing.*;
import java.awt.*;

public class ChooseItemUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel beverageSelectionScreen;
    private JPanel messageScreen;
    private JPanel prepayScreen;
    private CardServiceController cardServiceController;

    public ChooseItemUI() {
        cardServiceController = new CardServiceController();

        setTitle("Team 7 - Distributed Vending Machine - Choose Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 메인 UI를 닫지 않도록 설정
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        beverageSelectionScreen = createBeverageSelectionScreen();
        messageScreen = createMessageScreen();
        prepayScreen = createPrepayScreen();

        mainPanel.add(beverageSelectionScreen, "BeverageSelectionScreen");
        mainPanel.add(messageScreen, "MessageScreen");
        mainPanel.add(prepayScreen, "PrepayScreen");

        add(mainPanel);
        cardLayout.show(mainPanel, "BeverageSelectionScreen");
    }

    private JPanel createBeverageSelectionScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("음료 선택", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        String[] beverages = {"콜라", "사이다", "녹차", "홍차", "밀크티", "탄산수", "보리차", "캔커피",
                "물", "에너지드링크", "유자차", "식혜", "아이스티", "딸기주스", "오렌지주스",
                "포도주스", "이온음료", "아메리카노", "핫초코", "카페라떼"};

        int x = 30, y = 75, width = 100, height = 30;
        for (int i = 0; i < beverages.length; i++) {
            JButton button = new JButton(beverages[i]);
            styleButton(button);
            button.setBounds(x + (i % 5) * (width + 10), y + (i / 5) * (height + 20), width, height);
            panel.add(button);
        }

        JLabel typeLabel = new JLabel("종류:");
        typeLabel.setBounds(50, 300, 50, 30);
        panel.add(typeLabel);

        JComboBox<String> beverageComboBox = new JComboBox<>(beverages);
        beverageComboBox.setBounds(100, 300, 150, 30);
        panel.add(beverageComboBox);

        JLabel quantityLabel = new JLabel("개수:");
        quantityLabel.setBounds(300, 300, 50, 30);
        panel.add(quantityLabel);

        JComboBox<Integer> quantityComboBox = new JComboBox<>();
        for (int i = 1; i <= 99; i++) {
            quantityComboBox.addItem(i);
        }
        quantityComboBox.setBounds(350, 300, 80, 30);
        panel.add(quantityComboBox);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(450, 300, 100, 30);
        okButton.addActionListener(e -> {
            String selectedBeverage = (String) beverageComboBox.getSelectedItem();
            int quantity = (int) quantityComboBox.getSelectedItem();
            boolean isStockEnough = checkStock(selectedBeverage, quantity);
            if (isStockEnough) {
                cardLayout.show(mainPanel, "MessageScreen");
            } else {
                cardLayout.show(mainPanel, "PrepayScreen");
            }
        });
        panel.add(okButton);

        return panel;
    }

    private JPanel createMessageScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("    Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel1 = new JLabel("<html>선택하신 음료수의 재고가 충분합니다.</html>", JLabel.CENTER);
        JLabel messageLabel2 = new JLabel("<html><br>결제를 진행하시겠습니까?</html>", JLabel.CENTER);
        messageLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel1.setBounds(50, 100, 500, 100);
        messageLabel2.setBounds(50, 130, 500, 100);
        panel.add(messageLabel1);
        panel.add(messageLabel2);

        JButton yesButton = new JButton("YES");
        styleButton(yesButton);
        yesButton.setBounds(150, 250, 100, 50);
        yesButton.addActionListener(e -> {
            int price = 1000; // 실제 가격을 설정해야 합니다.
            Runnable onSuccess = () -> {
                PaymentUI paymentUI = new PaymentUI(true, this::goToMainMenu, this::goToBeverageSelection);
                paymentUI.setVisible(true);
                dispose();
            };
            Runnable onInsufficientBalance = () -> {
                PaymentUI paymentUI = new PaymentUI(false, this::goToMainMenu, this::goToBeverageSelection);
                paymentUI.setVisible(true);
                dispose();
            };
            Runnable onRetry = () -> {
                ChooseItemUI chooseItemUI = new ChooseItemUI();
                chooseItemUI.setVisible(true);
            };
            CardInputUI cardInputUI = new CardInputUI(price, onSuccess, onInsufficientBalance, onRetry);
            cardInputUI.setVisible(true);
            dispose();
        });
        panel.add(yesButton);

        JButton noButton = new JButton("NO");
        styleButton(noButton);
        noButton.setBounds(350, 250, 100, 50);
        noButton.addActionListener(e -> {
            goToMainMenu();
            dispose();
        });
        panel.add(noButton);

        return panel;
    }

    private JPanel createPrepayScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("다른 DVM에서 구매 진행하기", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html>(00,00) 위치의 자판기에서 해당 음료를 판매중입니다.<br>선결제를 진행하시겠습니까?</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton yesButton = new JButton("YES");
        styleButton(yesButton);
        yesButton.setBounds(150, 250, 100, 50);
        yesButton.addActionListener(e -> {
            int price = 1000; // 실제 가격을 설정해야 합니다.
            Runnable onSuccess = () -> {
                PaymentUI paymentUI = new PaymentUI(true, this::goToMainMenu, this::goToBeverageSelection);
                paymentUI.setVisible(true);
                dispose();
            };
            Runnable onInsufficientBalance = () -> {
                PaymentUI paymentUI = new PaymentUI(false, this::goToMainMenu, this::goToBeverageSelection);
                paymentUI.setVisible(true);
                dispose();
            };
            Runnable onRetry = () -> {
                ChooseItemUI chooseItemUI = new ChooseItemUI();
                chooseItemUI.setVisible(true);
            };
            CardInputUI cardInputUI = new CardInputUI(price, onSuccess, onInsufficientBalance, onRetry);
            cardInputUI.setVisible(true);
            dispose();
        });
        panel.add(yesButton);

        JButton noButton = new JButton("NO");
        styleButton(noButton);
        noButton.setBounds(350, 250, 100, 50);
        noButton.addActionListener(e -> {
            goToMainMenu();
            dispose();
        });
        panel.add(noButton);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0x3B5998));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 1));
    }

    private boolean checkStock(String beverageName, int quantity) {
        // 재고 확인 로직을 구현하세요.
        // 임시로 항상 true를 반환합니다.
        return true;
    }

    private void goToMainMenu() {
        MainUI mainUI = new MainUI();
        mainUI.setVisible(true);
    }

    private void goToBeverageSelection() {
        ChooseItemUI chooseItemUI = new ChooseItemUI();
        chooseItemUI.setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            ChooseItemUI chooseItemUI = new ChooseItemUI();
//            chooseItemUI.setVisible(true);
//        });
//    }
}


//package UI;
//
//import dvm.service.controller.card.CardServiceController;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class ChooseItemUI extends JFrame {
//    private CardLayout cardLayout;
//    private JPanel mainPanel;
//    private JPanel beverageSelectionScreen;
//    private JPanel messageScreen;
//    private JPanel prepayScreen;
//    private CardServiceController cardServiceController;
//
//    public ChooseItemUI() {
//        cardServiceController = new CardServiceController();
//
//        setTitle("Team 7 - Distributed Vending Machine - Choose Item");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 메인 UI를 닫지 않도록 설정
//        setSize(600, 400);
//        setLocationRelativeTo(null);
//
//        cardLayout = new CardLayout();
//        mainPanel = new JPanel(cardLayout);
//
//        beverageSelectionScreen = createBeverageSelectionScreen();
//        messageScreen = createMessageScreen();
//        prepayScreen = createPrepayScreen();
//
//        mainPanel.add(beverageSelectionScreen, "BeverageSelectionScreen");
//        mainPanel.add(messageScreen, "MessageScreen");
//        mainPanel.add(prepayScreen, "PrepayScreen");
//
//        add(mainPanel);
//        cardLayout.show(mainPanel, "BeverageSelectionScreen");
//    }
//
//    private JPanel createBeverageSelectionScreen() {
//        JPanel panel = new JPanel();
//        panel.setLayout(null); // Absolute positioning
//
//        JLabel titleLabel = new JLabel("음료 선택", JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setOpaque(true);
//        titleLabel.setBackground(new Color(0x3B5998));
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setBounds(0, 0, 600, 50);
//        panel.add(titleLabel);
//
//        String[] beverages = {"콜라", "사이다", "녹차", "홍차", "밀크티", "탄산수", "보리차", "캔커피",
//                "물", "에너지드링크", "유자차", "식혜", "아이스티", "딸기주스", "오렌지주스",
//                "포도주스", "이온음료", "아메리카노", "핫초코", "카페라떼"};
//
//        int x = 30, y = 75, width = 100, height = 30;
//        for (int i = 0; i < beverages.length; i++) {
//            JButton button = new JButton(beverages[i]);
//            styleButton(button);
//            button.setBounds(x + (i % 5) * (width + 10), y + (i / 5) * (height + 20), width, height);
//            panel.add(button);
//        }
//
//        JLabel typeLabel = new JLabel("종류:");
//        typeLabel.setBounds(50, 300, 50, 30);
//        panel.add(typeLabel);
//
//        JComboBox<String> beverageComboBox = new JComboBox<>(beverages);
//        beverageComboBox.setBounds(100, 300, 150, 30);
//        panel.add(beverageComboBox);
//
//        JLabel quantityLabel = new JLabel("개수:");
//        quantityLabel.setBounds(300, 300, 50, 30);
//        panel.add(quantityLabel);
//
//        JComboBox<Integer> quantityComboBox = new JComboBox<>();
//        for (int i = 1; i <= 99; i++) {
//            quantityComboBox.addItem(i);
//        }
//        quantityComboBox.setBounds(350, 300, 80, 30);
//        panel.add(quantityComboBox);
//
//        JButton okButton = new JButton("OK");
//        styleButton(okButton);
//        okButton.setBounds(450, 300, 100, 30);
//        okButton.addActionListener(e -> {
//            String selectedBeverage = (String) beverageComboBox.getSelectedItem();
//            int quantity = (int) quantityComboBox.getSelectedItem();
//            boolean isStockEnough = checkStock(selectedBeverage, quantity);
//            if (isStockEnough) {
//                cardLayout.show(mainPanel, "MessageScreen");
//            } else {
//                cardLayout.show(mainPanel, "PrepayScreen");
//            }
//        });
//        panel.add(okButton);
//
//        return panel;
//    }
//
//    private JPanel createMessageScreen() {
//        JPanel panel = new JPanel();
//        panel.setLayout(null); // Absolute positioning
//
//        JLabel titleLabel = new JLabel("    Message", JLabel.LEFT);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setOpaque(true);
//        titleLabel.setBackground(new Color(0x3B5998));
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setBounds(0, 0, 600, 50);
//        panel.add(titleLabel);
//
//        JLabel messageLabel1 = new JLabel("<html>선택하신 음료수의 재고가 충분합니다.</html>", JLabel.CENTER);
//        JLabel messageLabel2 = new JLabel("<html><br>결제를 진행하시겠습니까?</html>", JLabel.CENTER);
//        messageLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
//        messageLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
//        messageLabel1.setBounds(50, 100, 500, 100);
//        messageLabel2.setBounds(50, 130, 500, 100);
//        panel.add(messageLabel1);
//        panel.add(messageLabel2);
//
//        JButton yesButton = new JButton("YES");
//        styleButton(yesButton);
//        yesButton.setBounds(150, 250, 100, 50);
//        yesButton.addActionListener(e -> {
//            int price = 1000; // 실제 가격을 설정해야 합니다.
//            Runnable onSuccess = () -> {
//                PaymentUI paymentUI = new PaymentUI(true, this::goToMainMenu, this::goToBeverageSelection);
//                paymentUI.setVisible(true);
//                dispose();
//            };
//            Runnable onInsufficientBalance = () -> {
//                PaymentUI paymentUI = new PaymentUI(false, this::goToMainMenu, this::goToBeverageSelection);
//                paymentUI.setVisible(true);
//                dispose();
//            };
//            Runnable onRetry = () -> {
//                ChooseItemUI chooseItemUI = new ChooseItemUI();
//                chooseItemUI.setVisible(true);
//            };
//            CardInputUI cardInputUI = new CardInputUI(price, onSuccess, onInsufficientBalance, onRetry);
//            cardInputUI.setVisible(true);
//            dispose();
//        });
//        panel.add(yesButton);
//
//        JButton noButton = new JButton("NO");
//        styleButton(noButton);
//        noButton.setBounds(350, 250, 100, 50);
//        noButton.addActionListener(e -> {
//            goToMainMenu();
//            dispose();
//        });
//        panel.add(noButton);
//
//        return panel;
//    }
//
//    private JPanel createPrepayScreen() {
//        JPanel panel = new JPanel();
//        panel.setLayout(null); // Absolute positioning
//
//        JLabel titleLabel = new JLabel("다른 DVM에서 구매 진행하기", JLabel.LEFT);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setOpaque(true);
//        titleLabel.setBackground(new Color(0x3B5998));
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setBounds(0, 0, 600, 50);
//        panel.add(titleLabel);
//
//        JLabel messageLabel = new JLabel("<html>(00,00) 위치의 자판기에서 해당 음료를 판매중입니다.<br>선결제를 진행하시겠습니까?</html>", JLabel.CENTER);
//        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        messageLabel.setBounds(50, 100, 500, 100);
//        panel.add(messageLabel);
//
//        JButton yesButton = new JButton("YES");
//        styleButton(yesButton);
//        yesButton.setBounds(150, 250, 100, 50);
//        yesButton.addActionListener(e -> {
//            int price = 1000; // 실제 가격을 설정해야 합니다.
//            Runnable onSuccess = () -> {
//                PaymentUI paymentUI = new PaymentUI(true, this::goToMainMenu, this::goToBeverageSelection);
//                paymentUI.setVisible(true);
//                dispose();
//            };
//            Runnable onInsufficientBalance = () -> {
//                PaymentUI paymentUI = new PaymentUI(false, this::goToMainMenu, this::goToBeverageSelection);
//                paymentUI.setVisible(true);
//                dispose();
//            };
//            Runnable onRetry = () -> {
//                ChooseItemUI chooseItemUI = new ChooseItemUI();
//                chooseItemUI.setVisible(true);
//            };
//            CardInputUI cardInputUI = new CardInputUI(price, onSuccess, onInsufficientBalance, onRetry);
//            cardInputUI.setVisible(true);
//            dispose();
//        });
//        panel.add(yesButton);
//
//        JButton noButton = new JButton("NO");
//        styleButton(noButton);
//        noButton.setBounds(350, 250, 100, 50);
//        noButton.addActionListener(e -> {
//            goToMainMenu();
//            dispose();
//        });
//        panel.add(noButton);
//
//        return panel;
//    }
//
//    private void styleButton(JButton button) {
//        button.setFont(new Font("Arial", Font.PLAIN, 16));
//        button.setBackground(Color.WHITE);
//        button.setForeground(new Color(0x3B5998));
//        button.setFocusPainted(false);
//        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 1));
//    }
//
//    private boolean checkStock(String beverageName, int quantity) {
//        // 재고 확인 로직을 구현하세요.
//        // 임시로 항상 true를 반환합니다.
//        return true;
//    }
//
//    private void goToMainMenu() {
//        MainUI mainUI = new MainUI();
//        mainUI.setVisible(true);
//    }
//
//    private void goToBeverageSelection() {
//        ChooseItemUI chooseItemUI = new ChooseItemUI();
//        chooseItemUI.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            ChooseItemUI chooseItemUI = new ChooseItemUI();
//            chooseItemUI.setVisible(true);
//        });
//    }
//}
