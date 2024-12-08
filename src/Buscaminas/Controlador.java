package Buscaminas;

import java.util.Scanner;

public class Controlador {
    private Tablero tablero;
    private ClassVista vista;

    public Controlador() {
        tablero = new Tablero();
        vista = new ClassVista();
    }

    public void jugar() {
        try (Scanner scanner = new Scanner(System.in)) {
			boolean juegoTerminado = false;

			while (!juegoTerminado) {
			    vista.mostrarTablero(tablero.getCasillas());
			    System.out.println("Introduce coordenadas (por ejemplo, A5):");

			    try {
			        String input = scanner.nextLine().toUpperCase().trim(); // Lee la entrada y la convierte a mayúsculas
			        if (input.length() < 2 || input.length() > 3) {
			            throw new IllegalArgumentException("Entrada inválida. Usa formato letra-número (ejemplo: A5).");
			        }

			        char filaChar = input.charAt(0); // Primera letra de la entrada
			        int fila = filaChar - 'A'; // Convierte la letra a un índice (0 = A, 1 = B, etc.)
			        if (fila < 0 || fila >= 10) {
			            throw new IllegalArgumentException("Fila fuera de rango. Debe ser una letra entre A y J.");
			        }

			        // Extraemos el número que está en la segunda posición (el número de la columna)
			        int columna = Integer.parseInt(input.substring(1)) - 1; // Convierte el número a índice (1 = 0, 2 = 1, etc.)
			        if (columna < 0 || columna >= 10) {
			            throw new IllegalArgumentException("Columna fuera de rango. Debe ser un número entre 1 y 10.");
			        }

			        // Si la entrada es válida, procesamos la casilla seleccionada
			        ClassCasilla casilla = tablero.getCasillas()[fila][columna];
			        if (casilla.isDescubierta()) {
			            vista.mostrarMensaje("¡Ya has descubierto esta casilla! Intenta otra.");
			        } else if (casilla.isMina()) {
			            vista.mostrarMensaje("¡Has perdido! Has descubierto una mina.");
			            juegoTerminado = true;
			        } else {
			            casilla.setDescubierta(true);
			            if (verificarVictoria()) {
			                vista.mostrarMensaje("¡Felicidades! Has descubierto todas las casillas seguras.");
			                juegoTerminado = true;
			            }
			        }
			    } catch (NumberFormatException e) {
			        vista.mostrarMensaje("Error: Entrada no válida. El número de columna debe ser un número.");
			    } catch (IllegalArgumentException e) {
			        vista.mostrarMensaje(e.getMessage());
			    } catch (Exception e) {
			        vista.mostrarMensaje("Ocurrió un error inesperado: " + e.getMessage());
			    }
			}
		}
        vista.mostrarTablero(tablero.getCasillas());
    }

    private boolean verificarVictoria() {
        for (ClassCasilla[] fila : tablero.getCasillas()) {
            for (ClassCasilla casilla : fila) {
                if (!casilla.isMina() && !casilla.isDescubierta()) {
                    return false;
                }
            }
        }
        return true;
    }
}

