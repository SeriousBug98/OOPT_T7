package UI;

import dvm.service.controller.admin.SetItemPrice;
import dvm.service.controller.admin.SetItemStock;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel adminModeScreen;
    private JPanel stockManagementScreen;
    private JPanel priceManagementScreen;
    private JPanel logoutScreen;

    public AdminUI() {
        setTitle("Team 7 - Distributed Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        adminModeScreen = createAdminModeScreen();
        stockManagementScreen = createStockManagementScreen();
        priceManagementScreen = createPriceManagementScreen();
        logoutScreen = createLogoutScreen();

        mainPanel.add(adminModeScreen, "AdminModeScreen");
        mainPanel.add(stockManagementScreen, "StockManagementScreen");
        mainPanel.add(priceManagementScreen, "PriceManagementScreen");
        mainPanel.add(logoutScreen, "LogoutScreen");

        add(mainPanel);
        cardLayout.show(mainPanel, "AdminModeScreen");
    }

    private JPanel createAdminModeScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("관리자 모드", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JButton stockButton = new JButton("음료 재고 관리");
        styleButton(stockButton);
        stockButton.setBounds(125, 140, 150, 100);
        stockButton.addActionListener(e -> cardLayout.show(mainPanel, "StockManagementScreen"));
        panel.add(stockButton);

        JButton priceButton = new JButton("음료 가격 관리");
        styleButton(priceButton);
        priceButton.setBounds(325, 140, 150, 100);
        priceButton.addActionListener(e -> cardLayout.show(mainPanel, "PriceManagementScreen"));
        panel.add(priceButton);

        JButton logoutButton = new JButton("LOGOUT");
        styleButton(logoutButton);
        logoutButton.setBounds(225, 300, 150, 40);
        logoutButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "LogoutScreen");
            Timer timer = new Timer(2000, event -> {
                MainUI mainUI = new MainUI();
                mainUI.setVisible(true);
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        });
        panel.add(logoutButton);

        return panel;
    }

    private JPanel createStockManagementScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("음료 재고 관리", JLabel.CENTER);
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
            int beverageId = beverageComboBox.getSelectedIndex() + 1;

            SetItemStock setItemStock = new SetItemStock();
            setItemStock.process(beverageId, quantity);
            cardLayout.show(mainPanel, "AdminModeScreen");
        });
        panel.add(okButton);

        return panel;
    }

    private JPanel createPriceManagementScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("음료 가격 관리", JLabel.CENTER);
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
        JComboBox<String> beverageComboBox = new JComboBox<>(beverages);
        beverageComboBox.setBounds(100, 300, 150, 30);
        panel.add(beverageComboBox);

        JLabel priceLabel = new JLabel("가격:");
        priceLabel.setBounds(300, 300, 50, 30);
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(350, 300, 80, 30);
        panel.add(priceField);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(450, 300, 100, 30);
        okButton.addActionListener(e -> {
            try {
                String selectedBeverage = (String) beverageComboBox.getSelectedItem();
                int price = Integer.parseInt(priceField.getText());
                int beverageId = beverageComboBox.getSelectedIndex() + 1;

                SetItemPrice setItemPrice = new SetItemPrice();
                setItemPrice.process(beverageId, price);
                cardLayout.show(mainPanel, "AdminModeScreen");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "유효한 숫자를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                priceField.setText("");
            }
        });
        panel.add(okButton);

        return panel;
    }

    private JPanel createLogoutScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Message", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel messageLabel = new JLabel("<html><br>관리자 모드 로그아웃</html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(50, 100, 500, 100);
        panel.add(messageLabel);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(200, 250, 200, 50);
        okButton.addActionListener(e -> {
            MainUI mainUI = new MainUI();
            mainUI.setVisible(true);
            dispose();
        });
        panel.add(okButton);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0x3B5998));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 1));
    }

    private void updateStock(String beverageName, int quantity) {
        int beverageId = getBeverageId(beverageName);
        if (beverageId != -1) {
            SetItemStock setItemStock = new SetItemStock();
            setItemStock.process(beverageId, quantity);
        }
    }

    private void updatePrice(String beverageName, int price) {
        int beverageId = getBeverageId(beverageName);
        if (beverageId != -1) {
            SetItemPrice setItemPrice = new SetItemPrice();
            setItemPrice.process(beverageId, price);
        }
    }

    private int getBeverageId(String beverageName) {
        String[] beverages = {"콜라", "사이다", "녹차", "홍차", "밀크티", "탄산수", "보리차", "캔커피",
                "물", "에너지드링크", "유자차", "식혜", "아이스티", "딸기주스", "오렌지주스",
                "포도주스", "이온음료", "아메리카노", "핫초코", "카페라떼"};
        for (int i = 0; i < beverages.length; i++) {
            if (beverages[i].equals(beverageName)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminUI adminUI = new AdminUI();
            adminUI.setVisible(true);
        });
    }
}
