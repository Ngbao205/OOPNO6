package com.cafe.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Mon")
public class Mon {
    private String tenMon;
    private double gia;

    public Mon() {}

    public Mon(String tenMon, double gia) {
        this.tenMon = tenMon;
        this.gia = gia;
    }

    @XmlElement
    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    @XmlElement
    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return tenMon + " - " + String.format("%,.0f VNĐ", gia);
    }
}
