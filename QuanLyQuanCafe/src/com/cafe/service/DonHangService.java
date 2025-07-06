package com.cafe.service;

import com.cafe.model.DonHang;
import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class DonHangService {
    private static final String FILE_NAME = "DonHang.xml";
    private List<DonHang> danhSach = new ArrayList<>();

    public DonHangService() {
        docTuFile();
    }

    public List<DonHang> getAll() {
        return danhSach;
    }

    public void themDon(DonHang don) {
        danhSach.add(don);
        ghiVaoFile();
    }

    public void docTuFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            JAXBContext context = JAXBContext.newInstance(DanhSachDonHang.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DanhSachDonHang wrapper = (DanhSachDonHang) unmarshaller.unmarshal(file);
            danhSach = wrapper.getDonHangs();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void ghiVaoFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(DanhSachDonHang.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            DanhSachDonHang wrapper = new DanhSachDonHang();
            wrapper.setDonHangs(danhSach);

            marshaller.marshal(wrapper, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void themDonHang(DonHang don) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @XmlRootElement(name = "DanhSachDonHang")
    public static class DanhSachDonHang {
        private List<DonHang> donHangs = new ArrayList<>();

        @XmlElement(name = "DonHang")
        public List<DonHang> getDonHangs() {
            return donHangs;
        }

        public void setDonHangs(List<DonHang> donHangs) {
            this.donHangs = donHangs;
        }
    }
}
