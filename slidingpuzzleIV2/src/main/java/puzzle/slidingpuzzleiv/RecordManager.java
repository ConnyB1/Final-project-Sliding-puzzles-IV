/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle.slidingpuzzleiv;

/**
 *
 * @author costco
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordManager {
    private static final String FILE_PATH = "records.txt";

    public static void saveRecord(String name, int score) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(name + ", " + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadRecords() {
        List<String> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
