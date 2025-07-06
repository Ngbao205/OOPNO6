package com.cafe.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChiTietDon")
public class ChiTietDon {
    private Mon mon;
    private int soLuong;

    public ChiTietDon() {}

    public ChiTietDon(Mon mon, int soLuong) {
        this.mon = mon;
        this.soLuong = soLuong;
    }

    @XmlElement
    public Mon getMon() {
        return mon;
    }

    public void setMon(Mon mon) {
        this.mon = mon;
    }

    @XmlElement
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double tinhTien() {
        return mon.getGia() * soLuong;
    }

    @Override
    public String toString() {
        return mon.getTenMon() + " x " + soLuong + " = " + String.format("%,.0f VNĐ", tinhTien());
    }
}
