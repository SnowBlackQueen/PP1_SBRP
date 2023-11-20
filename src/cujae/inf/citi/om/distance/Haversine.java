/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.distance;

/**
 *
 * @author nanda
 */

//Clase que modela cómo calcular la distancia mediante la fórmula Haversine.

public class Haversine extends IDistance{
    public static final double EARTH_RADIUS_KM = 6371;
	
    public Haversine() {
	super();
	// TODO Auto-generated constructor stub
    }

    @Override
    public Double calculateDistance(double axisXStart, double axisYStart, double axisXEnd, double axisYEnd) {
	double distance = 0.0;
		
	double longitudeStart = axisXStart * (Math.PI/180);
	double latitudeStart = axisYStart * (Math.PI/180);
		
	double longitudeEnd = axisXEnd * (Math.PI/180);
	double latitudeEnd = axisYEnd * (Math.PI/180);
		
	double difLatitude = latitudeEnd - latitudeStart;
	double difLongtitude = longitudeEnd - longitudeStart;
		
	distance = Math.pow(Math.sin((difLatitude/2)), 2) + Math.cos(latitudeStart) * Math.cos(latitudeEnd) * Math.pow(Math.sin((difLongtitude/2)), 2);		
	distance = 2 * EARTH_RADIUS_KM * Math.atan2(Math.sqrt(distance), Math.sqrt((1 - distance)));
		
	return distance;
    }
    
}
