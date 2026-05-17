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
    private int idTheme; 
    private String namaLevel;
    private int baris;
    private int kolom;
    private int waktuDetik;
    private String namaTheme; 

    public Level() {}

    public Level(int idTheme, String namaLevel, int baris, int kolom, int waktuDetik) {
        this.idTheme = idTheme;
        this.namaLevel = namaLevel;
        this.baris = baris;
        this.kolom = kolom;
        this.waktuDetik = waktuDetik;
    }

    public int getIdLevel() { return idLevel; }
    public void setIdLevel(int idLevel) { this.idLevel = idLevel; }

    public int getIdTheme() { return idTheme; }
    public void setIdTheme(int idTheme) { this.idTheme = idTheme; }

    public String getNamaLevel() { return namaLevel; }
    public void setNamaLevel(String namaLevel) { this.namaLevel = namaLevel; }

    public int getBaris() { return baris; }
    public void setBaris(int baris) { this.baris = baris; }

    public int getKolom() { return kolom; }
    public void setKolom(int kolom) { this.kolom = kolom; }

    public int getWaktuDetik() { return waktuDetik; }
    public void setWaktuDetik(int waktuDetik) { this.waktuDetik = waktuDetik; }

    public String getNamaTheme() { return namaTheme; }
    public void setNamaTheme(String namaTheme) { this.namaTheme = namaTheme; }
}
