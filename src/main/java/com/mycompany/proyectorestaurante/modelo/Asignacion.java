/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectorestaurante.modelo;

/**
 *
 * @author Alejandra
 */
public class Asignacion {
    private int id;
    private int idMesa;
    private String nombreCliente;
    private String estado;
    
    public Asignacion(){
        
    }
    
    public int getId(){
        return id;
    }
    public int getIdMesa(){
        return idMesa;
    }
    
   public String getNombreCliente(){
       return nombreCliente;
   }
   public String getEstado(){
       return estado;
   }
}
