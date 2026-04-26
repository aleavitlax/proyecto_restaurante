/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectorestaurante.controlador;

import com.mycompany.proyectorestaurante.bd.ConexionBD;
import com.mycompany.proyectorestaurante.dao.AsignacionDAO;
import com.mycompany.proyectorestaurante.dao.MesaDAO;
import com.mycompany.proyectorestaurante.modelo.Mesa;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 *
 * @author Alejandra
 */
public class PantallaAsignar_mesaController implements Initializable{
    @FXML private TableView<Mesa> tablaMesas;
    @FXML private TableColumn<Mesa, Integer> ColID;
    @FXML private TableColumn<Mesa, Integer> ColCapacidad;
    @FXML private TableColumn<Mesa, String> ColEstado;
    
    @FXML private TextField txtCliente;
    @FXML private TextField txtPersonas;
    @FXML private Label lblMensaje;
    
    private Mesa mesaSeleccionada;
    MesaDAO mesaDao = new MesaDAO();
    AsignacionDAO asignacionDao = new AsignacionDAO();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.err.println(">>>> ¡EL INITIALIZE SI ESTA FUNCIONANDO! <<<<");
        ColID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        ColEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cargarMesa();
        
           tablaMesas.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    mesaSeleccionada = newSelection;
                }
            }
        );
    }
    
    public void cargarMesa(){
         List<Mesa> listaMesas = mesaDao.obtenerMesasDisponibles();
    
    // Esto saldrá en rojo en la consola de NetBeans
    System.err.println("DEBUG: Cantidad de mesas recibidas del DAO: " + (listaMesas != null ? listaMesas.size() : "NULL"));

    if (listaMesas != null) {
        ObservableList<Mesa> lista = FXCollections.observableArrayList(listaMesas);
        tablaMesas.setItems(lista);
    }
        
    
    }
    
    @FXML
    private void asignarMesa(){
        String cliente = txtCliente.getText();
        String personasTexto= txtPersonas.getText();
        
        if(cliente.isEmpty()){
            lblMensaje.setText("ingresa el nombre del cliente");
            return;
        }
        
        
        if(mesaSeleccionada== null){
            lblMensaje.setText("selecciona una mesa");
            return;
        }
        
        int personas;
        
        try{
            personas = Integer.parseInt(personasTexto);
        }catch(Exception e){
            lblMensaje.setText("solo numeros");
            return;
        }
        
        if(personas > mesaSeleccionada.getCapacidad()){
            lblMensaje.setText("capacidad insuficiente");
            return;
        }
        
        boolean resultado = asignacionDao.asignarMesa(mesaSeleccionada.getId(), cliente);
        
        if(resultado){
        lblMensaje.setText("mesa asignada correctamente");
        txtCliente.clear();
        txtPersonas.clear();
        mesaSeleccionada = null;
        cargarMesa();
    }else{
        lblMensaje.setText("error al asignar la mesa");
    }
        
    
   
    
   
    
    
   
        
        
    }
    @FXML
    private void cancelar() {
    txtCliente.clear();
    txtPersonas.clear();
    lblMensaje.setText("");
}
    
    
}
