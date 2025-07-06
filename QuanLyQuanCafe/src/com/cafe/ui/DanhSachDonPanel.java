package com.cafe.ui;

import com.cafe.model.*;
import com.cafe.service.DonHangService;
import com.cafe.util.TienUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DanhSachDonPanel extends JPanel {

    private final JTable tableDon;
    private final JTable tableChiTiet;
    private final DefaultTableModel modelDon;
    private final DefaultTableModel modelChiTiet;
    private final DonHangService donHangService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DanhSachDonPanel() {
        setLayout(new BorderLayout());
        donHangService = new DonHangService();

        // BẢNG ĐƠN HÀNG
        modelDon = new DefaultTableModel(new Object[]{"Ma don ", "Ngay", "Tong tien"}, 0);
        tableDon = new JTable(modelDon);
        JScrollPane scrollDon = new JScrollPane(tableDon);
        scrollDon.setBorder(BorderFactory.createTitledBorder("Danh sach don hang"));

        // BẢNG CHI TIẾT
        modelChiTiet = new DefaultTableModel(new Object[]{"Ten mon", "Gia", "So luong", "Thanh tien"}, 0);
        tableChiTiet = new JTable(modelChiTiet);
        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createTitledBorder("Chi tiet don hang"));

        // Sự kiện chọn dòng
        tableDon.getSelectionModel().addListSelectionListener(e -> hienThiChiTiet());

        // Layout chia đôi
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollDon, scrollChiTiet);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
        taiDuLieu();
    }

    private void taiDuLieu() {
        List<DonHang> ds = donHangService.getAll();
        modelDon.setRowCount(0);
        for (DonHang d : ds) {
            modelDon.addRow(new Object[]{
                    d.getMaDon(),
                    sdf.format(d.getNgayLap()),
                    TienUtil.dinhDangTien(d.tongTien())
            });
        }
    }

    private void hienThiChiTiet() {
        int row = tableDon.getSelectedRow();
        if (row == -1) return;

        int maDon = (int) modelDon.getValueAt(row, 0);
        DonHang don = donHangService.getAll().stream()
                .filter(d -> d.getMaDon() == maDon)
                .findFirst().orElse(null);

        if (don == null) return;

        modelChiTiet.setRowCount(0);
        for (ChiTietDon ct : don.getChiTietDons()) {
            modelChiTiet.addRow(new Object[]{
                    ct.getMon().getTenMon(),
                    TienUtil.dinhDangTien(ct.getMon().getGia()),
                    ct.getSoLuong(),
                    TienUtil.dinhDangTien(ct.tinhTien())
            });
        }
    }
}
