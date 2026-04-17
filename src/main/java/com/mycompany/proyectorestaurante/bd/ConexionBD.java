/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectorestaurante.bd;
import java.sql.*;
/**
 *
 * @author Alejandra
 */
public class ConexionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/bdRestaurante";
    private static final String USER = "restaurante";
    private static final String PASSWORD = "12345";
    
    public static Connection conectar(){
        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }catch(Exception e){
            System.out.println("Error de conexión" + e.getMessage());
            return null;
        }

}
    
}
