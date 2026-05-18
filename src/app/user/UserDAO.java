/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.user;

import app.config.KoneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Siti Amalia Putri
 */
public class UserDAO {
     Connection conn;

    public UserDAO() {
        conn = KoneksiDB.getKoneksi();
    }

    public boolean insertUser(User user) {

        String sql = "INSERT INTO tb_user(username, password, nama_lengkap) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getNamaLengkap());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Insert user gagal : " + e.getMessage());
            return false;
        }
    }

     public List<User> getAllUser() {

        List<User> list = new ArrayList<>();

        String sql = "SELECT id_user, username, nama_lengkap FROM tb_user";

        try (
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                User user = new User();

                user.setIdUser(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setNamaLengkap(rs.getString("nama_lengkap"));

                list.add(user);
            }

        } catch (Exception e) {
            System.out.println("Get user gagal : " + e.getMessage());
        }

        return list;
    }

    public boolean updateUser(User user) {

        String sql = "UPDATE tb_user SET username=?, nama_lengkap=? WHERE id_user=?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getNamaLengkap());
            pst.setInt(3, user.getIdUser());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Update user gagal : " + e.getMessage());
            return false;
        }
    }

    public boolean updatePassword(int idUser, String password) {

        String sql = "UPDATE tb_user SET password=? WHERE id_user=?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, password);
            pst.setInt(2, idUser);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Update password gagal : " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int idUser) {

        String sql = "DELETE FROM tb_user WHERE id_user=?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idUser);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Delete user gagal : " + e.getMessage());
            return false;
        }
    }
}
