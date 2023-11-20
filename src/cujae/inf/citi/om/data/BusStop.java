/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.data;

import cujae.inf.citi.om.exceptions.WithoutCapacityException;
import java.util.ArrayList;

/**
 *
 * @author nanda
 */

//Clase que modela los datos de una parada en un SBRP.

public class BusStop {
    private String idBusStop;
    private Location locationBusStop;
    private int capacityBusStop;
    private ArrayList<String> listPassengers;
    
    public BusStop(){
        super();
    }
    
    public BusStop(String idBusStop, int capacity, Location locationBus) {
        setIdBusStop(idBusStop);
        setLocationBusStop(locationBus);
        setCapacityBusStop(capacity);

        this.listPassengers = new ArrayList<String>();
    }
    
    public String getIdBusStop(){
        return idBusStop;
    }
    
    public void setIdBusStop(String idBusStop) throws IllegalArgumentException{
        if(!idBusStop.equals(null)){
            this.idBusStop = idBusStop;
        }
        else
            throw new IllegalArgumentException();     
    }
    
    public Location getLocationBusStop(){
        return locationBusStop;
    }
    
    public void setLocationBusStop(Location locationBusStop) throws IllegalArgumentException{
        if(!locationBusStop.equals(null)){
            this.locationBusStop = locationBusStop;
        }
        else
            throw new IllegalArgumentException();            
    }    
    
    public int getCapacityBusStop(){
        return capacityBusStop;
    } 
    
    public void setCapacityBusStop(int capacityBusStop){
        if(capacityBusStop > 0){
            this.capacityBusStop = capacityBusStop;
        }
    }
    
     public ArrayList<String> getListPassengers(){
         return listPassengers;
     }
     
     //Método para determinar si hay capacidad en la parada.
     public boolean hasCapacity(){
         return capacityBusStop > listPassengers.size();
     }
     
     //Método para saber si se puede insertar un nuevo pasajero.
     public boolean insertPassenger(String idPassenger){
         if(hasCapacity()){
             listPassengers.add(idPassenger);
         }
         
         return hasCapacity();
     }
     
     //Método para aumentar la capacidad de la parada.
     public void increaseCapacityBusStop(int increase){
         if(increase > 0){
             this.capacityBusStop += increase;
         }
     }
     
     //Método para decrementar la capacidad de la parada.
     public void decreaseCapacityBusStop(int decrease){
         if(decrease > 0 && (capacityBusStop - decrease > 0) ){
            this.capacityBusStop -= decrease;
         }
            
     }
     
     public String get_coordinates(){
    
        return this.locationBusStop.getAxisX() + " " + this.locationBusStop.getAxisY();
        
    }
     
}
