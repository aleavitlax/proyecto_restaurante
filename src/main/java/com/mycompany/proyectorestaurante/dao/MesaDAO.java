/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectorestaurante.dao;
import com.mycompany.proyectorestaurante.bd.ConexionBD;
import com.mycompany.proyectorestaurante.modelo.Mesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
   
   public List<Mesa> obtenerMesasDisponibles(){
       List<Mesa>lista = new ArrayList<>();
       try{
           
           PreparedStatement ps = conn.prepareStatement("SELECT * FROM mesas WHERE "
                   + "estado = 'disponible'");
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()){
               Mesa m = new Mesa();
               m.setId(rs.getInt("id"));
               m.setCapacidad(rs.getInt("capacidad"));
               m.setEstado(rs.getString("estado"));
               
               lista.add(m);
           }
           
       }catch (Exception e){
           System.out.println("ERROR:"+ e.getMessage());
           return null;
       }
       return lista;
   }
}

