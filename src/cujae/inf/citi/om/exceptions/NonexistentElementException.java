/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.exceptions;

/**
 *
 * @author nanda
 */

//Clase que maneja la excepción lanzada cuando un elemento (pasajero, parada, ruta, vehículo) no existe.

public class NonexistentElementException extends Exception {
    public NonexistentElementException(String message){  
        super(message);  
    }
    
}
