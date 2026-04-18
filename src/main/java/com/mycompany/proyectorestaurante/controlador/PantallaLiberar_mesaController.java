/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectorestaurante.controlador;

import com.mycompany.proyectorestaurante.dao.MesaDAO;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.TextField;
//import com.mycompany.proyectorestaurante.dao;
/**
 * FXML Controller class
 *
 * @author Alejandra
 */
public class PantallaLiberar_mesaController implements Initializable {
    
    MesaDAO mesaDAO = new MesaDAO();
    @FXML 
    private TextField txtMesa;
    @FXML
    private Label lblMensaje;
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }  
    @FXML
    private void liberarMesa(){
       try{ 
        int numeroMesa = Integer.parseInt(txtMesa.getText());

        String resultado = mesaDAO.liberarMesa(numeroMesa);

        switch (resultado) {
            case "OK":
                lblMensaje.setText("Mesa liberada");
                break;
            case "YA_LIBRE":
                lblMensaje.setText("la mesa ya estaba disponible");
                break;
            case "NO_PAGADA":
                lblMensaje.setText("No se puede liberar: cuenta no pagada");
                break;
            case "NO_EXISTE":
                lblMensaje.setText("La mesa no existe");
                break;
            default:
                lblMensaje.setText("Error al liberar la mesa");
                break;
        }
    }catch(NumberFormatException e){
        lblMensaje.setText("solo se permiten numeros");
        txtMesa.clear();
}
    }
    @FXML
    private void cancelar(){
        txtMesa.clear();
        lblMensaje.setText("Operación cancelada");
    }
    
}
