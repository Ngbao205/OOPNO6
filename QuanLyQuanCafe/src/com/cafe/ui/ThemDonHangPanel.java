package com.cafe.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.toedter.calendar.JDateChooser; 

import com.cafe.model.Mon;
import com.cafe.model.ChiTietDon;
import com.cafe.model.DonHang;
import com.cafe.service.DonHangService;
import com.cafe.util.TienUtil;
import com.cafe.util.IDGenerator;


public class ThemDonHangPanel extends JPanel {

    private final JComboBox<Mon> cbMon;
    private final JTextField txtSoLuong;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final List<ChiTietDon> danhSachMon;
    private final JDateChooser dateChooser;
    private final DonHangService donHangService;

    public ThemDonHangPanel() {
        setLayout(new BorderLayout());
        danhSachMon = new ArrayList<>();
        donHangService = new DonHangService();

        // TOP: Nhập thông tin món
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin đơn hàng"));

        cbMon = new JComboBox<>(getMonMau()); // Combo món mẫu
        txtSoLuong = new JTextField();
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());

        JButton btnThemMon = new JButton("Thêm món");
        btnThemMon.addActionListener(e -> themMon());

        inputPanel.add(new JLabel("Chọn món:"));
        inputPanel.add(cbMon);
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(txtSoLuong);
        inputPanel.add(new JLabel("Ngày lập:"));
        inputPanel.add(dateChooser);
        inputPanel.add(btnThemMon);

        add(inputPanel, BorderLayout.NORTH);

        // CENTER: Bảng danh sách món
        tableModel = new DefaultTableModel(new Object[]{"Tên món", "Giá", "Số lượng", "Thành tiền"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // BOTTOM: Nút lưu đơn
        JButton btnLuuDon = new JButton("Lưu đơn hàng");
        btnLuuDon.addActionListener(e -> luuDon());

        add(btnLuuDon, BorderLayout.SOUTH);
    }

    private void themMon() {
        Mon mon = (Mon) cbMon.getSelectedItem();
        if (mon == null) return;

        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ChiTietDon chiTiet = new ChiTietDon(mon, soLuong);
        danhSachMon.add(chiTiet);
        tableModel.addRow(new Object[]{
                mon.getTenMon(),
                TienUtil.dinhDangTien(mon.getGia()),
                soLuong,
                TienUtil.dinhDangTien(chiTiet.tinhTien())
        });
    }

    private void luuDon() {
        if (danhSachMon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có món nào trong đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date ngay = dateChooser.getDate();
        if (ngay == null) ngay = new Date();

        int id = IDGenerator.getNextID();
        DonHang don = new DonHang(id, ngay);
        don.setChiTietDons(danhSachMon);
        donHangService.themDon(don);

        JOptionPane.showMessageDialog(this, "Lưu đơn hàng thành công! Mã đơn: " + id);

        // Reset
        danhSachMon.clear();
        tableModel.setRowCount(0);
        txtSoLuong.setText("");
    }

    private Mon[] getMonMau() {
        return new Mon[]{
                new Mon("Cà phê sữa", 25000),
                new Mon("Trà đào", 30000),
                new Mon("Nước cam", 20000),
                new Mon("Bạc xỉu", 28000),
                new Mon("Matcha đá xay", 45000)
        };
    }
}
