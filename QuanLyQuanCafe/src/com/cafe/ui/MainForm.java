package com.cafe.ui;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    private final JTabbedPane tabbedPane;

    public MainForm() {
        super("Phần mềm quản lý quán cafe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Thêm đơn hàng", new ThemDonHangPanel());
        tabbedPane.addTab("Danh sách đơn", new DanhSachDonPanel());
        tabbedPane.addTab("Tìm kiếm", new TimKiemPanel());
        tabbedPane.addTab("Thống kê", new ThongKePanel());
        tabbedPane.addTab("Đăng xuất", new DangXuatPanel(this));

        add(tabbedPane, BorderLayout.CENTER);
    }
}
