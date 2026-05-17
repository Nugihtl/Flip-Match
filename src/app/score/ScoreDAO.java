/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.score;

import app.config.KoneksiDB;
import java.sql.*;
import java.util.*;

/**
 *
 * @author ASUS
 */
public class ScoreDAO {

    private Connection conn;

    public ScoreDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver tidak ditemukan: " + e.getMessage());
        }
        conn = KoneksiDB.getKoneksi();
    }

    // Ambil top 5 skor tertinggi
    public List<Score> getTop5() {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT s.id_score, u.nama_lengkap, l.nama_level, "
                + "(s.waktu_selesai * 100) - (s.jumlah_langkah * 10) AS skor, "
                + "s.tanggal_main "
                + "FROM tb_score s "
                + "JOIN tb_user u ON s.id_user = u.id_user "
                + "JOIN tb_level l ON s.id_level = l.id_level "
                + "ORDER BY skor DESC LIMIT 5";
        try (PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Score s = new Score();
                s.setIdScore(rs.getInt("id_score"));
                s.setNamaLengkap(rs.getString("nama_lengkap"));
                s.setNamaLevel(rs.getString("nama_level"));
                s.setSkor(rs.getInt("skor"));
                s.setTanggalMain(rs.getString("tanggal_main"));
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println("Gagal ambil top 5: " + e.getMessage());
        }
        return list;
    }

    // Insert skor baru
    public boolean insert(int idUser, int idLevel, int waktuSelesai, int jumlahLangkah) {
        String sql = "INSERT INTO tb_score (id_user, id_level, waktu_selesai, jumlah_langkah) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idUser);
            pst.setInt(2, idLevel);
            pst.setInt(3, waktuSelesai);
            pst.setInt(4, jumlahLangkah);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal insert skor: " + e.getMessage());
            return false;
        }
    }

    // Hapus skor terkecil jika sudah ada 5 data
    public void hapusSkorTerkecil() {
        String sql = "DELETE FROM tb_score WHERE id_score = "
                + "(SELECT id_score FROM tb_score ORDER BY "
                + "(waktu_selesai * 100) - (jumlah_langkah * 10) ASC LIMIT 1)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Gagal hapus skor terkecil: " + e.getMessage());
        }
    }

    // Cek jumlah data di tb_score
    public int hitungJumlahSkor() {
        String sql = "SELECT COUNT(*) FROM tb_score";
        try (PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Gagal hitung skor: " + e.getMessage());
        }
        return 0;
    }
}
