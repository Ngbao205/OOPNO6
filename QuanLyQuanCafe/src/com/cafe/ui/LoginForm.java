package com.cafe.ui;

import com.cafe.login.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginForm extends JFrame {

    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final LoginService loginService;

    public LoginForm() {
        super("Đăng nhập quản lý quán cafe");
        loginService = new LoginService();
        loginService.taoTaiKhoanMacDinh();

        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        panel.add(new JLabel("Tên đăng nhập:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        add(panel, BorderLayout.CENTER);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.addActionListener(this::handleLogin);
        add(btnLogin, BorderLayout.SOUTH);
    }

    private void handleLogin(ActionEvent e) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (loginService.dangNhap(username, password)) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            this.dispose(); // đóng LoginForm
            new MainForm().setVisible(true); // mở MainForm (chưa viết)
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
