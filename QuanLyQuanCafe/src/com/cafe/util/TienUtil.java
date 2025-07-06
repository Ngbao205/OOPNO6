package com.cafe.util;

import java.text.NumberFormat;
import java.util.Locale;

public class TienUtil {

    // Định dạng tiền: 1,000,000 VNĐ
    public static String dinhDangTien(double soTien) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format(soTien) + " VNĐ";
    }

    // Ví dụ: để test
    public static void main(String[] args) {
        System.out.println(dinhDangTien(123456789)); // Kết quả: 123.456.789 VNĐ
    }
}
