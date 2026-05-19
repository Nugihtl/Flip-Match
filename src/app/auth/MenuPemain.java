/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package app.auth;

import app.auth.Session;
import app.game.GameEngine;
import app.level.Level;
import app.level.LevelDAO;
import app.theme.ThemeDAO;
import app.theme.theme;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 *
 * @author Siti Amalia Putri
 */
public class MenuPemain extends javax.swing.JFrame {

    private Level selectedLevel;

    private LevelDAO levelDAO = new LevelDAO();

    private ThemeDAO themeDAO = new ThemeDAO();
    /**
     * Creates new form MenuPemanin
     */
    public MenuPemain() {
        initComponents();
        setTitle("Flip & Match");
        
        // Panggil fungsi generasi komponen dinamis
        tampilkanLevelDinamis();
        tampilkanThemeDinamis();

        java.net.URL logoURL = getClass().getResource("logo-match.png");
        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            setIconImage(scaledImage);
        }
        
    }
    
    private void tampilkanLevelDinamis() {
        panelLevelContainer.removeAll();
        // Susun mendatar: 1 baris, kolom otomatis mengikuti jumlah data
        panelLevelContainer.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        try {
            java.util.List<Level> daftarLevel = levelDAO.getAllLevel();

            for (Level lvl : daftarLevel) {
                javax.swing.JButton btn = new javax.swing.JButton(lvl.getNamaLevel());
                
                // Atur visualisasi tombol level
                btn.setBackground(new java.awt.Color(102, 51, 0));
                btn.setForeground(new java.awt.Color(255, 255, 255));
                btn.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12));
                btn.setPreferredSize(new java.awt.Dimension(131, 30));
                btn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

                // Logika klik tombol level
                btn.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        selectedLevel = lvl;
                        JOptionPane.showMessageDialog(MenuPemain.this, "Level " + lvl.getNamaLevel() + " dipilih");
                    }
                });

                panelLevelContainer.add(btn);
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat level dinamis: " + e.getMessage());
        }

        panelLevelContainer.revalidate();
        panelLevelContainer.repaint();
    }
    
    private void tampilkanThemeDinamis() {
        panelThemeContainer.removeAll();
        // Susun menurun: baris otomatis bertambah, 1 kolom tetap
        panelThemeContainer.setLayout(new java.awt.GridLayout(0, 1, 0, 20));

        try {
            java.util.List<theme> daftarTheme = themeDAO.getAllTheme();

            for (theme thm : daftarTheme) {
                javax.swing.JButton btn = new javax.swing.JButton(thm.getNamaTheme());
                
                // Atur visualisasi tombol tema
                btn.setBackground(new java.awt.Color(102, 51, 0));
                btn.setForeground(new java.awt.Color(255, 255, 255));
                btn.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12));
                btn.setPreferredSize(new java.awt.Dimension(253, 30));
                btn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

                // Logika klik tombol tema dan peluncuran GameEngine
                btn.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (selectedLevel == null) {
                            JOptionPane.showMessageDialog(MenuPemain.this, 
                                "Silakan pilih tingkat kesulitan (Level) terlebih dahulu!", 
                                "Peringatan", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        app.game.GameEngine game = new app.game.GameEngine(
                            selectedLevel.getIdLevel(),
                            thm.getNamaTheme(),
                            thm.getFolderPath(),
                            selectedLevel.getBaris(),
                            selectedLevel.getKolom(),
                            selectedLevel.getWaktuDetik()
                        );
                        game.setVisible(true);
                    }
                });

                panelThemeContainer.add(btn);
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat tema dinamis: " + e.getMessage());
        }

        panelThemeContainer.revalidate();
        panelThemeContainer.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelLevelContainer = new javax.swing.JPanel();
        panelThemeContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setSize(new java.awt.Dimension(1280, 720));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 720));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(204, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 0));
        jLabel1.setText("Selamat Datang Player");

        jLabel10.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(45, 22, 0));
        jLabel10.setText("Pilih menu dibawah untuk melanjutkan!");

        panelLevelContainer.setOpaque(false);

        javax.swing.GroupLayout panelLevelContainerLayout = new javax.swing.GroupLayout(panelLevelContainer);
        panelLevelContainer.setLayout(panelLevelContainerLayout);
        panelLevelContainerLayout.setHorizontalGroup(
            panelLevelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );
        panelLevelContainerLayout.setVerticalGroup(
            panelLevelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        panelThemeContainer.setOpaque(false);

        javax.swing.GroupLayout panelThemeContainerLayout = new javax.swing.GroupLayout(panelThemeContainer);
        panelThemeContainer.setLayout(panelThemeContainerLayout);
        panelThemeContainerLayout.setHorizontalGroup(
            panelThemeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );
        panelThemeContainerLayout.setVerticalGroup(
            panelThemeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelThemeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelLevelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(242, 242, 242))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(panelThemeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(panelLevelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(306, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(300, 300, 300))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPemain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPemain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPemain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPemain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPemain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelLevelContainer;
    private javax.swing.JPanel panelThemeContainer;
    // End of variables declaration//GEN-END:variables
}
