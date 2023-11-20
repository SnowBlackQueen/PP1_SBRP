/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.data;

/**
 *
 * @author nanda
 */

//Clase que modela los datos de un pasajero en un SBRP.

public class Passenger {
    private String idPassenger;
    private PassengerType typePassenger;
    private Location locationPassenger;
    
    public Passenger(){
        super();
    }
    
    public Passenger(String idPassenger, Location locationPassenger){
        super();
	setIdPassenger(idPassenger);
        setLocationPassenger(locationPassenger); 
    }
    
    public Passenger(PassengerType typePassenger){
        setTypePassenger(typePassenger);
    }
    
    public String getIdPassenger(){
        return idPassenger;
    }
    
    public void setIdPassenger(String idPassenger) throws IllegalArgumentException{
        if(!idPassenger.equals(null)){
            this.idPassenger = idPassenger;
        }
        else
            throw new IllegalArgumentException();
    }
    
    public PassengerType getTypePassenger(){
        return typePassenger;
    }
    
    public void setTypePassenger(PassengerType typePassenger) throws IllegalArgumentException{
        if(!typePassenger.equals(null)){
            this.typePassenger = typePassenger;
        }
        else
            throw new IllegalArgumentException();
    }
    
    public Location getLocationPassenger(){
        return locationPassenger;
    }
    
    public void setLocationPassenger(Location locationPassenger) throws IllegalArgumentException{
        if(!locationPassenger.equals(null)){
            this.locationPassenger = locationPassenger;
        }
        else 
            throw new IllegalArgumentException();
    }
    
    public String get_coordinates(){
    
        return this.locationPassenger.getAxisX() + " " + this.locationPassenger.getAxisY();
        
    }
    
}
