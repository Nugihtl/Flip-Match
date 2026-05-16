/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.auth;

import app.config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Siti Amalia Putri
 */
public class LoginDAO {
    Connection conn;

    public LoginDAO() {
        conn = KoneksiDB.getKoneksi();
    }

    public ResultSet loginUser(String username, String password) {

        try {

            String sql = "SELECT * FROM tb_user WHERE username=? AND password=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            return rs;

        } catch (Exception e) {

            System.out.println("Login Error : " + e.getMessage());

            return null;
        }
    }
}
