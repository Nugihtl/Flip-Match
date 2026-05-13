/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nugih
 */
public class KoneksiDB {
    
    // Variabel statis untuk menyimpan instansi koneksi
    private static Connection koneksi;

    public static Connection getKoneksi() {
        // Pengecekan apakah koneksi sudah pernah dibuat sebelumnya
        if (koneksi == null) {
            try {
                // Parameter koneksi basis data lokal
                String url = "jdbc:mysql://localhost:3306/db_memory_match";
                String user = "root";
                String password = ""; // Kosongkan jika menggunakan konfigurasi bawaan XAMPP/MySQL
                
                // Melakukan registrasi driver dan membuka koneksi
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Status: Koneksi ke basis data db_memory_match berhasil.");
                
            } catch (SQLException e) {
                System.out.println("Status: Koneksi ke basis data gagal.");
                System.out.println("Pesan Error: " + e.getMessage());
            }
        }
        return koneksi;
    }

    // Fungsi main() ditambahkan secara khusus untuk pengujian koneksi yang terisolasi
    public static void main(String[] args) {
        // Menjalankan fungsi koneksi secara langsung tanpa harus membuka form GUI
        getKoneksi();
    }
}
