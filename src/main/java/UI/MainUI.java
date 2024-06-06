package UI;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel mainScreen;
    private JPanel authCodeScreen;
    private JPanel adminModeScreen;

    public MainUI() {
        setTitle("Team 7 - Distributed Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainScreen = createMainScreen();
        authCodeScreen = createAuthCodeScreen();
        adminModeScreen = createAdminModeScreen();

        mainPanel.add(mainScreen, "MainScreen");
        mainPanel.add(authCodeScreen, "AuthCodeScreen");
        mainPanel.add(adminModeScreen, "AdminModeScreen");

        add(mainPanel);
        cardLayout.show(mainPanel, "MainScreen");
    }

    private JPanel createMainScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("메인 화면", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JButton beverageButton = new JButton("1. 음료 선택");
        styleButton(beverageButton);
        beverageButton.setBounds(55, 150, 150, 100);
        beverageButton.addActionListener(e -> {
            ChooseItemUI chooseItemUI = new ChooseItemUI();
            chooseItemUI.setVisible(true);
        });
        panel.add(beverageButton);

        JButton authCodeButton = new JButton("2. 인증코드 입력");
        styleButton(authCodeButton);
        authCodeButton.setBounds(225, 150, 150, 100);
        authCodeButton.addActionListener(e -> cardLayout.show(mainPanel, "AuthCodeScreen"));
        panel.add(authCodeButton);

        JButton adminButton = new JButton("3. 관리자 모드");
        styleButton(adminButton);
        adminButton.setBounds(395, 150, 150, 100);
        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminModeScreen"));
        panel.add(adminButton);

        return panel;
    }

    private JPanel createAuthCodeScreen() {
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
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setBounds(150, 100, 300, 30);
        panel.add(instructionLabel);

        JTextField authCodeField = new JTextField();
        authCodeField.setBounds(150, 150, 300, 30);
        panel.add(authCodeField);

        JLabel remainingLabel = new JLabel("남은 입력 횟수: 03/03", JLabel.CENTER);
        remainingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        remainingLabel.setBounds(150, 200, 300, 30);
        panel.add(remainingLabel);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(250, 250, 100, 30);
        panel.add(okButton);

        return panel;
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

        JLabel idLabel = new JLabel("ID");
        idLabel.setBounds(150, 100, 100, 30);
        panel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(250, 100, 200, 30);
        panel.add(idField);

        JLabel pwLabel = new JLabel("PW");
        pwLabel.setBounds(150, 150, 100, 30);
        panel.add(pwLabel);

        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(250, 150, 200, 30);
        panel.add(pwField);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(200, 200, 100, 30);
        panel.add(okButton);

        JButton cancelButton = new JButton("CANCEL");
        styleButton(cancelButton);
        cancelButton.setBounds(350, 200, 100, 30);
        panel.add(cancelButton);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0x3B5998));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x3B5998), 1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI mainUI = new MainUI();
            mainUI.setVisible(true);
        });
    }
}
