/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.level;

import app.config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nugih
 */
public class LevelDAO {
    private Connection conn;
    
    public LevelDAO(){
    conn = KoneksiDB.getKoneksi();
    }
    
    //buat createnya
    public boolean insert(Level level) {
        String sql = "INSERT INTO tb_level (nama_level, baris, kolom, waktu_detik) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, level.getNamaLevel());
            pst.setInt(2, level.getBaris());
            pst.setInt(3, level.getKolom());
            pst.setInt(4, level.getWaktuDetik());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal Insert: " + e.getMessage());
            return false;
        }
    }
    
    // buat readnya
    public List<Level> getAll() {
        List<Level> listLevel = new ArrayList<>();
        String sql = "SELECT * FROM tb_level";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Level lvl = new Level();
                lvl.setIdLevel(rs.getInt("id_level"));
                lvl.setNamaLevel(rs.getString("nama_level"));
                lvl.setBaris(rs.getInt("baris"));
                lvl.setKolom(rs.getInt("kolom"));
                lvl.setWaktuDetik(rs.getInt("waktu_detik"));
                listLevel.add(lvl);
            }
        } catch (Exception e) {
            System.out.println("Gagal Read: " + e.getMessage());
        }
        return listLevel;
    }
    
    // update
    public boolean update(Level level) {
        String sql = "UPDATE tb_level SET nama_level = ?, baris = ?, kolom = ?, waktu_detik = ? WHERE id_level = ?";
        try (java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, level.getNamaLevel());
            pst.setInt(2, level.getBaris());
            pst.setInt(3, level.getKolom());
            pst.setInt(4, level.getWaktuDetik());
            pst.setInt(5, level.getIdLevel());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal Update: " + e.getMessage());
            return false;
        }
    }

    //buat si delete
    public boolean delete(int id) {
        String sql = "DELETE FROM tb_level WHERE id_level = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal Delete: " + e.getMessage());
            return false;
        }
    } 
}
