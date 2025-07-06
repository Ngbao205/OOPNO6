package com.cafe.util;

import java.io.*;

public class IDGenerator {
    private static final String FILE = "id.txt";

    // Lấy ID hiện tại từ file, tăng lên 1 và lưu lại
    public static int getNextID() {
        int id = 0;
        try {
            File f = new File(FILE);
            if (f.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line = br.readLine();
                    if (line != null) id = Integer.parseInt(line.trim());
                }
            }
            id++;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(String.valueOf(id));
            }
        } catch (IOException | NumberFormatException e) {
        }
        return id;
    }

    // Reset ID về 0 (có thể dùng trong debug hoặc xóa toàn bộ dữ liệu)
    public static void resetID() {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
                bw.write("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int taoIDDonHang() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
