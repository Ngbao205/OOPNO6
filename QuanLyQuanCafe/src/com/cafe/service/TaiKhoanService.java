package com.cafe.service;

import com.cafe.model.TaiKhoan;
import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class TaiKhoanService {
    private static final String FILE_NAME = "TaiKhoan.xml";
    private List<TaiKhoan> danhSach = new ArrayList<>();

    public TaiKhoanService() {
        docTuFile();
    }

    public List<TaiKhoan> getAll() {
        return danhSach;
    }

    public void themTaiKhoan(TaiKhoan tk) {
        danhSach.add(tk);
        ghiVaoFile();
    }

    public TaiKhoan timTaiKhoan(String username, String password) {
        return danhSach.stream()
            .filter(tk -> tk.getTenDangNhap().equals(username) && tk.getMatKhau().equals(password))
            .findFirst()
            .orElse(null);
    }

    public void docTuFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            JAXBContext context = JAXBContext.newInstance(DanhSachTaiKhoan.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DanhSachTaiKhoan wrapper = (DanhSachTaiKhoan) unmarshaller.unmarshal(file);
            danhSach = wrapper.getTaiKhoans();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void ghiVaoFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(DanhSachTaiKhoan.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            DanhSachTaiKhoan wrapper = new DanhSachTaiKhoan();
            wrapper.setTaiKhoans(danhSach);

            marshaller.marshal(wrapper, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public boolean kiemTraDangNhap(String ten, String matKhau) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Wrapper class
    @XmlRootElement(name = "DanhSachTaiKhoan")
    public static class DanhSachTaiKhoan {
        private List<TaiKhoan> taiKhoans = new ArrayList<>();

        @XmlElement(name = "TaiKhoan")
        public List<TaiKhoan> getTaiKhoans() {
            return taiKhoans;
        }

        public void setTaiKhoans(List<TaiKhoan> taiKhoans) {
            this.taiKhoans = taiKhoans;
        }
    }
}
