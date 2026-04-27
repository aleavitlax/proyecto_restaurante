package com.mycompany.proyectorestaurante.modelo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author herpa
 */
public class ProductoPedido {
    private final SimpleStringProperty nombre;
    private final SimpleIntegerProperty cantidad;
    private final SimpleDoubleProperty subtotal;

    public ProductoPedido(String nombre, int cantidad, double subtotal) {
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.subtotal = new SimpleDoubleProperty(subtotal);
    }

    public String getNombre() { return nombre.get(); }
    public int getCantidad() { return cantidad.get(); }
    public double getSubtotal() { return subtotal.get(); }
}