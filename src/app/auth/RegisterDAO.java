/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.auth;

import app.config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Siti Amalia Putri
 */
public class RegisterDAO {
 Connection conn;

    public RegisterDAO() {
        conn = KoneksiDB.getKoneksi();
    }

    public boolean register(String nama, String username, String password) {

        try {

            String sql = "INSERT INTO tb_user(username, password, nama_lengkap) VALUES(?,?,?)";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, nama);

            pst.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Register Error : " + e.getMessage());
            return false;
        }
    }
}
