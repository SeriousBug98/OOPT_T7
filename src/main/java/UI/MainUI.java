package UI;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    private CardLayout cardLayout;
    public JPanel mainPanel;
    private JPanel mainScreen;
    private JPanel adminLoginScreen;
    private ChooseItemUI chooseItemUI;

    public MainUI() {
        setTitle("Team 7 - Distributed Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainScreen = createMainScreen();
        adminLoginScreen = createAdminLoginScreen();
        chooseItemUI = new ChooseItemUI();

        mainPanel.add(mainScreen, "MainScreen");
        mainPanel.add(adminLoginScreen, "AdminLoginScreen");
        mainPanel.add(chooseItemUI, "ChooseItemScreen");

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
        beverageButton.addActionListener(e -> cardLayout.show(mainPanel, "ChooseItemScreen"));
        panel.add(beverageButton);

        JButton authCodeButton = new JButton("2. 인증코드 입력");
        styleButton(authCodeButton);
        authCodeButton.setBounds(225, 150, 150, 100);
        authCodeButton.addActionListener(e -> {
            AuthenticationCodeInputUI authCodeUI = new AuthenticationCodeInputUI();
            authCodeUI.setVisible(true);
            this.setVisible(false);
        });
        panel.add(authCodeButton);

        JButton adminButton = new JButton("3. 관리자 모드");
        styleButton(adminButton);
        adminButton.setBounds(395, 150, 150, 100);
        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminLoginScreen"));
        panel.add(adminButton);

        return panel;
    }

    private JPanel createAdminLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute positioning

        JLabel titleLabel = new JLabel("관리자 모드", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x3B5998));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 600, 50);
        panel.add(titleLabel);

        JLabel instructionLabel = new JLabel("아이디와 패스워드를 입력해주세요", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        instructionLabel.setBounds(150, 100, 300, 30);
        panel.add(instructionLabel);

        JLabel idLabel = new JLabel("ID");
        idLabel.setBounds(150, 150, 100, 30);
        panel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(250, 150, 200, 30);
        panel.add(idField);

        JLabel pwLabel = new JLabel("PW");
        pwLabel.setBounds(150, 200, 100, 30);
        panel.add(pwLabel);

        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(250, 200, 200, 30);
        panel.add(pwField);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.setBounds(170, 270, 100, 30);
        okButton.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(pwField.getPassword());
            if (id.equals("admin") && pw.equals("1234")) {
                AdminUI adminUI = new AdminUI();
                adminUI.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "ID 또는 비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(okButton);

        JButton cancelButton = new JButton("CANCEL");
        styleButton(cancelButton);
        cancelButton.setBounds(330, 270, 100, 30);
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "MainScreen"));
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


