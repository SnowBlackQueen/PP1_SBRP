/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.generator.heuristic;

import java.util.ArrayList;
import java.util.Random;

import cujae.inf.citi.om.data.Passenger;
import cujae.inf.citi.om.data.BusStop;
import cujae.inf.citi.om.data.Problem;
import cujae.inf.citi.om.exceptions.ItemNoFoundException;
import cujae.inf.citi.om.exceptions.RLC_Exception;
import cujae.inf.citi.om.generator.solution.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nanda
 */

//Clase que modela la heurística del Vecino más Cercano con lista de candidatos restringidos.

public class NearestNeighborWithRLC extends Heuristic{
    public static int sizeRLC = 1;
	
    public NearestNeighborWithRLC() {
	super();
	// TODO Auto-generated constructor stub
    }

    public void setSizeRLC(int rlc, Problem p) throws RLC_Exception{
        int temp = p.getListBusesStop().size() / 2; 
        if((rlc <= temp) && (rlc > 0))
            NearestNeighborWithRLC.sizeRLC = rlc;
        else
            throw new RLC_Exception("La lista de candidatos restringidos debe ser mayor que cero y menor que la mitad de la cantidad de paradas.");
    }
    
    @Override
    public Solution getSolutionInicial(Problem p) throws IllegalArgumentException{
	if(!p.equals(null)){
            if(sizeRLC == 0)
                sizeRLC = 1;

            Solution solution = new Solution();
            //ArrayList<Passenger> PassengersToVisit = null;

            //Porque es un problema de tipo SBRP.
            int posDepot = 0;
            String idDepot = p.getListDepots().get(posDepot).getIdDepot();
            //PassengersToVisit = new ArrayList<Passenger>(p.getListPassengers());	

            int countBuses = Integer.valueOf(p.getInfoDepot().get(1));
            int capacityBus = Integer.valueOf(p.getInfoDepot().get(2));

            //Passenger passenger = new Passenger();
            BusStop busStop = new BusStop();
            Route route = new Route();
            ArrayList<BusStop> busesStopToVisit = new ArrayList<BusStop>(p.getListBusesStop());
            
            int requestRoute = busesStopToVisit.get(0).getCapacityBusStop();

            try {
                busStop = getNNBusStop(busesStopToVisit, idDepot, p);
            } catch (ItemNoFoundException ex) {
                Logger.getLogger(NearestNeighborWithRLC.class.getName()).log(Level.SEVERE, null, ex);
            }
            route.getListIdBusesStop().add(busStop.getIdBusStop());
            busesStopToVisit.remove(busStop);

            switch(p.getTypeProblem().ordinal())
            {
                case 0:
                {
                    while((!busesStopToVisit.isEmpty()) && (countBuses > 0))
                    {
                        try {
                            busStop = getNNBusStop(busesStopToVisit, busStop.getIdBusStop(), p);
                            
                            if(capacityBus >= (requestRoute + busStop.getCapacityBusStop()))
                            {
                                requestRoute += busStop.getCapacityBusStop();
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
                                    route.getListIdBusesStop().add(busStop.getIdBusStop());
                                    busesStopToVisit.remove(busStop);
                                }
                            }
                        } catch (ItemNoFoundException ex) {
                            Logger.getLogger(NearestNeighborWithRLC.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if(route != null)
                    {
                        route.setRequestRoute(requestRoute);
                        route.setIdDepot(idDepot);
                        solution.getListRoutes().add(route);
                    }

                    if(!busesStopToVisit.isEmpty())
                    {
                        route = new Route();
                        requestRoute = 0;

                        while(!busesStopToVisit.isEmpty())
                        {
                            try {
                                int j = 0;
                                boolean found = false;
                                
                                requestRoute = solution.getListRoutes().get(j).getRequestRoute();
                                
                                while((j < solution.getListRoutes().size()) && (!found))
                                {
                                    if(capacityBus >= (requestRoute + busStop.getCapacityBusStop()))
                                    {
                                        solution.getListRoutes().get(j).setRequestRoute(requestRoute + busStop.getCapacityBusStop());
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
                                    route.getListIdBusesStop().add(busStop.getIdBusStop());
                                    route.setRequestRoute(route.getRequestRoute() + busStop.getCapacityBusStop());
                                    busesStopToVisit.remove(busStop);		
                                }
                                
                                if(!busesStopToVisit.isEmpty())
                                    busStop = getNNBusStop(busesStopToVisit, busStop.getIdBusStop(), p);
                            } catch (ItemNoFoundException ex) {
                                Logger.getLogger(NearestNeighborWithRLC.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
	
    //Método que devuelve el cliente más cercano al cliente referencia.
    private BusStop getNNBusStop(ArrayList<BusStop> listBusesStop, String reference, Problem p) throws IllegalArgumentException, ItemNoFoundException{
	if(!reference.equals(null)){
            if(!p.equals(null)){
                BusStop busStop = new BusStop();
                int RLC = -1;

                if(listBusesStop.size() == 1)
                    busStop = listBusesStop.get(0);
                else
                {
                    ArrayList<BusStop> listNN = getListNN(listBusesStop, reference, p);
                    ArrayList<BusStop> listRLC = new ArrayList<BusStop>();

                    RLC = Math.min(sizeRLC, listBusesStop.size());

                    for(int i = 0; i < RLC; i++)	
                        listRLC.add(listNN.get(i));		

                    Random random = new Random();
                    int index = random.nextInt(RLC);	
                    busStop = listRLC.remove(index);
                }

                return busStop;
            }
            else
                throw new IllegalArgumentException();
        }
        else
            throw new IllegalArgumentException();
    }

    //Método que devuelve la lista de vecinos más cercanos.
    private ArrayList<BusStop> getListNN(ArrayList<BusStop> listBusesStop, String reference, Problem p) throws IllegalArgumentException, ItemNoFoundException{
	if(!reference.equals(null)){
            if(!p.equals(null)){
                ArrayList<Double> listDistances = new ArrayList<Double>();
                ArrayList<BusStop> listNN = new ArrayList<BusStop>();
                double refDistance = 0.0;
                int row = p.getPosElement(reference);
                int col = p.getPosElement(listBusesStop.get(0).getIdBusStop());

                for (int i = 0 ; i < listBusesStop.size(); i++)
                {
                    refDistance = p.getCostMatrix().getItem(p.getPosElement(reference),
                                                            p.get_bus_stop_position(listBusesStop.get(i).getIdBusStop()));
                    listDistances.add(refDistance);
                    listNN.add(listBusesStop.get(i));
                }

                AscendentOrdenate(listDistances, listNN);

                return listNN;
            }
            else
                throw new IllegalArgumentException();
        }
        else
            throw new IllegalArgumentException();
    }
    
}
