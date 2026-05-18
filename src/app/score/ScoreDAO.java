package app.score;

import app.config.KoneksiDB;
import java.sql.*;
import java.util.*;

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

    // ── READ: Ambil top 5 skor tertinggi dengan rumus tersinkronisasi ────────
    public List<Score> getTop5(int idLevel) {
        List<Score> list = new ArrayList<>();
        // Rumus disamakan dengan GameEngine: GREATEST(0, Base + TimeBonus - MovePenalty)
        String sql = "SELECT s.id_score, u.nama_lengkap, l.nama_level, "
                + "GREATEST(0, (((l.baris * l.kolom) / 2) * 100) + (s.waktu_selesai * 10) - (s.jumlah_langkah * 5)) AS skor, "
                + "s.tanggal_main "
                + "FROM tb_score s "
                + "JOIN tb_user u ON s.id_user = u.id_user "
                + "JOIN tb_level l ON s.id_level = l.id_level "
                + "WHERE s.id_level = ? "
                + "ORDER BY skor DESC LIMIT 5";
                
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idLevel);
            ResultSet rs = pst.executeQuery();
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

    // ── CREATE: Insert skor baru ──────────────────────────────────────────────
    public boolean insert(int idUser, int idLevel, int waktuSelesai, int jumlahLangkah) {
        int skorBaru = hitungSkorRelatif(waktuSelesai, jumlahLangkah);
        int skorLama = getSkorPemain(idUser, idLevel);

        if (skorLama >= -999999) { // Cek apakah pemain sudah memiliki data
            // UPDATE jika skor baru lebih tinggi secara relatif
            if (skorBaru > skorLama) {
                return update(idUser, idLevel, waktuSelesai, jumlahLangkah);
            } else {
                return true; 
            }
        }

        // Belum ada data → INSERT baru
        String sql = "INSERT INTO tb_score (id_user, id_level, waktu_selesai, jumlah_langkah, tanggal_main) "
                + "VALUES (?, ?, ?, ?, NOW())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ps.setInt(2, idLevel);
            ps.setInt(3, waktuSelesai);
            ps.setInt(4, jumlahLangkah);
            ps.executeUpdate();

            hapusSkorTerkecil(idLevel); 
            return true;
        } catch (SQLException e) {
            System.out.println("Gagal insert skor: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE: Perbarui skor jika lebih tinggi ───────────────────────────────
    private boolean update(int idUser, int idLevel, int waktuSelesai, int jumlahLangkah) {
        String sql = "UPDATE tb_score SET waktu_selesai=?, jumlah_langkah=?, tanggal_main=NOW() "
                + "WHERE id_user=? AND id_level=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, waktuSelesai);
            ps.setInt(2, jumlahLangkah);
            ps.setInt(3, idUser);
            ps.setInt(4, idLevel);
            ps.executeUpdate();

            hapusSkorTerkecil(idLevel);
            return true;
        } catch (SQLException e) {
            System.out.println("Gagal update skor: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE: Hapus skor di luar top 5 ─────────────────────────────────────
    public void hapusSkorTerkecil(int idLevel) {
        String sql = "DELETE FROM tb_score WHERE id_level = ? AND id_score NOT IN ("
                + "  SELECT id_score FROM ("
                + "    SELECT s.id_score FROM tb_score s "
                + "    WHERE s.id_level = ? "
                + "    ORDER BY (s.waktu_selesai * 10) - (s.jumlah_langkah * 5) DESC "
                + "    LIMIT 5"
                + "  ) AS top5"
                + ")";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLevel);
            ps.setInt(2, idLevel);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal hapus skor terkecil: " + e.getMessage());
        }
    }

    // ── Helper: Hitung jumlah data di tb_score ────────────────────────────────
    public int hitungJumlahSkor() {
        String sql = "SELECT COUNT(*) FROM tb_score";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal hitung skor: " + e.getMessage());
        }
        return 0;
    }

    // ── Helper: Ambil skor relatif pemain yang sudah ada ──────────
    private int getSkorPemain(int idUser, int idLevel) {
        String sql = "SELECT waktu_selesai, jumlah_langkah FROM tb_score "
                + "WHERE id_user=? AND id_level=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ps.setInt(2, idLevel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int waktu = rs.getInt("waktu_selesai");
                int langkah = rs.getInt("jumlah_langkah");
                return hitungSkorRelatif(waktu, langkah);
            }
        } catch (SQLException e) {
            System.out.println("Gagal cek skor pemain: " + e.getMessage());
        }
        return -9999999; // Indikator belum ada data
    }

    // ── Helper: Rumus hitung skor (untuk komparasi pemecahan rekor) ───────────
    private int hitungSkorRelatif(int waktuSelesai, int jumlahLangkah) {
        // Karena Base Score untuk level yang sama adalah konstan, 
        // kita hanya perlu mengkomparasi nilai bonus dan pinalti.
        return (waktuSelesai * 10) - (jumlahLangkah * 5);
    }
}