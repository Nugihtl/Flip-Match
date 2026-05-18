/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.game;

import app.auth.Session;
import app.score.ScoreDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author Siti Amalia Putri
 */public class GameEngine extends JFrame {

    private final int idLevel;
    private final String themeName;
    private final String themeFolder;
    private final int rows;
    private final int cols;
    private final int initialTime;

    private JPanel topPanel;
    private JPanel boardPanel;
    private JLabel lblPlayer;
    private JLabel lblTheme;
    private JLabel lblLevel;
    private JLabel lblTime;
    private JLabel lblMoves;

    private final List<GameCard> cards = new ArrayList<>();
    private final Map<String, ImageIcon> frontIconCache = new HashMap<>();

    private Timer countdownTimer;
    private int timeLeft;
    private int moves;
    private int matchedPairs;
    private final int totalPairs;

    private GameCard firstCard = null;
    private GameCard secondCard = null;
    private boolean comparing = false;
    private boolean gameEnded = false;

    private int cardWidth;
    private int cardHeight;

    private ImageIcon backIcon;

    private final ScoreDAO scoreDAO = new ScoreDAO();

    public GameEngine(int idLevel, String themeName, String themeFolder, int rows, int cols, int initialTime) {
        this.idLevel = idLevel;
        this.themeName = themeName;
        this.themeFolder = themeFolder;
        this.rows = rows;
        this.cols = cols;
        this.initialTime = initialTime;
        this.totalPairs = (rows * cols) / 2;

        if ((rows * cols) % 2 != 0) {
            throw new IllegalArgumentException("Jumlah kartu harus genap.");
        }

        setCardSize();
        initUI();
        loadThemeAndBuildBoard();
        startCountdown();
    }
    
    private void setCardSize() {

        if(rows == 4 && cols == 4) {

            cardWidth = 150;
            cardHeight = 210;

        } else if(rows == 4 && cols == 5) {

            cardWidth = 120;
            cardHeight = 170;

        } else if(rows == 6 && cols == 6) {

            cardWidth = 80;
            cardHeight = 120;
            
        } else {
            
        cardWidth = 100;
        cardHeight = 140;
        }
    }

    private void initUI() {
        setTitle("Flip & Match");
        java.net.URL logoURL = getClass().getResource("logo-match.png");
        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            // Resize ke 64x64
            Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            setIconImage(scaledImage);
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        topPanel = new JPanel(new GridLayout(2, 3, 10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(245, 245, 245));

        lblPlayer = new JLabel("Player: " + Session.username);
        lblTheme = new JLabel("Theme: " + themeName);
        lblLevel = new JLabel("Level: " + rows + "x" + cols);
        lblTime = new JLabel("Time: " + initialTime + "s");
        lblMoves = new JLabel("Moves: 0");

        Font f = new Font("SansSerif", Font.BOLD, 16);
        lblPlayer.setFont(f);
        lblTheme.setFont(f);
        lblLevel.setFont(f);
        lblTime.setFont(f);
        lblMoves.setFont(f);

        topPanel.add(lblPlayer);
        topPanel.add(lblTheme);
        topPanel.add(lblLevel);
        topPanel.add(lblTime);
        topPanel.add(lblMoves);
        topPanel.add(new JLabel("")); // spacer

        boardPanel = new JPanel(new GridLayout(rows, cols, 8, 8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardPanel.setBackground(Color.WHITE);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        backIcon = createBackIcon(cardWidth, cardHeight);

        setVisible(true);
    }

    private void loadThemeAndBuildBoard() {
        List<String> imageFiles = loadImageFilesFromFolder(themeFolder);

        if (imageFiles.size() < totalPairs) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gambar tema tidak cukup.\nButuh minimal " + totalPairs + " gambar unik.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            dispose();
            return;
        }

        Collections.shuffle(imageFiles);

        List<String> selected = new ArrayList<>(imageFiles.subList(0, totalPairs));

        List<String> pairedImages = new ArrayList<>();
        for (String path : selected) {
            pairedImages.add(path);
            pairedImages.add(path);
        }

        Collections.shuffle(pairedImages);

        boardPanel.removeAll();
        cards.clear();

        for (String imagePath : pairedImages) {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(cardWidth, cardHeight));
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setFocusable(false);
            btn.setBackground(Color.WHITE);
            btn.setIcon(backIcon);

            GameCard card = new GameCard(imagePath, btn);

            btn.addActionListener((ActionEvent e) -> handleCardClick(card));

            cards.add(card);
            boardPanel.add(btn);
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private List<String> loadImageFilesFromFolder(String folderPath) {
        List<String> result = new ArrayList<>();

        try {
            // Bersihkan slash di depan kalau ada
            String cleanPath = folderPath.startsWith("/") ? folderPath.substring(1) : folderPath;

            java.net.URL folderURL = getClass().getClassLoader().getResource(cleanPath);

            if (folderURL == null) {
                throw new IllegalArgumentException("Folder tema tidak ditemukan: " + cleanPath);
            }

            Path folder = Paths.get(folderURL.toURI());

            if (!Files.isDirectory(folder)) {
                throw new IllegalArgumentException("Bukan folder: " + cleanPath);
            }

            try (var stream = Files.list(folder)) {
                stream.filter(Files::isRegularFile)
                      .filter(this::isImageFile)
                      .forEach(path -> result.add(path.toString()));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal membaca folder tema:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return result;
    }

    private boolean isImageFile(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        return fileName.endsWith(".png")
                || fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")
                || fileName.endsWith(".gif");
    }

    private void handleCardClick(GameCard card) {
        if (gameEnded || comparing || card.isMatched() || card.isOpen()) {
            return;
        }

        showFront(card);
        card.setOpen(true);

        if (firstCard == null) {
            firstCard = card;
            return;
        }

        secondCard = card;
        moves++;
        lblMoves.setText("Moves: " + moves);

        comparing = true;
        setBoardEnabled(false);

        Timer compareTimer = new Timer(650, e -> {
            ((Timer) e.getSource()).stop();
            checkMatch();
        });
        compareTimer.setRepeats(false);
        compareTimer.start();
    }

    private void checkMatch() {
        if (firstCard == null || secondCard == null) {
            comparing = false;
            setBoardEnabled(true);
            return;
        }

        if (firstCard.getImagePath().equals(secondCard.getImagePath())) {
            firstCard.setMatched(true);
            secondCard.setMatched(true);
            firstCard.getButton().setEnabled(false);
            secondCard.getButton().setEnabled(false);
            matchedPairs++;

            if (matchedPairs == totalPairs) {
                endGame(true);
                return;
            }
        } else {
            hideCard(firstCard);
            hideCard(secondCard);
        }

        firstCard = null;
        secondCard = null;
        comparing = false;
        setBoardEnabled(true);
    }

    private void hideCard(GameCard card) {
        card.setOpen(false);
        card.getButton().setIcon(backIcon);
    }

    private void showFront(GameCard card) {
        card.getButton().setIcon(loadFrontIcon(card.getImagePath()));
    }

    private void setBoardEnabled(boolean enabled) {
        for (GameCard card : cards) {
            if (!card.isMatched()) {
                card.getButton().setEnabled(enabled);
            }
        }
    }

    private ImageIcon loadFrontIcon(String imagePath) {
        if (frontIconCache.containsKey(imagePath)) {
            return frontIconCache.get(imagePath);
        }

        try {
            BufferedImage original = ImageIO.read(Paths.get(imagePath).toFile());
            Image scaled = original.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaled);
            frontIconCache.put(imagePath, icon);
            return icon;
        } catch (IOException e) {
            return createErrorIcon(cardWidth, cardHeight);
        }
    }

    private ImageIcon createBackIcon(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = img.createGraphics();

        g2.setColor(new Color(60, 120, 80));
        g2.fillRoundRect(0, 0, w, h, 20, 20);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 42));
        String text = "?";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        int textHeight = g2.getFontMetrics().getAscent();

        g2.drawString(text, (w - textWidth) / 2, (h + textHeight) / 2 - 10);
        g2.dispose();

        return new ImageIcon(img);
    }

    private ImageIcon createErrorIcon(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = img.createGraphics();

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, w, h);

        g2.setColor(Color.RED);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.drawString("IMG ERR", 20, h / 2);
        g2.dispose();

        return new ImageIcon(img);
    }

    private void startCountdown() {
        timeLeft = initialTime;
        lblTime.setText("Time: " + timeLeft + "s");

        countdownTimer = new Timer(1000, e -> {
            if (gameEnded) {
                return;
            }

            timeLeft--;
            lblTime.setText("Time: " + timeLeft + "s");

            if (timeLeft <= 0) {
                endGame(false);
            }
        });

        countdownTimer.start();
    }

    private void endGame(boolean win) {
        if (gameEnded) {
            return;
        }

        gameEnded = true;

        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        setBoardEnabled(false);

        if (win) {
        int displayScore = calculateDisplayScore();

        boolean saved = false;

        if (Session.idUser > 0) {
            saved = scoreDAO.insert(
                    Session.idUser,
                    idLevel,
                    timeLeft,
                    moves
            );
        }

            JOptionPane.showMessageDialog(
                    this,
                    "Selamat! Kamu menang.\n"
                    + "Moves: " + moves + "\n"
                    + "Time left: " + timeLeft + "s\n"
                    + "Score: " + displayScore
                    + (saved ? "\nSkor tersimpan." : "\nSkor tidak tersimpan."),
                    "Menang",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Waktu habis. Kamu kalah!",
                    "Kalah",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private int calculateDisplayScore() {
        int base = totalPairs * 100;
        int timeBonus = timeLeft * 10;
        int movePenalty = moves * 5;
        return Math.max(0, base + timeBonus - movePenalty);
    }

    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null, "Panggil GameEngine dari MenuPemain.");
    }
}
