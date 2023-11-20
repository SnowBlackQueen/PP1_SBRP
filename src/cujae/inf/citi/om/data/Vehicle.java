/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.data;

import cujae.inf.citi.om.exceptions.WithoutCapacityException;
/**
 *
 * @author nanda
 */

//Clase que modela los datos de un autobus en SBRP.

public class Vehicle {
    private int capacityBus;
    private String busPlate;
    private Location locationBus;
    
    public Vehicle(){
        super();
    }
    
    public Vehicle(int capacityBus, String busPlate, Location locationBus) throws WithoutCapacityException{
        setCapacityBus(capacityBus);
        setBusPlate(busPlate);
        this.locationBus = locationBus;
    }
    
    public int getCapacityBus(){
        return capacityBus;
    }
    
    public void setCapacityBus(int capacityBus) throws WithoutCapacityException{
        if(capacityBus > 0){
            this.capacityBus = capacityBus;
        }
        else
            throw new WithoutCapacityException("La capacidad del vehículo no puede ser negativa.");
    }
    
    public String getBusPlate(){
        return busPlate;
    }
    
    public void setBusPlate(String busPlate){
        this.busPlate = busPlate;
    }
    
   
    
    //Método para incrementar la capacidad de los vehículos.
    public void increaseCapacityVehicles(int increase) {
        if(increase > 0){
            this.capacityBus += increase;
        }
        
    }
    
    //Método para decrementar la capacidad de los vehículos.
    public void decreaseCapacityVehicles(int decrease){
        if(decrease > 0 && (capacityBus - decrease > 0) ){
            this.capacityBus -= decrease;
        }
        
    }

}
