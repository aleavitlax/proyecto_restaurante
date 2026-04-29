package com.mycompany.proyectorestaurante.controlador;

import com.mycompany.proyectorestaurante.App;
import java.io.IOException;
import java.util.Stack;

public class GestorNavegacion {
    
    // La pila estática que guardará nuestro historial
    private static Stack<String> historial = new Stack<>();
    private static String pantallaActual = "Dashboard"; // Tu pantalla inicial por defecto

    /**
     * Usa este método cuando quieras cambiar de pantalla.
     * Automáticamente guarda la pantalla actual antes de ir a la nueva.
     */
    public static void irA(String fxml) throws IOException {
        historial.push(pantallaActual); // Guardamos donde estamos
        pantallaActual = fxml;          // Actualizamos a dónde vamos
        App.setRoot(fxml);              // Le decimos a App que cambie la vista
    }

    /**
     * Usa este método para el botón "Volver".
     * Saca la última pantalla de la pila y regresa a ella.
     */
    public static void volver() throws IOException {
        if (!historial.isEmpty()) {
            String anterior = historial.pop(); // Sacamos la última pantalla visitada
            pantallaActual = anterior;
            App.setRoot(anterior);             // Regresamos a ella
        } else {
            System.out.println("Ya estás en la pantalla principal, no se puede volver más.");
        }
    }
}
