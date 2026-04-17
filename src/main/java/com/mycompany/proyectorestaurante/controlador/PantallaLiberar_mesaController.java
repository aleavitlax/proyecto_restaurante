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
        int numeroMesa = Integer.parseInt(txtMesa.getText());
        boolean resultado = mesaDAO.liberarMesa(numeroMesa);
        if(resultado){
            lblMensaje.setText("La mesa se liberó correctamente");
        }else{
            lblMensaje.setText("No se pudo liberar la mesa");
        }
    }
    @FXML
    private void cancelar(){
        txtMesa.clear();
        lblMensaje.setText("Operación cancelada");
    }
    
}
