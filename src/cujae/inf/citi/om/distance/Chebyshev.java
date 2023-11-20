/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.distance;

/**
 *
 * @author nanda
 */

//Clase que modela como calcular la distancia mediante la fórmula de Chebyshev.

public class Chebyshev extends IDistance{
    public Chebyshev() {
	super();
	// TODO Auto-generated constructor stub
    }

    @Override
    public Double calculateDistance(double axisXStart, double axisYStart, double axisXEnd, double axisYEnd) {
	double distance = 0.0;
	double axisX = 0.0;
	double axisY = 0.0;
		
	axisX = Math.abs(axisXStart - axisXEnd);
	axisY = Math.abs(axisYStart - axisYEnd);
	distance = Math.max(axisX, axisY);
		
	return distance;
    }
    
}
