package com.mycompany.proyectorestaurante.controlador;

import com.mycompany.proyectorestaurante.App;
import com.mycompany.proyectorestaurante.bd.ConexionBD;
import com.mycompany.proyectorestaurante.modelo.ProductoPedido;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class RegistrarPedidoController {

    @FXML private TableView<ProductoPedido> TablaDePedido;
    @FXML private TableColumn<ProductoPedido, String> colNombre;
    @FXML private TableColumn<ProductoPedido, Integer> colCantidad;
    @FXML private Label lblTotal;

    private int idPedidoActual = 1; // Este ID se debe recibir dinámicamente según la mesa

    @FXML
    public void initialize() {
        // Vinculación de las columnas del FXML con las propiedades del Modelo
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cargarResumenPedido();
        actualizarTotalDinamico();
    }

    // Carga los productos actuales del pedido en la tabla (CU-03)
    private void cargarResumenPedido() {
        ObservableList<ProductoPedido> lista = FXCollections.observableArrayList();
        String query = "SELECT m.nombre, dp.cantidad, dp.subtotal " +
                       "FROM detalle_pedido dp " +
                       "JOIN menu m ON dp.id_platillo = m.id_platillo " +
                       "WHERE dp.id_pedido = ?";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPedidoActual);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(new ProductoPedido(
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal")
                ));
            }
            TablaDePedido.setItems(lista);
        } catch (SQLException e) {
            System.out.println("Error al cargar pedido: " + e.getMessage());
        }
    }

    // Actualiza el Label del total sumando los subtotales de la BD
    private void actualizarTotalDinamico() {
        String query = "SELECT SUM(subtotal) FROM detalle_pedido WHERE id_pedido = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPedidoActual);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double total = rs.getDouble(1);
                lblTotal.setText(String.format("Total: $%.2f", total));
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular total: " + e.getMessage());
        }
    }

    @FXML
private void AgregarAPedido() {
    try {
        // Al presionar el botón del prototipo, abrimos el menú digital (CU-14)
        // Esto permite que el mesero seleccione platillos de la tabla 'menu'
        App.setRoot("MenuDigital"); 
    } catch (IOException e) {
        System.out.println("Error al abrir el menú: " + e.getMessage());
    }
}

@FXML
private void EnviarACocina() {
    // 1. Cambiamos el estado de la mesa a 'ocupada' en la BD (bd_restaurante)
    String queryMesa = "UPDATE mesas SET estado = 'ocupada' WHERE id = 12";
    
    // 2. El Chef ahora podrá ver el pedido en su pantalla (CU-06)
    System.out.println("Pedido enviado a cocina. La mesa 12 ahora está OCUPADA.");
    // Aquí ejecutarías tu conexión SQL con ConexionBD.conectar()
}
    
    @FXML
    private void Volver() {
        try {
        // Lógica para regresar a la pantalla anterior (ej. Dashboard)
                App.setRoot("Dashboard"); 
            } catch (IOException e) {
            System.out.println("Error al cambiar de pantalla: " + e.getMessage());
        }
    }
}