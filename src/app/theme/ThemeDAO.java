/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.theme;

import app.config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aziza putri amelia
 */
public class ThemeDAO {
    private Connection conn;
    
    public ThemeDAO(){
        conn = KoneksiDB.getKoneksi();
    }

    // TAMBAH
    public void tambahTheme(theme theme){
        String sql = "INSERT INTO tb_theme(nama_theme, folder_path) VALUES (?, ?)";
    
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, theme.getNamaTheme());
            stmt.setString(2, theme.getFolderPath());
            
            stmt.executeUpdate();
            System.out.println("Data theme berhasil ditambahkan.");
        } catch (Exception e) {
            System.out.println("Gagal menambahkan theme: " + e.getMessage());
        }
    }
    
    // MENAMPILKAN DATA
    public List<theme> getAllTheme() {
        List<theme> listTheme = new ArrayList<>();
        String sql = "SELECT * FROM tb_theme";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                theme theme = new theme();
                
                theme.setIdTheme(rs.getInt("id_theme"));
                theme.setNamaTheme(rs.getString("nama_theme"));
                theme.setFolderPath(rs.getString("folder_path"));
                
                listTheme.add(theme);
            }
        } catch (Exception e){
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }
        
        return listTheme;
    }

    // UPDATE
    public void updateTheme(theme theme){
        String sql = "UPDATE tb_theme SET nama_theme=?, folder_path=? WHERE id_theme=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, theme.getNamaTheme());
            stmt.setString(2, theme.getFolderPath());
            stmt.setInt(3, theme.getIdTheme());
            stmt.executeUpdate();
            
            System.out.println("Data theme berhasil diupdate");
        } catch (Exception e){
            System.out.println("Gagal update theme: " + e.getMessage());
        }
    }
    
    // HAPUS
    public void deleteTheme(int idTheme){
        String sql = "DELETE FROM tb_theme WHERE id_theme=?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idTheme);
            stmt.executeUpdate();
            System.out.println("Data theme berhasil dihapus");
        } catch (Exception e) {
            System.out.println("Gagal hapus theme: " + e.getMessage());
        }
    }
    
}