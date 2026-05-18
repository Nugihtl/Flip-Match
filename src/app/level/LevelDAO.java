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

    public LevelDAO() {
        this.conn = KoneksiDB.getKoneksi();
    }
    
    public java.util.List<Level> getAllLevel() {
        java.util.List<Level> list = new java.util.ArrayList<>();
        String sql = "SELECT id_level, nama_level, baris, kolom, waktu_detik FROM tb_level";
        
        try (java.sql.PreparedStatement pst = conn.prepareStatement(sql);
             java.sql.ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                Level l = new Level();
                l.setIdLevel(rs.getInt("id_level"));
                l.setNamaLevel(rs.getString("nama_level"));
                l.setBaris(rs.getInt("baris"));
                l.setKolom(rs.getInt("kolom"));
                l.setWaktuDetik(rs.getInt("waktu_detik"));
                list.add(l);
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil semua level: " + e.getMessage());
        }
        return list;
    }
    
    public List<Level> getAll() {
        List<Level> list = new ArrayList<>();
        String sql = "SELECT l.*, t.nama_theme FROM tb_level l " +
                     "JOIN tb_theme t ON l.id_theme = t.id_theme";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Level lvl = new Level();
                lvl.setIdLevel(rs.getInt("id_level"));
                lvl.setIdTheme(rs.getInt("id_theme"));
                lvl.setNamaLevel(rs.getString("nama_level"));
                lvl.setBaris(rs.getInt("baris"));
                lvl.setKolom(rs.getInt("kolom"));
                lvl.setWaktuDetik(rs.getInt("waktu_detik"));
                lvl.setNamaTheme(rs.getString("nama_theme")); 
                list.add(lvl);
            }
        } catch (Exception e) {
            System.out.println("Error getAll Level: " + e.getMessage());
        }
        return list;
    }

    public boolean insert(Level level) {
        String sql = "INSERT INTO tb_level (id_theme, nama_level, baris, kolom, waktu_detik) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, level.getIdTheme());
            ps.setString(2, level.getNamaLevel());
            ps.setInt(3, level.getBaris());
            ps.setInt(4, level.getKolom());
            ps.setInt(5, level.getWaktuDetik());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error insert Level: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Level level) {
        String sql = "UPDATE tb_level SET id_theme = ?, nama_level = ?, baris = ?, kolom = ?, waktu_detik = ? WHERE id_level = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, level.getIdTheme());
            ps.setString(2, level.getNamaLevel());
            ps.setInt(3, level.getBaris());
            ps.setInt(4, level.getKolom());
            ps.setInt(5, level.getWaktuDetik());
            ps.setInt(6, level.getIdLevel());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error update Level: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idLevel) {
        String sql = "DELETE FROM tb_level WHERE id_level = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLevel);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error delete Level: " + e.getMessage());
            return false;
        }
    } 

    public Level getLevelById(int idLevel) {

       Level level = null;

       String sql = "SELECT * FROM tb_level WHERE id_level = ?";

       try {

           PreparedStatement ps = conn.prepareStatement(sql);

           ps.setInt(1, idLevel);

           ResultSet rs = ps.executeQuery();

           if(rs.next()) {

               level = new Level();

               level.setIdLevel(rs.getInt("id_level"));
               level.setNamaLevel(rs.getString("nama_level"));
               level.setBaris(rs.getInt("baris"));
               level.setKolom(rs.getInt("kolom"));
               level.setWaktuDetik(rs.getInt("waktu_detik"));
           }

       } catch (Exception e) {
           System.out.println("Gagal ambil level: " + e.getMessage());
       }

       return level;
   }
}

