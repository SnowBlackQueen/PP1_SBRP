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

//Clase que modela los datos de un depósito en un SBRP.

public class Depot {
    protected String idDepot;
    protected Location locationDepot;
    protected ArrayList<Vehicle> listBuses;
    protected int maximumFleetBuses;
    private int bus_capacity;
    
    public Depot(){
        super();
    }
    
    public Depot(String idDepot, Location locationDepot, int maximumFleetBuses,
                 int countVehicles, int capacityVehicles) throws WithoutCapacityException{
        setIdDepot(idDepot);
        setLocationDepot(locationDepot);
        setMaximumFleetBuses(maximumFleetBuses);
        listBuses = new ArrayList<Vehicle>();
        bus_capacity = capacityVehicles;
        
        generateVehicles(countVehicles, capacityVehicles);
        
    }
    
    public String getIdDepot(){
        return idDepot;
    }
    
    public void setIdDepot(String idDepot) throws IllegalArgumentException{
        if(!idDepot.equals(null)){
            this.idDepot = idDepot;
        }
        else
            throw new IllegalArgumentException();
    }
    
    public Location getLocationDepot(){
        return locationDepot;
    }
    
    public void setLocationDepot(Location locationDepot) throws IllegalArgumentException{
        if(!locationDepot.equals(null)){
            this.locationDepot = locationDepot;
        }
        else 
            throw new IllegalArgumentException();
    }
    
    public ArrayList<Vehicle> getListBuses(){
        return listBuses;
    }
    
    public void setListBuses(ArrayList<Vehicle> listBuses){
        this.listBuses = listBuses;
    }
    
    public int getMaximumFleetBuses(){
        return maximumFleetBuses;
    }
    
    public void setMaximumFleetBuses(int maximumFleetBuses){
        if(maximumFleetBuses > 0){
            this.maximumFleetBuses = maximumFleetBuses;
        }
    }
    
    //Método para incrementar la capacidad de la flota de vehículos.
    public void increaseFleetCapacity(int increase){
        if(increase > 0){
            this.maximumFleetBuses += increase;   
        }
    }
    
    //Método para decrementar la capacidad de la flota de vehículos.
    public void decreaseFleetCapacity(int decrease){
        if(decrease > 0 && (maximumFleetBuses - decrease > 0)){
            this.maximumFleetBuses += decrease;   
        }
    }
    
    //Método para generar los vehículos.
    private void generateVehicles(int countVehicles, int capacityVehicles) throws WithoutCapacityException{
        if(countVehicles > 0 && capacityVehicles > 0){
        
            for(int i = 0; i < countVehicles; i++){
            
                Vehicle v = new Vehicle(capacityVehicles, (i+1)+"", this.locationDepot);
                
                listBuses.add(v);
                
            }
            
        }
    }
    
     public int get_vehicle_capacity() {
        return bus_capacity;
    }

    public void set_vehice_capacity(int vehicle_capacity) {
        this.bus_capacity = bus_capacity;
    }
    
}
