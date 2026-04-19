/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectorestaurante.dao;
import com.mycompany.proyectorestaurante.bd.ConexionBD;
import java.sql.*;
/**
 *
 * @author Alejandra
 */
public class MesaDAO {
   



    Connection conn = ConexionBD.conectar();

   public String liberarMesa(int numeroMesa) {
    try {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT estado, pagada FROM mesas WHERE capacidad = ?"
        );
        ps.setInt(1, numeroMesa);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String estado = rs.getString("estado");
            boolean pagada = rs.getBoolean("pagada");

            if (estado.equals("disponible")) {
                return "YA_LIBRE";
            }

            if (!pagada){
                return "NO_PAGADA";
            }

          
            PreparedStatement update = conn.prepareStatement(
                "UPDATE mesas SET estado = 'disponible' WHERE capacidad = ?"
            );
            update.setInt(1, numeroMesa);
            update.executeUpdate();

            return "OK";
        } else {
            return "NO_EXISTE";
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        return "ERROR";
    }
}
}

