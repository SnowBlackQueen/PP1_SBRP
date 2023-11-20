/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.generator.solution;

import java.util.ArrayList;
import cujae.inf.citi.om.data.Problem;

/**
 *
 * @author nanda
 */

//Clase que modela los datos de una ruta en un SBRP.

public class Route {
    protected ArrayList<String> listIdBusesStop;
    protected int requestRoute;
    protected int costRoute;
    protected String idDepot;
	
    public Route() {
	super();
	this.listIdBusesStop = new ArrayList<String>();
	this.requestRoute = 0;
        setCostRoute(costRoute);
    }
	
    public Route(ArrayList<String> listIdPassengers, int requestRoute, int costRoute, String idDepot) {
	super();
	this.listIdBusesStop = new ArrayList<String>(listIdPassengers);
	setRequestRoute(requestRoute);
        setCostRoute(costRoute);
        setIdDepot(idDepot);
    }

    public ArrayList<String> getListIdBusesStop() {
	return listIdBusesStop;
    }
	
    public void setListIdPassengers(ArrayList<String> listIdPassengers) {
	this.listIdBusesStop = listIdPassengers;
    }
	
    public int getRequestRoute() {
	return requestRoute;
    }

    public void setRequestRoute(int requestRoute) {
	if(requestRoute >= 0){
            this.requestRoute = requestRoute;
        }
    }

    public int getCostRoute() {
	return costRoute;
    }

    public void setCostRoute(int costRoute) {
	this.costRoute = costRoute;
    }

    public String getIdDepot() {
	return idDepot;
    }

    public void setIdDepot(String idDepot) throws IllegalArgumentException{
	if(!idDepot.equals(null)){
            this.idDepot = idDepot;
        }
        else
            throw new IllegalArgumentException();
    }
	
    //Método que calcula el costo de una ruta simple.
    public int getCostSingleRoute(Problem p){
	int costRoute = 0;
	String busStopInitial;
	String busStopNext;
	int posBusStopInitial = -1;
	int posBusStopNext = -1;
        int depot = p.getPosElement(idDepot);

	busStopInitial = listIdBusesStop.get(0);
	posBusStopInitial = p.getPosElement(busStopInitial);
		
	costRoute += p.getCostMatrix().getItem(depot, posBusStopInitial);

	for(int i = 1; i < listIdBusesStop.size(); i++)
	{
            busStopNext = listIdBusesStop.get(i);
            posBusStopNext = p.getPosElement(busStopNext);
			
            costRoute += p.getCostMatrix().getItem(posBusStopInitial, posBusStopNext);
			
            busStopInitial = busStopNext;
            posBusStopInitial = posBusStopNext;
	}
		
	costRoute += p.getCostMatrix().getItem(posBusStopInitial, depot);
	setCostRoute(costRoute);
		
	return costRoute;
    }
}
