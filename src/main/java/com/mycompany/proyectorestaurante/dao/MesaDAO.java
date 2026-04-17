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

    public boolean liberarMesa(int numeroMesa) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT estado FROM mesas WHERE capacidad = ?"
            );
            ps.setInt(1, numeroMesa);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String estado = rs.getString("estado");

                if (estado.equals("ocupada")) {
                    PreparedStatement update = conn.prepareStatement(
                        "UPDATE mesas SET estado = 'disponible' WHERE capacidad = ?"
                    );
                    update.setInt(1, numeroMesa);
                    update.executeUpdate();

                    return true; 
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }
}

