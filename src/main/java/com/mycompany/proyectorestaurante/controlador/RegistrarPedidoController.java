package com.mycompany.proyectorestaurante.controlador;

import com.mycompany.proyectorestaurante.bd.ConexionBD;
import com.mycompany.proyectorestaurante.modelo.ProductoPedido;
import com.mycompany.proyectorestaurante.controlador.GestorNavegacion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistrarPedidoController {

    // --- ELEMENTOS DE LA INTERFAZ (VINCULADOS A SCENE BUILDER) ---
    @FXML
    private TextField txtNombreProducto;
    
    @FXML
    private Label lblTotal;

    @FXML
    private TableView<ProductoPedido> TablaDePedido;

    @FXML
    private TableColumn<ProductoPedido, String> colNombre;

    @FXML
    private TableColumn<ProductoPedido, Integer> colCantidad;
    
    // (Opcional) Si tienes una columna de precio, agrégala aquí
    @FXML
    private TableColumn<ProductoPedido, Double> colPrecio;

    // Lista observable para que la tabla se actualice automáticamente
    private ObservableList<ProductoPedido> listaProductos;
    private double totalPedido = 0.0;

    // --- MÉTODOS DE INICIALIZACIÓN ---
    @FXML
    public void initialize() {
        // Inicializamos la lista vacía
        listaProductos = FXCollections.observableArrayList();
        
        // Configuramos qué dato va en cada columna (debe coincidir con la clase ProductoPedido)
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        // Si usas columna de precio:
        if (colPrecio != null) {
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        }

        // Vinculamos la lista a la tabla
        TablaDePedido.setItems(listaProductos);
        actualizarEtiquetaTotal();
    }

    // --- FUNCIONES DE LOS BOTONES ---

    /**
     * Busca el producto en la BD por su nombre y lo agrega a la tabla si existe.
     */
    @FXML
    private void AgregarProductoBuscado() {
        String nombreBuscado = txtNombreProducto.getText().trim();

        if (nombreBuscado.isEmpty()) {
            mostrarAlerta("Campo Vacío", "Por favor, escribe el nombre del producto.");
            return; 
        }

        // Consulta SQL para buscar por nombre exacto en la tabla menú
        String query = "SELECT id_platillo, nombre, precio FROM menu WHERE nombre = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, nombreBuscado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // El producto existe
                String nombreBD = rs.getString("nombre");
                double precioBD = rs.getDouble("precio");

                // Creamos el objeto (Asumiendo que el constructor es: nombre, cantidad, precio)
                ProductoPedido nuevoPlatillo = new ProductoPedido(nombreBD, 1, precioBD);
                
                // Lo agregamos a la tabla de memoria
                listaProductos.add(nuevoPlatillo);
                
                // Sumamos al total y limpiamos el campo
                totalPedido += precioBD;
                actualizarEtiquetaTotal();
                txtNombreProducto.clear();
                
            } else {
                mostrarAlerta("Producto No Encontrado", 
                    "El platillo '" + nombreBuscado + "' no existe en el menú.");
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al buscar producto: " + e.getMessage());
            mostrarAlerta("Error de BD", "Hubo un problema al conectar con la base de datos.");
        }
    }

    /**
     * Envía el pedido a la base de datos y actualiza el estado de la mesa. (CU-03)
     */
    @FXML
    private void EnviarACocina() {
        if (listaProductos.isEmpty()) {
            mostrarAlerta("Pedido Vacío", "No puedes enviar un pedido sin productos.");
            return;
        }

        // Aquí irá tu lógica para hacer INSERT en 'pedidos' e 'detalle_pedido'
        // ...
        
        // Actualizamos el estado de la mesa a 'ocupada'
        String queryMesa = "UPDATE mesas SET estado = 'ocupada' WHERE id = 12"; // Ejemplo con mesa 12
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(queryMesa)) {
            ps.executeUpdate();
            
            mostrarAlertaInfo("Éxito", "El pedido se ha enviado a cocina correctamente.");
            
            // Regresamos al Dashboard tras enviar el pedido exitosamente
            GestorNavegacion.volver();
            
        } catch (SQLException | IOException e) {
            System.out.println("Error al enviar pedido: " + e.getMessage());
        }
    }

    /**
     * Utiliza el GestorNavegacion para regresar a la pestaña anterior.
     */
    @FXML
    private void Volver() {
        try {
            GestorNavegacion.volver();
        } catch (IOException e) {
            System.out.println("Error al regresar: " + e.getMessage());
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private void actualizarEtiquetaTotal() {
        lblTotal.setText(String.format("Total: $%.2f", totalPedido));
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}