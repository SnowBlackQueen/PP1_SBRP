/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.data;

import java.text.DecimalFormat;

/**
 *
 * @author nanda
 */

//Clase que modela la ubicación geográfica de un pasajero a partir de sus coordenadas 
//cartesianas, en un SBRP.

public class Location {
    DecimalFormat df = new DecimalFormat("#.000");
    private double axisX;
    private double axisY;
    private String address;
	
    public Location(){
	super();
    }

    public Location(double axisX, double axisY){
	setAxisX(axisX);
        setAxisY(axisY);
    }
    
    public Location(String address){
        setAddress(address);
    }

    public double getAxisX() {
	return axisX;
    }

    public void setAxisX(double axisX) {
	this.axisX = axisX;
    }

    public double getAxisY() {
	return axisY;
    }

    public void setAxisY(double axisY) {
	this.axisY = axisY;
    }	
    
    public String getAddress(){
        return address;
    }
	
    public void setAddress(String address) throws IllegalArgumentException{
        if(!address.equals(null)){
            this.address = address;
        }
        else
            throw new IllegalArgumentException();
    }
    
    //Método que devuelve para un punto su coordenada polar Theta.
    public double getPolarTheta(){
	return Math.atan(axisY/axisX);
    }
	
    //Método que devuelve para un punto su coordenada polar Rho.
    public double getPolarRho(){
	return Math.sqrt((Math.pow(axisX, 2) + Math.pow(axisY, 2)));
    }
     
    //Método para concatenar coordenadas x,y.
    public String joinLocation(){
        String location = df.format(axisX) + " , " + df.format(axisY) ;
        return location;
        
    }
    
}
