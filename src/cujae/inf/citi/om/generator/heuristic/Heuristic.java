/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.generator.heuristic;

import cujae.inf.citi.om.data.BusStop;
import java.util.ArrayList;

import cujae.inf.citi.om.data.Passenger;
import cujae.inf.citi.om.data.Problem;
import cujae.inf.citi.om.generator.solution.Solution;

/**
 *
 * @author nanda
 */

//Clase abstracta que modela una heurística de construcción.

public abstract class Heuristic {
	
    //Método abstracto encargado de generar la solución.
    public abstract Solution getSolutionInicial(Problem p);
	 
    //Método que busca un pasajero por su identificador.
    protected BusStop getBusStopById(int idBusStop, ArrayList<BusStop> listBusesStop){
	int i = 0;
	boolean found = false;
	BusStop busStop = new BusStop();
		
	while((i < listBusesStop.size()) && (!found))
	{
            if(listBusesStop.get(i).getIdBusStop().equals(idBusStop))
            {
		busStop = listBusesStop.get(i);
		found = true;
            }
            else
		 i++;
        }
	return busStop;
    }
	
    protected void AscendentOrdenate(ArrayList<Metric> listWithOutOrder){
	double minorInsertionCost;
	Metric minorMetric;
	int referencePos;

	for(int i = 0; i < listWithOutOrder.size(); i++)
	{
            minorInsertionCost = listWithOutOrder.get(i).getInsertionCost();
            minorMetric = listWithOutOrder.get(i);
            referencePos = i;
	
            Metric currentMetric = new Metric();
            int currentPos = -1;
			
            for (int j = (i + 1); j < listWithOutOrder.size(); j++) 
            {
		if ((listWithOutOrder.get(j).getInsertionCost()) < minorInsertionCost)
		{
                    minorInsertionCost = listWithOutOrder.get(j).getInsertionCost();
                    currentMetric = listWithOutOrder.get(j);
                    currentPos = j;
		}
            }
			
            if(currentPos != -1)
            {
		listWithOutOrder.set(referencePos, currentMetric);
		listWithOutOrder.set(currentPos, minorMetric);	
            }
	}
    }
	
    protected void AscendentOrdenate(ArrayList<Double> listDistances, ArrayList<BusStop> listNN){
	Double minorDistance;
	BusStop busStopNN;
	int referencePos;

	for(int i = 0; i < listDistances.size(); i++)
	{
            minorDistance = listDistances.get(i);
            busStopNN = listNN.get(i);
            referencePos = i;

            Double currentDistance = 0.0;
            BusStop currentBusStop = new BusStop();
            int currentPos = -1;

            for(int j = (i + 1); j < listDistances.size(); j++)
            {
		if(minorDistance > listDistances.get(j))
		{
                    currentDistance = listDistances.get(j);
                    currentBusStop = listNN.get(j);
                    currentPos = j;
		}
            }
			
            if(currentPos != -1)
            {
		listDistances.set(referencePos, currentDistance);
		listDistances.set(currentPos, minorDistance);

		listNN.set(referencePos, currentBusStop);
		listNN.set(currentPos, busStopNN);	
            }
	}
    }
    
}
