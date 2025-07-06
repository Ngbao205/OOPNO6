package com.cafe.ui;

import com.cafe.model.DonHang;
import com.cafe.service.DonHangService;
import com.cafe.util.TienUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ThongKePanel extends JPanel {

    private final JLabel lblTongDon, lblTongTien, lblMaxDon, lblMinDon;
    private final DonHangService donHangService;

    public ThongKePanel() {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        donHangService = new DonHangService();

        lblTongDon = new JLabel();
        lblTongTien = new JLabel();
        lblMaxDon = new JLabel();
        lblMinDon = new JLabel();

        JLabel title = new JLabel("THỐNG KÊ ĐƠN HÀNG", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);
        add(lblTongDon);
        add(lblTongTien);
        add(lblMaxDon);
        add(lblMinDon);

        thongKe();
    }

    private void thongKe() {
        List<DonHang> ds = donHangService.getAll();
        if (ds.isEmpty()) {
            lblTongDon.setText("Chưa có dữ liệu đơn hàng.");
            return;
        }

        double tongTien = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        DonHang maxDon = null, minDon = null;

        for (DonHang d : ds) {
            double tong = d.tongTien();
            tongTien += tong;

            if (tong > max) {
                max = tong;
                maxDon = d;
            }

            if (tong < min) {
                min = tong;
                minDon = d;
            }
        }

        lblTongDon.setText("Tổng số đơn hàng: " + ds.size());
        lblTongTien.setText("Tổng doanh thu: " + TienUtil.dinhDangTien(tongTien));
        lblMaxDon.setText("Đơn lớn nhất: Mã " + maxDon.getMaDon() + " - " + TienUtil.dinhDangTien(max));
        lblMinDon.setText("Đơn nhỏ nhất: Mã " + minDon.getMaDon() + " - " + TienUtil.dinhDangTien(min));
    }
}
