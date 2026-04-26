/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectorestaurante.dao;
import com.mycompany.proyectorestaurante.bd.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Alejandra
 */
public class AsignacionDAO {
    Connection conn = ConexionBD.conectar();
    
    public Boolean asignarMesa(int idMesa, String cliente){
        try{
            
            PreparedStatement ps = conn.prepareStatement("INSERT INTO asignaciones (id_mesa, nombre_cliente, estado) "
                    + "VALUES (?, ?, 'disponible')");
            ps.setInt(1, idMesa);
            ps.setString(2, cliente);
            ps.executeUpdate();
            
            PreparedStatement update = conn.prepareStatement("UPDATE mesas SET estado = 'ocupada',"
                    + " pagada = false WHERE id = ?");
            update.setInt(1, idMesa);
            update.executeUpdate();
            
            return true;
        }catch (Exception e){
            System.out.println("Error"+ e.getMessage());
            return false;
        }
        
    }
    
}
