/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.score;

/**
 *
 * @author ASUS
 */


public class Score {

    // Atribut/Variabel: Tempat untuk menyimpan data
    private int idScore;
    private String namaLengkap;
    private String namaLevel;
    private int skor;
    private String tanggalMain;

    // Constructor Kosong: Digunakan untuk membuat objek 'kosong'
    // agar kita bisa mengisi datanya belakangan menggunakan method 'set'.
    public Score() {
    }

    // Getter & Setter untuk idScore
    // Getter: Mengambil id
    public int getIdScore() {
        return idScore;
    }

    // Setter: Memasukkan id
    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    // Getter: Mengambil nama lengkap
    public String getNamaLengkap() {
        return namaLengkap;
    }

    // Setter: Memasukan nama lengkap
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    // Getter: Mengambil nama lavel
    public String getNamaLevel() {
        return namaLevel;
    }

    // Setter: Memasukan nama lavel
    public void setNamaLevel(String namaLevel) {
        this.namaLevel = namaLevel;
    }

    // Getter: Mengambil skor
    public int getSkor() {
        return skor;
    }

    // Setter: Memasukan skor
    public void setSkor(int skor) {
        this.skor = skor;
    }

    // Getter: Mengambil tanggal main
    public String getTanggalMain() {
        return tanggalMain;
    }

    // Setter: Memasukan tanggal main
    public void setTanggalMain(String tanggalMain) {
        this.tanggalMain = tanggalMain;
    }
}
