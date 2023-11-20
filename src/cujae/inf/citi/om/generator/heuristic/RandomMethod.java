/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.generator.heuristic;

import java.util.ArrayList;
import java.util.Random;

import cujae.inf.citi.om.data.Passenger;
import cujae.inf.citi.om.data.BusStop;
import cujae.inf.citi.om.data.Depot;
import cujae.inf.citi.om.data.Problem;
import cujae.inf.citi.om.exceptions.ItemNoFoundException;
import cujae.inf.citi.om.factory.interfaces.HeuristicType;
import cujae.inf.citi.om.generator.solution.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nanda
 */

//Clase que modela un método de construcción de forma aleatoria.

public class RandomMethod extends Heuristic{
    public RandomMethod() {
	super();
	// TODO Auto-generated constructor stub
    }

    @Override
    public Solution getSolutionInicial(Problem p) throws IllegalArgumentException {
	if(!p.equals(null)){
            Solution solution = new Solution();
            //ArrayList<Passenger> passengersToVisit = null;

            //Porque es un problema de tipo SBRP.
            String idDepot = "1";
            //String idDepot = p.getListDepots().get(posDepot).getIdDepot();
            //passengersToVisit = new ArrayList<Passenger>(p.getListPassengers());

            int countBuses = Integer.valueOf(p.getInfoDepot().get(1));
            int capacityBuses = Integer.valueOf(p.getInfoDepot().get(2));

            //Passenger passenger = new Passenger();
            BusStop busStop = new BusStop();
            Route route = new Route();

            ArrayList<BusStop> busesStopToVisit = new ArrayList<BusStop>(p.getListBusesStop());
            //passenger = getRandomPassenger(passengersToVisit);
            busStop = getRandomBusStop(busesStopToVisit);
            
            //BusStop busStop = new BusStop();
            int requestRoute = busesStopToVisit.get(0).getCapacityBusStop();
            //route.getListIdPassengers().add(passenger.getIdPassenger());
            //passengersToVisit.remove(passenger);
            route.getListIdBusesStop().add(busStop.getIdBusStop());
            busesStopToVisit.remove(busStop);

            switch(p.getTypeProblem().ordinal())
            {
                case 0:
                {
                    //(!passengersToVisit.isEmpty()) &&
                    while((!busesStopToVisit.isEmpty()) && (countBuses > 0))
                    {
                        //for(int i = 1; i < paradas.size(); i++){
                            //passenger = getRandomPassenger(passengersToVisit);

                            busStop = getRandomBusStop(busesStopToVisit);
                            
                            if(capacityBuses >= (requestRoute + busStop.getCapacityBusStop()))
                            {
                                requestRoute += busStop.getCapacityBusStop();
                                //route.getListIdPassengers().add(passenger.getIdPassenger());
                                //passengersToVisit.remove(passenger);
                                route.getListIdBusesStop().add(busStop.getIdBusStop());
                                busesStopToVisit.remove(busStop);
                            }
                            else
                            {
                                route.setRequestRoute(requestRoute);
                                route.setIdDepot(idDepot);
                                solution.getListRoutes().add(route);

                                route = null;
                                --countBuses;

                                if(countBuses > 0)
                                {
                                    route = new Route();

                                    requestRoute = busStop.getCapacityBusStop();
                                    //route.getListIdPassengers().add(passenger.getIdPassenger());
                                    //passengersToVisit.remove(passenger);
                                    route.getListIdBusesStop().add(busStop.getIdBusStop());
                                    busesStopToVisit.remove(busStop);
                                }
                            }	
                        //}
                        
                    }

                    if(route != null)
                    {
                        route.setRequestRoute(requestRoute);
                        route.setIdDepot(idDepot);
                        solution.getListRoutes().add(route);
                    }

                    //!passengersToVisit.isEmpty()
                    if(!busesStopToVisit.isEmpty())
                    {
                        route = new Route();
                        requestRoute = 0;

                        //!passengersToVisit.isEmpty()
                        while(!busesStopToVisit.isEmpty())
                        {
                            int j = 0;
                            boolean found = false;	

                            requestRoute = solution.getListRoutes().get(j).getRequestRoute();

                            while((j < solution.getListRoutes().size()) && (!found))
                            {	
                                if(capacityBuses >= (requestRoute + busStop.getCapacityBusStop()))
                                {
                                    solution.getListRoutes().get(j).setRequestRoute(requestRoute + busStop.getCapacityBusStop());
                                    //solution.getListRoutes().get(j).getListIdPassengers().add(passenger.getIdPassenger());
                                    //passengersToVisit.remove(passenger);
                                    solution.getListRoutes().get(j).getListIdBusesStop().add(busStop.getIdBusStop());
                                    busesStopToVisit.remove(busStop);

                                    found = true;
                                }
                                else
                                {
                                    j++;	
                                    requestRoute = solution.getListRoutes().get(j).getRequestRoute();
                                }	
                            }

                            if(!found)
                            {
                                //route.getListIdPassengers().add(passenger.getIdPassenger());
                                route.getListIdBusesStop().add(busStop.getIdBusStop());
                                route.setRequestRoute(route.getRequestRoute() + busesStopToVisit.get(1).getCapacityBusStop());
                                //passengersToVisit.remove(passenger);
                                busesStopToVisit.remove(busStop);
                            }

                            //!passengersToVisit
                            if(!busesStopToVisit.isEmpty())
                              // passenger = getRandomPassenger(passengersToVisit);
                                busStop = getRandomBusStop(busesStopToVisit);
                        }

                        if(!route.getListIdBusesStop().isEmpty())
                        {
                            route.setIdDepot(idDepot);
                            solution.getListRoutes().add(route);
                        }
                    }

                    break;
                }

            }

            return solution;
        }
        else
            throw new IllegalArgumentException();    
    }

    //Método que devuelve una parada de la lista de forma aleatoria.
    private BusStop getRandomBusStop(ArrayList<BusStop> listBusesStop){
	BusStop busStop = new BusStop();		
	Random random = new Random();
	int index = -1;
		
	index = random.nextInt(listBusesStop.size());
	busStop = listBusesStop.get(index);
		
	return busStop;
    }
    
     //Método que devuelve un pasajero de la lista de forma aleatoria.
    private Passenger getRandomPassenger(ArrayList<Passenger> listPassengers){
	Passenger passenger = new Passenger();		
	Random random = new Random();
	int index = -1;
		
	index = random.nextInt(listPassengers.size());
	passenger = listPassengers.get(index);
		
	return passenger;
    }
}
