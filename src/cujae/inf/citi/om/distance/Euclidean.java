/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.distance;

/**
 *
 * @author nanda
 */

//Clase que modela cómo calcular la distancia mediante la fórmula Euclidiana.

public class Euclidean extends IDistance{
    public Euclidean() {
	super();
	// TODO Auto-generated constructor stub
    }

    @Override
    public Double calculateDistance(double axisXStart, double axisYStart, double axisXEnd, double axisYEnd) {
	double distance = 0.0;
	double axisX = 0.0;
	double axisY = 0.0;
		
	axisX = Math.pow((axisXStart - axisXEnd), 2);
	axisY = Math.pow((axisYStart - axisYEnd), 2);
	distance = Math.sqrt((axisX + axisY));
		
	return distance;
    }
    
}
