/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.generator.controller;

import cujae.inf.citi.om.data.BusStop;
import cujae.inf.citi.om.data.Depot;
import cujae.inf.citi.om.data.Location;
import cujae.inf.citi.om.data.Passenger;
import cujae.inf.citi.om.data.Problem;
import cujae.inf.citi.om.data.ProblemType;
import cujae.inf.citi.om.data.Vehicle;
import cujae.inf.citi.om.distance.IDistance;
import cujae.inf.citi.om.exceptions.DistanceNotAccessibleException;
import cujae.inf.citi.om.exceptions.ItemNoFoundException;
import cujae.inf.citi.om.factory.interfaces.DistanceType;
import cujae.inf.citi.om.factory.interfaces.HeuristicType;
import cujae.inf.citi.om.distance.IDistance;
import cujae.inf.citi.om.distance.FactoryDistance;
import cujae.inf.citi.om.exceptions.WithoutCapacityException;
import cujae.inf.citi.om.factory.interfaces.IFactoryHeuristic;
import cujae.inf.citi.om.factory.methods.FactoryHeuristic;
import cujae.inf.citi.om.fileAccess.Access;
import cujae.inf.citi.om.generator.heuristic.Heuristic;
import cujae.inf.citi.om.generator.solution.Solution;
import cujae.inf.citi.om.generator.solution.Tools;
import libmatrix.cujae.inf.citi.om.matrix.NumericArray;
import libmatrix.cujae.inf.citi.om.matrix.NumericMatrix;
import libmatrix.cujae.inf.citi.om.matrix.RowCol;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author nanda
 */

//Clase controladora.

public class Controller {
    private static Controller controller = null;
    private Problem p;
    
    private Solution bestSolution;
    private ArrayList<Solution> listSolutions ;
    private long timeExecute;
    public boolean calculateTime = false;

    private Controller() {
        super();
	listSolutions = new ArrayList<Solution>();
        this.p = new Problem();  
    }
    
    //Método que implementa el Patrón Singleton.
    public static Controller getController(){
	if(controller==null)
            controller=new Controller();
	return controller;
    }
    
    public Problem getProblem() {
        return p;
    }
    
    public void setProblem(Problem p){
        this.p = p;
    }
    
    public Solution getBestSolution() {
	return bestSolution;
    }

    public void setBestSolution(Solution bestSolution) {
	this.bestSolution = bestSolution;
    }

    public ArrayList<Solution> getListSolutions() {
	return listSolutions;
    }

    public void setListSolutions(ArrayList<Solution> listSolutions) {
	this.listSolutions = listSolutions;
    }

    public long getTimeExecute() {
	return timeExecute;
    }
   
    public boolean getCalculateTime(){
        return calculateTime;
    }
    
    public void setCalculateTime(boolean calculateTime){
        this.calculateTime = calculateTime;
    }
    
    //Inicializar clases con una informacion guardada
    public void initializateData(String path) throws DistanceNotAccessibleException, WithoutCapacityException {

        HashMap<String, Object> values = Access.load_file(path);

        int max_distance = Integer.parseInt((String) values.get("Maximum_Walking_Distance"));

        ArrayList<String> passenger = (ArrayList<String>) values.get("Passenger");
        ArrayList<String> depot = (ArrayList<String>) values.get("Depot");
        ArrayList<String> bus_stop = (ArrayList<String>) values.get("Bus_Stop");

        p = new Problem(max_distance,  depot, bus_stop, passenger);

    }
    
    //Método para inicializar los datos a partir del fichero.
    public void initializateDataAssignation(String path) throws DistanceNotAccessibleException, WithoutCapacityException {
    
        HashMap<String, Object> values = Access.loadFileAssignation(path);
        
        
        double maximumDistance = Double.parseDouble((String)values.get("Maximum_Walking_Distance"));
       
        ArrayList<String> depot = (ArrayList<String>)values.get("Depot");
        ArrayList<String> busStop = (ArrayList<String>)values.get("Bus_Stop");
        
        //Quite la lista de pasajeros aqui
        p = new Problem( maximumDistance, depot, busStop);
        
    } 
    
    //Método encargado de crear una heurística de construcción.
    private Heuristic newHeuristic(HeuristicType heuristicType) throws IllegalArgumentException, SecurityException, 
                                                                       ClassNotFoundException, InstantiationException, 
                                                                       IllegalAccessException, InvocationTargetException, 
                                                                       NoSuchMethodException {
	IFactoryHeuristic iFactoryHeuristic = new FactoryHeuristic();
	Heuristic heuristic = iFactoryHeuristic.createHeuristic(heuristicType);
	return heuristic;
    }
      
    //Método encargado de crear una distancia.
    private IDistance newDistance(String typeDistance) throws IllegalArgumentException, SecurityException, 
                                                                   ClassNotFoundException, InstantiationException, 
                                                                   IllegalAccessException, InvocationTargetException, 
                                                                   NoSuchMethodException {
	IDistance distance = FactoryDistance.createDistance(typeDistance);
	return distance;
    }
    
    //Método encargado de cargar los datos de los pasajeros con coordenadas.
    private ArrayList<Passenger> loadPassenger(ArrayList<String> idPassengers){
	ArrayList<Passenger> listPassengers = new ArrayList<Passenger>();
	Passenger passenger;
	Location location;

	for (int i = 0; i < idPassengers.size(); i++) 
	{	
            location = new Location();
            //location.setAxisX(axisXPassengers.get(i));
            //location.setAxisY(axisYPassengers.get(i));

            passenger = new Passenger();
            passenger.setIdPassenger(idPassengers.get(i));
            passenger.setLocationPassenger(location);

            listPassengers.add(passenger);
	}

	return listPassengers;
    }

    //Método encargado de cargar los datos del problema.
    public boolean loadProblem(Problem p, ProblemType typeProblem, 
                               String typeDistance)throws IllegalArgumentException, SecurityException, ClassNotFoundException,
                               InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        boolean loaded = false;

	if(p != null)
	{
            p.setTypeProblem(typeProblem);
            p.setListPassengers(loadPassenger(p.getListIdPassengers()));
            ArrayList<String> idDepots = new ArrayList<>();
            idDepots.add("1");
            p.setListDepots(loadDepot(p.getInfoDepot()));

            loaded = true;
        }

	if(typeDistance == null)
            typeDistance = "Euclidean";

	p.create_cost_matrix_passenger_vs_bus_stop(typeDistance);

	return loaded;
    }
    
    //Método encargado de cargar los datos del problema con coordenadas y asignación predeterminada.
    public boolean loadProblemWithDataAssignation(Problem p, ProblemType typeProblem, 
                               String typeDistance)throws IllegalArgumentException, SecurityException, ClassNotFoundException,
                               InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        boolean loaded = false;

	if(p != null)
	{
            p.setTypeProblem(typeProblem);
            p.setListPassengers(loadPassenger(p.getListIdPassengers()));
            ArrayList<String> idDepots = new ArrayList<>();
            idDepots.add("1");
            p.setListDepots(loadDepot(p.getInfoDepot()));

            loaded = true;
        }

	if(typeDistance == null)
            typeDistance = "Euclidean";

	p.setCostMatrix(fillCostMatrix(p.getListBusesStop(), p.getListDepots(), typeDistance));

	return loaded;
    }

    //Método encargado de llenar la matriz de costo.
    private NumericMatrix fillCostMatrix(ArrayList<BusStop> listBusesStop, ArrayList<Depot> listDepots, String typeDistance) 
                                        throws IllegalArgumentException, SecurityException, ClassNotFoundException, 
                                        InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int size = listBusesStop.size() + listDepots.size(); 
	NumericMatrix costMatrix = new NumericMatrix(size, size);
	IDistance distance = newDistance(typeDistance);

	int row = -1;
	int col = -1;
	int lastBusStop = 0;
	double costInDistance = 0.0;

	for(int i = 0; i < size; i++)
	{
            if(i < listBusesStop.size())
		row = p.getPosElement(listBusesStop.get(i).getIdBusStop());
            else
		row = p.getPosElement(listDepots.get(i - listBusesStop.size()).getIdDepot());

            for(int j = (i + 1); j <= size; j++)
            {
		if(j < listBusesStop.size())
		{
                    col = p.getPosElement(listBusesStop.get(j).getIdBusStop());
                    costInDistance = distance.calculateDistance(listBusesStop.get(i).getLocationBusStop().getAxisX(),
                                                                listBusesStop.get(i).getLocationBusStop().getAxisY(), 
                                                                listBusesStop.get(j).getLocationBusStop().getAxisX(), 
                                                                listBusesStop.get(j).getLocationBusStop().getAxisY());
		}	//aquí calcular la distancia por la fórmula de Euclidean.
		else
		{
                    col = p.getPosElement(listDepots.get(lastBusStop).getIdDepot());

                    if(i < listBusesStop.size())
			costInDistance = distance.calculateDistance(listBusesStop.get(i).getLocationBusStop().getAxisX(),
                                                                    listBusesStop.get(i).getLocationBusStop().getAxisY(),
                                                                    listDepots.get(lastBusStop).getLocationDepot().getAxisX(),
                                                                    listDepots.get(lastBusStop).getLocationDepot().getAxisY());
                    else
			costInDistance = distance.calculateDistance(listDepots.get(i-listBusesStop.size()).getLocationDepot().getAxisX(), 
                                                                    listDepots.get(i - listBusesStop.size()).getLocationDepot().getAxisY(), 
                                                                    listDepots.get(lastBusStop).getLocationDepot().getAxisX(), 
                                                                    listDepots.get(lastBusStop).getLocationDepot().getAxisY());

                    //lastBusStop++;
		}

		costMatrix.setItem(row, col, costInDistance);	
		costMatrix.setItem(col, row, costInDistance);	
            }
	}
	return costMatrix;
    }

    //Método encargado de cargar los datos de los depósitos con coordenadas.
    private ArrayList<Depot> loadDepot(ArrayList<String> infoDepot) {
        ArrayList<Depot> listDepots = new ArrayList<Depot>();
	//ArrayList<Vehicle> listVehicles = new ArrayList<Vehicle>();
	Depot depot = new Depot();
	Location location = new Location();
        String input = infoDepot.get(0);
        String[] parts = input.split("");
        
        //for(int i = 0; i < infoDepot.size(); i++)
	//{
            location.setAxisX(Double.parseDouble(parts[0]));
            location.setAxisY(Double.parseDouble(parts[1]));

            depot.setIdDepot("1");
            depot.setLocationDepot(location);

            //listVehicles = listDepots.get(0).getListBuses();
            //Vehicle vehicle;

           /* for(int j = 0; j < listVehicles.size(); j++)
            {
		vehicle = new Vehicle();
		//vehicle.setCountVehicles(countVehicles.get(j));
		//vehicle.setCapacityBus(capacityVehicles.get(j));

		listVehicles.add(vehicle);
            }*/

            //depot.setListBuses(listVehicles);
            listDepots.add(depot);
	//}

	return listDepots;
    }
    
    //Método que devuelve todas las soluciones obtenidas con la heurística.
    public ArrayList<ArrayList<Object>> getAllSolutions (){
	ArrayList<ArrayList<Object>> allSolutions = new ArrayList<ArrayList<Object>>();
	ArrayList<Object> code = new ArrayList<Object>();

	for (int i = 0; i < listSolutions.size(); i++) 
	{
            for (int j = 0; j < listSolutions.get(i).getListRoutes().size(); j++) 
		for (int k = 0; k < listSolutions.get(i).getListRoutes().get(j).getListIdBusesStop().size(); k++) 
                    code.add(listSolutions.get(i).getListRoutes().get(j).getListIdBusesStop().get(k));	

            allSolutions.add(code);
        }
	return allSolutions;
    }
    
    //Método que devuelve la cantidad de rutas de una solución.
    public int countRoutes(){
	int countRoutes = bestSolution.getListRoutes().size();

	return countRoutes;
    }
    
    //Método que ejecuta una heurística de construcción.
    public void executeHeuristic(int countExecution, HeuristicType heuristicType, Problem p) throws IllegalArgumentException, SecurityException,
                                                                                         ClassNotFoundException, InstantiationException,
                                                                                         IllegalAccessException, InvocationTargetException,
                                                                                         NoSuchMethodException, ItemNoFoundException{
        if(calculateTime == true)
            timeExecute = System.currentTimeMillis();
        
        Heuristic heuristic = newHeuristic(heuristicType);

	for(int i = 1; i <= countExecution; i++)
	{
            Solution currentSolution = heuristic.getSolutionInicial(p);
            //currentSolution.calculateCost(p);
            listSolutions.add(currentSolution);

            if(i == 1)
		bestSolution = currentSolution;
            /*else
		if(Tools.roundDouble(currentSolution.getCostSolution(), 2) < Tools.roundDouble(bestSolution.getCostSolution(), 2))
                    bestSolution = currentSolution; */
	}

	if(calculateTime == true)
	{
            timeExecute -= System.currentTimeMillis();
            timeExecute = Math.abs(timeExecute);
	}
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     public int cant_bus_stop() {

        return p.get_cant_bus_stop();

    }

    public int cant_passenger() {

        return p.get_cant_passenger();

    }

    public ArrayList<HashMap<String, String>> passengers_info() {

        return p.passengers_info();

    }

    public ArrayList<HashMap<String, String>> bus_stop_info() {

        return p.bus_stop_info();

    }
    
    public double maximun_walk_distance(){
    
        return p.getMaximumWalKDistance();
        
    }
    
    public HashMap<String, ArrayList<String>> passenger_assigned_by_bus_stop(String bus_stop) throws ItemNoFoundException {

        return p.passenger_assigned_by_bus_stop(bus_stop);

    }
     
    public double maximum_distance_walk(){
    
        return p.getMaximumWalKDistance();
        
    }
    
    public int cant_bus_Stop(){
    
        return p.get_cant_bus_stop();
        
    }
    
    public int cant_bus(){
    
        return p.cant_bus();
        
    }
    
    public int bus_capacity(){
    
        return p.bus_capacity();
        
    }
    
    public ArrayList<HashMap<String, String>> routes_info() {

        return this.bestSolution.routes_info();

    }
}
