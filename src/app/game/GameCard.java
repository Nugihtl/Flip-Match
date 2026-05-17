package app.game;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.JButton;

/**
 *
 * @author Siti Amalia Putri
 */
public class GameCard {
    private final String imagePath;
    private final JButton button;
    private boolean matched;
    private boolean open;

    public GameCard(String imagePath, JButton button) {
        this.imagePath = imagePath;
        this.button = button;
        this.matched = false;
        this.open = false;
    }

    public String getImagePath() {
        return imagePath;
    }

    public JButton getButton() {
        return button;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
