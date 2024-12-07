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

public class TimerThread extends Thread {
    private JLabel timerLabel;
    private int seconds;
    private boolean running;

    public TimerThread(JLabel timerLabel) {
        this.timerLabel = timerLabel;
        this.seconds = 0;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                seconds++;
                SwingUtilities.invokeLater(() -> timerLabel.setText("Time: " + seconds + "s"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTimer() {
        running = false;
    }
}
