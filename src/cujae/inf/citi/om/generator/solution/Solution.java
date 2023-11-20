/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.generator.solution;

import cujae.inf.citi.om.data.Passenger;
import java.util.ArrayList;
import cujae.inf.citi.om.data.Problem;
import cujae.inf.citi.om.data.ProblemType;
import java.util.HashMap;

/**
 *
 * @author nanda
 */

//Clase que modela los datos de una solución en un SBRP.

public class Solution {
    private ArrayList<Route> listRoutes;
	
    public Solution() {
	super();
	listRoutes = new ArrayList<Route>();
	// TODO Auto-generated constructor stub
    }

    public Solution(ArrayList<Route> listRoutes) {
	super();
	setListRoutes(listRoutes);
    }

    public ArrayList<Route> getListRoutes() {
	return listRoutes;
    }

    public void setListRoutes(ArrayList<Route> listRoutes) {
	this.listRoutes = listRoutes;
    }
	
    //Método que calcula el costo total de la solución.
    public Double calculateCost(Problem p){
	double totalCost = 0.0;

	for(int i = 0; i < listRoutes.size(); i++)
	{
            if(p.getTypeProblem().equals(ProblemType.SBRP)){
		//listRoutes.get(i).setCostRoute(listRoutes.get(i).getCostSingleRoute(p));
                totalCost += listRoutes.get(i).getCostSingleRoute(p);
            }
	}
		
	return totalCost;	
    }
	
    //Método que devuelve el costo total de la solución.
    public Double getCostSolution(){
	double costSolution = 0.0;
		
	for(int i = 0; i < listRoutes.size(); i++)
            costSolution += listRoutes.get(i).getCostRoute();
		
	return costSolution;
    }
    
    //Interfaz
    public ArrayList<HashMap<String, String>> routes_info() {

        ArrayList<HashMap<String, String>> routes = new ArrayList<>();
        int i = 0;
        
        for (Route r : this.listRoutes) {
            HashMap<String, String> values = new HashMap<>();
            values.put("id route", ("Ruta " + (i+1)));
            String route = "";
            for(int j = 0; j < r.getListIdBusesStop().size(); j++)
               if(route.isEmpty())
                   route = r.getListIdBusesStop().get(j);
                else
                   route = route + " -> " + r.getListIdBusesStop().get(j);
            values.put("bus stop", route);

            routes.add(values);
            i++;
        }

        return routes;

    }
}
