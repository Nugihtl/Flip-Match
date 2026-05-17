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

    private int idScore;
    private String namaLengkap;
    private String namaLevel;
    private int skor;
    private String tanggalMain;

    public Score() {
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNamaLevel() {
        return namaLevel;
    }

    public void setNamaLevel(String namaLevel) {
        this.namaLevel = namaLevel;
    }

    public int getSkor() {
        return skor;
    }

    public void setSkor(int skor) {
        this.skor = skor;
    }

    public String getTanggalMain() {
        return tanggalMain;
    }

    public void setTanggalMain(String tanggalMain) {
        this.tanggalMain = tanggalMain;
    }
}
