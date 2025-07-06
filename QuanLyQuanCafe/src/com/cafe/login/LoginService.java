package com.cafe.login;

import com.cafe.model.TaiKhoan;
import com.cafe.service.TaiKhoanService;

public class LoginService {

    private final TaiKhoanService taiKhoanService;

    public LoginService() {
        taiKhoanService = new TaiKhoanService();
    }

    
    public boolean dangNhap(String username, String password) {
        TaiKhoan tk = taiKhoanService.timTaiKhoan(username, password);
        return tk != null;
    }

    /**
     */
    public void taoTaiKhoanMacDinh() {
        if (taiKhoanService.getAll().isEmpty()) {
            taiKhoanService.themTaiKhoan(new TaiKhoan("admin", "123"));
            System.out.println("Đã tạo tài khoản mặc định: admin / 123");
        }
    }
}
