/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle.slidingpuzzleiv;

/**
 *
 * @author costco
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class Tile {
    private int number;
    private JButton button;
    private ImageIcon image;

    public Tile(int number) {
        this.number = number;
        button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
         button.setForeground(Color.WHITE);
        setNumber(number); // Establece el texto inicial del botón
    }
    public JButton getButton() {
        return button;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        button.setText(number == 0 ? "" : String.valueOf(number));
        button.setIcon(image); // Mantén la imagen actual
    }

    public void setImage(ImageIcon image) {
        this.image = image;
        if (image != null) {
            button.setIcon(image);
        } else {
            button.setIcon(null);
        }
        button.setText(number == 0 ? "" : String.valueOf(number)); // Actualiza el texto
    }

    public ImageIcon getImage() {
        return image;
    }
}