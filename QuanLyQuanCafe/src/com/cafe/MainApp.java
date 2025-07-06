package com.cafe;

import com.cafe.login.LoginService;
import com.cafe.model.*;
import com.cafe.service.DonHangService;
import com.cafe.util.IDGenerator;
import com.cafe.util.TienUtil;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final LoginService loginService = new LoginService();
    private static final DonHangService donHangService = new DonHangService();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        loginService.taoTaiKhoanMacDinh();

        System.out.println("==== DANG NHAP ====");
        System.out.print("Ten dang nhap: ");
        String user = sc.nextLine();
        System.out.print("Mat khau: ");
        String pass = sc.nextLine();

        if (loginService.dangNhap(user, pass)) {
            System.out.println(">> Dang nhap thanh cong!");
            menu();
        } else {
            System.out.println(">> Sai thong tin dang nhap!");
        }
    }

    private static void menu() {
        while (true) {
            System.out.println("\n=== MENU QUAN LY QUAN CAFE ===");
            System.out.println("1. Them don hang");
            System.out.println("2. Xem danh sach don hang");
            System.out.println("3. Tim kiem theo ten mon");
            System.out.println("4. Tim kiem theo khoang tien");
            System.out.println("5. Thong ke");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1 -> themDonHang();
                case 2 -> hienThiDonHang();
                case 3 -> timTheoTenMon();
                case 4 -> timTheoKhoangTien();
                case 5 -> thongKe();
                case 0 -> {
                    System.out.println("Tam biet!");
                    return;
                }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void themDonHang() {
        int id = IDGenerator.getNextID();
        Date ngay = new Date();
        DonHang don = new DonHang(id, ngay);

        System.out.print("So mon trong don: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.print("Ten mon: ");
            String tenMon = sc.nextLine();
            System.out.print("Gia mon: ");
            double gia = Double.parseDouble(sc.nextLine());
            System.out.print("So luong: ");
            int sl = Integer.parseInt(sc.nextLine());

            Mon mon = new Mon(tenMon, gia);
            ChiTietDon chiTiet = new ChiTietDon(mon, sl);
            don.getChiTietDons().add(chiTiet);
        }

        donHangService.themDon(don);
        System.out.println(">> Them don hang thanh cong! Ma don: " + id);
    }

    private static void hienThiDonHang() {
        List<DonHang> list = donHangService.getAll();
        if (list.isEmpty()) {
            System.out.println(">> Chua co don hang nao.");
            return;
        }

        for (DonHang don : list) {
            System.out.println("[" + don.getMaDon() + "] Ngay: " + sdf.format(don.getNgayLap())
                    + " | Tổng tiền: " + TienUtil.dinhDangTien(don.tongTien()));
            for (ChiTietDon ct : don.getChiTietDons()) {
                System.out.println("   > " + ct);
            }
        }
    }

    private static void timTheoTenMon() {
        System.out.print("Nhap ten mon gan dung: ");
        String keyword = sc.nextLine().toLowerCase();

        for (DonHang don : donHangService.getAll()) {
            for (ChiTietDon ct : don.getChiTietDons()) {
                if (ct.getMon().getTenMon().toLowerCase().contains(keyword)) {
                    System.out.println("[" + don.getMaDon() + "] " + ct);
                }
            }
        }
    }

    private static void timTheoKhoangTien() {
        System.out.print("Nhap tien nho: ");
        double from = Double.parseDouble(sc.nextLine());
        System.out.print("Nhap tien lon: ");
        double to = Double.parseDouble(sc.nextLine());

        for (DonHang don : donHangService.getAll()) {
            double tong = don.tongTien();
            if (tong >= from && tong <= to) {
                System.out.println("[" + don.getMaDon() + "] " + TienUtil.dinhDangTien(tong));
            }
        }
    }

    private static void thongKe() {
        List<DonHang> ds = donHangService.getAll();
        if (ds.isEmpty()) {
            System.out.println(">> Chưa co don hang nao.");
            return;
        }

        double tong = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        DonHang donMax = null, donMin = null;

        for (DonHang d : ds) {
            double tien = d.tongTien();
            tong += tien;
            if (tien > max) {
                max = tien;
                donMax = d;
            }
            if (tien < min) {
                min = tien;
                donMin = d;
            }
        }

        System.out.println("Tong so don hang: " + ds.size());
        System.out.println("Tong doanh thu: " + TienUtil.dinhDangTien(tong));
        System.out.println("Đon hang cao nhat: " + donMax);
        System.out.println("Đon hang thap nhat: " + donMin);
    }
}
