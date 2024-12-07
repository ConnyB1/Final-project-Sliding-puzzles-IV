/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package puzzle.slidingpuzzleiv;

/**
 *
 * @author costco
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class SlidingpuzzleIV extends JFrame {
    private ArrayList<Tile> tiles;
    private JPanel puzzlePanel;
    private int gridSize = 3; // Tamaño inicial (3x3)
    private JLabel timerLabel;
    private TimerThread timerThread;
    private ArrayList<ImageIcon> imagePieces;
    private String selectedImagePath;

    // Colores proporcionados
    private static final Color DARK_GREEN = new Color(0x1F4529);
    private static final Color MEDIUM_GREEN = new Color(0x47663B);
    private static final Color LIGHT_BEIGE = new Color(0xE8ECD7);

    public SlidingpuzzleIV() {
        setTitle("Sliding Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 500);
        setResizable(false);

        // Cambiar el fondo de la ventana principal
        getContentPane().setBackground(LIGHT_BEIGE);

        showMainMenu(); // Mostrar el menú inicial antes de iniciar el juego.

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void showMainMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 10, 10));
        menuPanel.setBackground(LIGHT_BEIGE);

        JLabel titleLabel = new JLabel("Sliding Puzzle", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(DARK_GREEN); // Titulo en verde oscuro
        menuPanel.add(titleLabel);

        JButton startButton = new JButton("Iniciar");
        startButton.setBackground(MEDIUM_GREEN);
        startButton.setForeground(LIGHT_BEIGE);
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.addActionListener(e -> showLevelAndImageMenu());
        menuPanel.add(startButton);

        JButton exitButton = new JButton("Salir");
        exitButton.setBackground(MEDIUM_GREEN);
        exitButton.setForeground(LIGHT_BEIGE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.addActionListener(e -> System.exit(0));
        menuPanel.add(exitButton);

        getContentPane().removeAll();
        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showLevelAndImageMenu() {
        JPanel selectionPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        selectionPanel.setBackground(LIGHT_BEIGE);

        // Selección de nivel
        JLabel levelLabel = new JLabel("Selecciona el tamaño del tablero:", JLabel.CENTER);
        levelLabel.setForeground(DARK_GREEN);
        selectionPanel.add(levelLabel);

        String[] levelOptions = {"3x3 (Fácil)", "4x4 (Medio)", "5x5 (Difícil)"};
        JComboBox<String> levelComboBox = new JComboBox<>(levelOptions);
        selectionPanel.add(levelComboBox);

        // Selección de imagen
        JLabel imageLabel = new JLabel("Selecciona una imagen:", JLabel.CENTER);
        imageLabel.setForeground(DARK_GREEN);
        selectionPanel.add(imageLabel);

        String[] images = {"Imagen 1", "Imagen 2", "Imagen 3"};
        JComboBox<String> imageComboBox = new JComboBox<>(images);
        selectionPanel.add(imageComboBox);

        // Botones
        int result = JOptionPane.showConfirmDialog(
                this,
                selectionPanel,
                "Configuración",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            gridSize = levelComboBox.getSelectedIndex() + 3; // 3x3, 4x4 o 5x5
            selectedImagePath = switch (imageComboBox.getSelectedIndex()) {
                case 0 -> "C:\\Users\\pecho\\OneDrive\\Escritorio\\PracticasJava\\slidingpuzzleIV\\Imagenes\\Torre-Eiffel.png";
                case 1 -> "C:\\Users\\pecho\\OneDrive\\Escritorio\\PracticasJava\\slidingpuzzleIV\\Imagenes\\MURALLA.jpg";
                case 2 -> "C:\\Users\\pecho\\OneDrive\\Escritorio\\PracticasJava\\slidingpuzzleIV\\Imagenes\\Coliseo-Romano.jpg";
                default -> null;
            };

            if (selectedImagePath != null) {
                initGame();
            }
        }
    }

    private void initGame() {
        getContentPane().removeAll();

        timerLabel = new JLabel("Tiempo: 0s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(DARK_GREEN); // Color de texto en verde oscuro
        add(timerLabel, BorderLayout.NORTH);

        puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(gridSize, gridSize));
        puzzlePanel.setBackground(LIGHT_BEIGE); // Fondo de panel en beige
        add(puzzlePanel, BorderLayout.CENTER);

        loadAndSliceImage(selectedImagePath);
        tiles = new ArrayList<>();

        for (int i = 0; i < gridSize * gridSize; i++) {
            Tile tile = new Tile(i < gridSize * gridSize - 1 ? i + 1 : 0);

            if (i < imagePieces.size() && imagePieces.get(i) != null) {
                tile.setImage(imagePieces.get(i));
            }

            tile.getButton().addActionListener(this::moveTile);
            tiles.add(tile);
        }

        Collections.shuffle(tiles);

        for (Tile tile : tiles) {
            puzzlePanel.add(tile.getButton());
        }

        puzzlePanel.revalidate();

        if (timerThread != null) timerThread.stopTimer();
        timerThread = new TimerThread(timerLabel);
        timerThread.start();

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame()); // Resetear el juego al presionar
        add(resetButton, BorderLayout.SOUTH); // Añadir el botón de reset al final

        revalidate();
        repaint();
    }
    
    
    private void resetGame() {
        // Detener el temporizador
        if (timerThread != null) timerThread.stopTimer();

        // Barajar las piezas de nuevo
        Collections.shuffle(tiles);

        // Limpiar el panel del puzzle antes de añadir las piezas de nuevo
        puzzlePanel.removeAll();

        // Añadir las piezas barajadas al panel
        for (Tile tile : tiles) {
            tile.getButton().setEnabled(true); // Asegurarse de que los botones estén habilitados
            puzzlePanel.add(tile.getButton());
        }

        // Forzar que el panel se repinte con las nuevas piezas
        puzzlePanel.revalidate();
    puzzlePanel.repaint();

        // Reiniciar el temporizador
        if (timerThread != null) timerThread.stopTimer();
        timerThread = new TimerThread(timerLabel);
        timerThread.start();
    }

    private void loadAndSliceImage(String imagePath) {
        try {
            BufferedImage fullImage = ImageIO.read(new File(imagePath));
            int pieceWidth = fullImage.getWidth() / gridSize;
            int pieceHeight = fullImage.getHeight() / gridSize;

            imagePieces = new ArrayList<>();
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    BufferedImage piece = fullImage.getSubimage(
                            col * pieceWidth,
                            row * pieceHeight,
                            pieceWidth,
                            pieceHeight
                    );

                    Image scaledPiece = piece.getScaledInstance(
                            500 / gridSize,
                            500 / gridSize,
                            Image.SCALE_SMOOTH
                    );

                    ImageIcon icon = new ImageIcon(scaledPiece);
                    imagePieces.add(icon);
                }
            }

            imagePieces.set(imagePieces.size() - 1, null); // Última pieza vacía
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveTile(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        int clickedIndex = -1;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getButton() == clickedButton) {
                clickedIndex = i;
                break;
            }
        }

        int emptyIndex = -1;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getNumber() == 0) {
                emptyIndex = i;
                break;
            }
        }

        if (isAdjacent(clickedIndex, emptyIndex)) {
            Tile clickedTile = tiles.get(clickedIndex);
            Tile emptyTile = tiles.get(emptyIndex);

            emptyTile.setNumber(clickedTile.getNumber());
            emptyTile.setImage(clickedTile.getImage());
            clickedTile.setNumber(0);
            clickedTile.setImage(null);

            puzzlePanel.revalidate();
            puzzlePanel.repaint();

            if (isSolved()) {
                timerThread.stopTimer();
                JOptionPane.showMessageDialog(this, "¡Felicidades! ¡Has resuelto el rompecabezas!");
                showMainMenu();
            }
        }
    }

    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / gridSize;
        int col1 = index1 % gridSize;
        int row2 = index2 / gridSize;
        int col2 = index2 % gridSize;

        return (row1 == row2 && Math.abs(col1 - col2) == 1) || (col1 == col2 && Math.abs(row1 - row2) == 1);
    }

    private boolean isSolved() {
        for (int i = 0; i < tiles.size() - 1; i++) {
            if (tiles.get(i).getNumber() != i + 1) {
                return false;
            }
        }
        return tiles.get(tiles.size() - 1).getNumber() == 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SlidingpuzzleIV::new);
    }
}