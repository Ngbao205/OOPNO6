package com.cafe.ui;

import com.cafe.model.*;
import com.cafe.service.DonHangService;
import com.cafe.util.TienUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TimKiemPanel extends JPanel {

    private final DonHangService donHangService;
    private final JTextField txtTenMon, txtTuTien, txtDenTien;
    private final DefaultTableModel model;
    private final JTable table;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public TimKiemPanel() {
        setLayout(new BorderLayout());
        donHangService = new DonHangService();

        JPanel topPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        txtTenMon = new JTextField();
        txtTuTien = new JTextField();
        txtDenTien = new JTextField();

        JButton btnTimTen = new JButton("Tìm theo tên món");
        btnTimTen.addActionListener(e -> timTheoTenMon());

        JButton btnTimTien = new JButton("Tìm theo khoảng tiền");
        btnTimTien.addActionListener(e -> timTheoKhoangTien());

        topPanel.add(new JLabel("Tên món gần đúng:"));
        topPanel.add(txtTenMon);
        topPanel.add(btnTimTen);
        topPanel.add(new JLabel(""));

        topPanel.add(new JLabel("Từ tiền:"));
        topPanel.add(txtTuTien);
        topPanel.add(new JLabel("Đến tiền:"));
        topPanel.add(txtDenTien);

        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Mã đơn", "Ngày", "Tên món", "SL", "Tổng đơn"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void timTheoTenMon() {
        String keyword = txtTenMon.getText().toLowerCase().trim();
        model.setRowCount(0);

        for (DonHang d : donHangService.getAll()) {
            for (ChiTietDon ct : d.getChiTietDons()) {
                if (ct.getMon().getTenMon().toLowerCase().contains(keyword)) {
                    model.addRow(new Object[]{
                            d.getMaDon(),
                            sdf.format(d.getNgayLap()),
                            ct.getMon().getTenMon(),
                            ct.getSoLuong(),
                            TienUtil.dinhDangTien(d.tongTien())
                    });
                }
            }
        }
    }

    private void timTheoKhoangTien() {
        double from, to;
        try {
            from = Double.parseDouble(txtTuTien.getText());
            to = Double.parseDouble(txtDenTien.getText());
            if (from > to) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập khoảng tiền hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.setRowCount(0);
        for (DonHang d : donHangService.getAll()) {
            double tong = d.tongTien();
            if (tong >= from && tong <= to) {
                model.addRow(new Object[]{
                        d.getMaDon(),
                        sdf.format(d.getNgayLap()),
                        "Tổng đơn",
                        "",
                        TienUtil.dinhDangTien(tong)
                });
            }
        }
    }
}
