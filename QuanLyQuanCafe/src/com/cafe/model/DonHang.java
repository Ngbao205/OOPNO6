package com.cafe.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "DonHang")
public class DonHang {
    private int maDon;
    private Date ngayLap;
    private List<ChiTietDon> chiTietDons = new ArrayList<>();

    public DonHang() {}

    public DonHang(int maDon, Date ngayLap) {
        this.maDon = maDon;
        this.ngayLap = ngayLap;
    }

    @XmlElement
    public int getMaDon() {
        return maDon;
    }

    public void setMaDon(int maDon) {
        this.maDon = maDon;
    }

    @XmlElement
    @XmlSchemaType(name = "date")
    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    @XmlElementWrapper(name = "DanhSachChiTiet")
    @XmlElement(name = "ChiTiet")
    public List<ChiTietDon> getChiTietDons() {
        return chiTietDons;
    }

    public void setChiTietDons(List<ChiTietDon> chiTietDons) {
        this.chiTietDons = chiTietDons;
    }

    public double tongTien() {
        return chiTietDons.stream().mapToDouble(ChiTietDon::tinhTien).sum();
    }

    @Override
    public String toString() {
        return "Ma: " + maDon + " | Ngay: " + ngayLap + " | Tong: " + String.format("%,.0f VND", tongTien());
    }
}
