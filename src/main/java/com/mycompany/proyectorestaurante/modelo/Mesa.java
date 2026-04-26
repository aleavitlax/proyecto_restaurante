/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectorestaurante.modelo;

/**
 *
 * @author Alejandra
 */
public class Mesa {
        private int id;
        private int capacidad;
        private String estado;
        
        public Mesa(){
            
        }
        
        public int getId(){
            return id;
        }
        public int getCapacidad(){
            return capacidad;
        }
        public String getEstado(){
            return estado;
        }
        
        public void setId(int newId){
            this.id = newId;
        }
        
        public void setCapacidad(int newCapacidad){
            this.capacidad= newCapacidad;
        }
        public void setEstado(String newEstado){
            this.estado= newEstado;
        }
}
