/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle.slidingpuzzleiv;

/**
 *
 * @author costco
 */
import java.util.Random;

public class Puzzleolvable {
    public static int[] generateSolvablePermutation(int gridSize) {
        int[] permutation = new int[gridSize * gridSize];
        for (int i = 0; i < permutation.length; i++) {
            permutation[i] = i;
        }

        // Barajar los números
        shuffleArray(permutation);

        // Asegurar que la permutación sea solucionable
        while (!isSolvable(permutation, gridSize)) {
            shuffleArray(permutation);
        }

        return permutation;
    }

    private static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static boolean isSolvable(int[] permutation, int gridSize) {
        int inversions = calculateInversions(permutation);
        
        if (gridSize % 2 != 0) {
            // Para tableros impares, deben tener un número par de inversiones.
            return inversions % 2 == 0;
        } else {
            // Para tableros pares, cuenta la posición de la celda vacía desde abajo.
            int emptyRow = findEmptyRowFromBottom(permutation, gridSize);
            return (inversions % 2 == 0 && emptyRow % 2 != 0) || (inversions % 2 != 0 && emptyRow % 2 == 0);
        }
    }

    private static int calculateInversions(int[] permutation) {
        int inversions = 0;
        for (int i = 0; i < permutation.length - 1; i++) {
            for (int j = i + 1; j < permutation.length; j++) {
                if (permutation[i] > permutation[j] && permutation[i] != 0 && permutation[j] != 0) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    private static int findEmptyRowFromBottom(int[] permutation, int gridSize) {
        int emptyIndex = -1;
        for (int i = 0; i < permutation.length; i++) {
            if (permutation[i] == 0) {
                emptyIndex = i;
                break;
            }
        }
        // Calcular la fila de la celda vacía desde abajo
        return gridSize - (emptyIndex / gridSize);
    }
}
