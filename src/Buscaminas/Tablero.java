package Buscaminas;

import java.util.Random;

public class Tablero {
    private ClassCasilla[][] casillas; // Matriz que representa el tablero
    private final int filas = 10;
    private final int columnas = 10;
    private final int numMinas = 10;

    public Tablero() {
        casillas = new ClassCasilla[filas][columnas]; // Inicializa la matriz de casillas
        inicializarTablero();                   // Llama a inicializar para crear las casillas
        colocarMinas();                         // Coloca las minas en el tablero
        calcularMinasAdyacentes();              // Calcula las minas adyacentes
    }

    // Método para inicializar el tablero con objetos Casilla
    private void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new ClassCasilla();
            }
        }
    }

    // Coloca minas aleatoriamente en el tablero
    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;
        while (minasColocadas < numMinas) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);
            if (!casillas[fila][columna].isMina()) {
                casillas[fila][columna].setMina(true);
                minasColocadas++;
            }
        }
    }

    // Calcula cuántas minas hay alrededor de cada casilla
    private void calcularMinasAdyacentes() {
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j].isMina()) continue;

                int minas = 0;
                for (int k = 0; k < dx.length; k++) {
                    int ni = i + dx[k];
                    int nj = j + dy[k];

                    if (ni >= 0 && ni < filas && nj >= 0 && nj < columnas && casillas[ni][nj].isMina()) {
                        minas++;
                    }
                }
                casillas[i][j].setMinasAdyacentes(minas);
            }
        }
    }

    public ClassCasilla[][] getCasillas() {
        return casillas; // Devuelve la matriz de casillas
    }
}
