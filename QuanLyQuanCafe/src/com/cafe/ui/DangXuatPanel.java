package com.cafe.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangXuatPanel extends JPanel {

    private final JFrame mainFrame;

    public DangXuatPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        JButton btnLogout = new JButton("Dang xuat");
        btnLogout.setFont(btnLogout.getFont().deriveFont(16f));
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        DangXuatPanel.this,
                        "Ban co chac chan muon dang xuat?",
                        "Xac nhan",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    mainFrame.dispose(); // Đóng MainForm
                    new LoginForm().setVisible(true); // Quay về Login
                }
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        btnLogout.setAlignmentX(CENTER_ALIGNMENT);
        add(btnLogout);
        add(Box.createVerticalGlue());
    }
}
