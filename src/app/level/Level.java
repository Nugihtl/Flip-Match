/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.level;

/**
 *
 * @author nugih
 */
public class Level {
    private int idLevel;
    private String namaLevel;
    private int baris;
    private int kolom;
    private int waktuDetik;

    // Constructor
    public Level() {}

    public Level(String namaLevel, int baris, int kolom, int waktuDetik) {
        this.namaLevel = namaLevel;
        this.baris = baris;
        this.kolom = kolom;
        this.waktuDetik = waktuDetik;
    }

    // Getter dan Setter
    public int getIdLevel() { return idLevel; }
    public void setIdLevel(int idLevel) { this.idLevel = idLevel; }

    public String getNamaLevel() { return namaLevel; }
    public void setNamaLevel(String namaLevel) { this.namaLevel = namaLevel; }

    public int getBaris() { return baris; }
    public void setBaris(int baris) { this.baris = baris; }

    public int getKolom() { return kolom; }
    public void setKolom(int kolom) { this.kolom = kolom; }

    public int getWaktuDetik() { return waktuDetik; }
    public void setWaktuDetik(int waktuDetik) { this.waktuDetik = waktuDetik; }
}
