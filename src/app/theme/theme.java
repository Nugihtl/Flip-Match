/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.theme;

/**
 *
 * @author aziza putri amelia
 */
public class theme {
    
    private int idTheme;
    private String namaTheme;
    private String folderPath;
    
    public theme(){
        
    }
    
    public theme(int idTheme, String namaTheme, String folderPath) {
        this.idTheme = idTheme;
        this.namaTheme = namaTheme;
        this.folderPath = folderPath;
    }
    
    public int getIdTheme() {
        return idTheme;
    }
    
    public String getNamaTheme() {
    return namaTheme;
    }
    
    public void setNamaTheme(String namaTheme) {
        this.namaTheme = namaTheme;
    }
    
    public String getFolderPath() {
        return folderPath;
    }
    
    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }

    public void setIdTheme(int idTheme) {
    this.idTheme = idTheme;
    }
}
