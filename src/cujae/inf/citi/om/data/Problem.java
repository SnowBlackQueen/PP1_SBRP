/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.data;

import cujae.inf.citi.om.distance.FactoryDistance;
import cujae.inf.citi.om.distance.IDistance;
import cujae.inf.citi.om.exceptions.DistanceNotAccessibleException;
import cujae.inf.citi.om.exceptions.ItemNoFoundException;
import cujae.inf.citi.om.exceptions.WithoutCapacityException;
import cujae.inf.citi.om.factory.interfaces.HeuristicType;
import java.lang.reflect.InvocationTargetException;
import libmatrix.cujae.inf.citi.om.matrix.NumericArray;
import libmatrix.cujae.inf.citi.om.matrix.NumericMatrix;
import libmatrix.cujae.inf.citi.om.matrix.RowCol;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author nanda
 */
//Clase que modela los datos de un SBRP.
public class Problem {

    private ArrayList<Passenger> listPassengers;
    private ArrayList<Depot> listDepots;
    private ArrayList<String> infoDepot;
    private ArrayList<BusStop> listBusesStop;
    private ProblemType typeProblem;
    private NumericMatrix costMatrix;
    private double maximumWalkDistance;
    private String distance_used;
    private String heuristic_used;
    private int rlc = 1;

    private ArrayList<Double> listCapacities = null;

    public Problem() {
        super();
    }

    //
    public Problem(double maximumWalkDistance, ArrayList<String> infoDepot,
                   ArrayList<String> listBusesStop) throws DistanceNotAccessibleException, WithoutCapacityException {
        setMaximumWalkDistance(maximumWalkDistance);
        this.listPassengers = new ArrayList<>();
        this.listDepots = new ArrayList<>();
        this.infoDepot = new ArrayList<>();
        setInfoDepot(infoDepot);
        costMatrix = new NumericMatrix();
        this.listBusesStop = new ArrayList<BusStop>();
        this.initializate_data_assigntion(listBusesStop);
    }

    public Problem(double maximumWalkDistance, ArrayList<String> infoDepot,
                   ArrayList<String> listBusesStop,  ArrayList<String> listPassengers) throws DistanceNotAccessibleException, WithoutCapacityException {
        setMaximumWalkDistance(maximumWalkDistance);
        this.listPassengers = new ArrayList<>();
        this.listDepots = new ArrayList<>();
        this.infoDepot = new ArrayList<>();
        setInfoDepot(infoDepot);
        costMatrix = new NumericMatrix();
        this.listBusesStop = new ArrayList<BusStop>();
        this.initializateData(listPassengers, infoDepot, listBusesStop);
    }
    
    public ArrayList<Passenger> getListPassengers() {
        return listPassengers;
    }

    public void setListPassengers(ArrayList<Passenger> listPassengers) {
        this.listPassengers = listPassengers;
    }

    public ArrayList<Depot> getListDepots() {
        return listDepots;
    }

    public void setListDepots(ArrayList<Depot> listDepots) {
        this.listDepots = listDepots;
    }

    public ArrayList<String> getInfoDepot(){
        return infoDepot;
    }
    
    public void setInfoDepot(ArrayList<String> infoDepot){
        this.infoDepot = infoDepot;
    }
    
    public ArrayList<BusStop> getListBusesStop() {
        return listBusesStop;
    }

    public void setListBusesStop(ArrayList<BusStop> listBusesStop) {
        this.listBusesStop = listBusesStop;
    }

    public ProblemType getTypeProblem() {
        return typeProblem;
    }

    public void setTypeProblem(ProblemType typeProblem) throws IllegalArgumentException {
        if (!typeProblem.equals(null)) {
            this.typeProblem = typeProblem;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setTypeProblem(int typeProblem) {
        switch (typeProblem) {
            case 0: {
                this.typeProblem = ProblemType.SBRP;
                break;
            }

            case 1: {
                this.typeProblem = ProblemType.SBRPHV;
                break;
            }

            case 2: {
                this.typeProblem = ProblemType.SBRPPD;
                break;
            }

            case 3: {
                this.typeProblem = ProblemType.SBRPMO;
                break;
            }
        }
    }

    public NumericMatrix getCostMatrix() {
        return costMatrix;
    }

    public void setCostMatrix(NumericMatrix costMatrix) throws IllegalArgumentException {
        if (!costMatrix.equals(null)) {
            this.costMatrix = costMatrix;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getMaximumWalKDistance() {
        return maximumWalkDistance;
    }

    public void setMaximumWalkDistance(double maximumWalkDistance) throws DistanceNotAccessibleException {
        if (maximumWalkDistance > 0) {
            this.maximumWalkDistance = maximumWalkDistance;
        }
    }

    public ArrayList<Double> getListCapacities() {
        return listCapacities;
    }

    public void setListCapacities(ArrayList<Double> listCapacities) {
        this.listCapacities = listCapacities;
    }

    public String getDistanceUsed(){
        return this.distance_used;
    }
    
    public String getHeuristicUsed(){
        return this.heuristic_used;
    }
    public void RLC(int rlc){
        this.rlc  = rlc;
    }
    public int getRLC(){
        return this.rlc;
    }
    public void setDistance(String distance_used){
        this.distance_used = distance_used;
    }
    public void setHeuristic(String heuristic_used){
        this.heuristic_used = heuristic_used;
    }
    
    //Método para incrementar la distancia máxima a recorrer a pie.
    public void increaseDistanceToWalk(int increase) throws DistanceNotAccessibleException {
        if (increase > 0) {
            this.maximumWalkDistance += increase;
        }
    }

    //Método para decrementar la distancia máxima a recorrer a pie.
    public void decreaseDistanceToWalk(int decrease) throws DistanceNotAccessibleException {
        if (decrease > 0 && (maximumWalkDistance - decrease > 0)) {
            this.maximumWalkDistance -= decrease;
        }
    }

    //Método para generar los depósitos al cargar un fichero.
    private void generateDepots(ArrayList<String> infoDepot) throws WithoutCapacityException {
        int max = infoDepot.size() / 3;

        for (int i = 0; i < max; i++) {

            String[] coordinates = infoDepot.get(0).trim().split("\\s+");

            Location l = new Location(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[0]));

            int countVehicles = Integer.parseInt(infoDepot.get(1));

            int capacityVehicles = Integer.parseInt(infoDepot.get(2));

            Depot d = new Depot((i + 1) + "", l, 20, countVehicles, capacityVehicles);

            this.listDepots.add(d);
        }
    }

    //Método para inicializar clases con una información guardada.
    private void initializateData(ArrayList<String> listPassengers, ArrayList<String> infoDepot,
            ArrayList<String> listBusesStop) throws WithoutCapacityException {
        int i = 1;

        for (String s : listPassengers) {

            String[] coordinates = s.split("\\s+");

            Location l = new Location(Double.parseDouble(coordinates[0].trim()),
                    Double.parseDouble(coordinates[1].trim()));

            this.listPassengers.add(new Passenger("" + i++, l));
        }

        i = 1;

        for (String s : listBusesStop) {

            String[] coordinates = s.split("\\s+");

            Location l = new Location(Double.parseDouble(coordinates[0].trim()),
                    Double.parseDouble(coordinates[1].trim()));

            this.listBusesStop.add(new BusStop("" + i++, 20, l));

        }

        generateDepots(infoDepot);
    }

    //Método para obtener la lista de ID de los pasajeros.
    public ArrayList<String> getListIdPassengers() {
        int countPassengers = listPassengers.size();
        ArrayList<String> listIdPassengers = new ArrayList<String>();

        for (int i = 0; i < countPassengers; i++) {
            listIdPassengers.add(listPassengers.get(i).getIdPassenger());
        }

        return listIdPassengers;
    }

    //Método que busca un pasajero dado su ID.
    public Passenger getPassengerByIdPassenger(String idPassenger) throws IllegalArgumentException {
        if (!idPassenger.equals(null)) {
            Passenger passenger = null;
            int i = 0;
            boolean found = false;
            int countPassengers = listPassengers.size();

            while ((i < countPassengers) && (!found)) {
                if (listPassengers.get(i).getIdPassenger().equals(idPassenger)) {
                    passenger = listPassengers.get(i);
                    found = true;
                } else {
                    i++;
                }
            }

            return passenger;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Método que devuelve el tipo de pasajero dado su ID.
    public PassengerType getTypeByIdPassenger(String idPassenger) throws IllegalArgumentException {
        if (!idPassenger.equals(null)) {
            PassengerType typePassenger = null;
            int i = 0;
            boolean found = false;
            int countPassenger = listPassengers.size();

            while ((i < countPassenger) && (!found)) {
                if (listPassengers.get(i).getIdPassenger().equals(idPassenger)) {
                    typePassenger = (listPassengers.get(i)).getTypePassenger();
                    found = true;
                } else {
                    i++;
                }
            }

            return typePassenger;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Método que devuelve una lista con la cantidad de autobuses involucrados en SBRP.
    public ArrayList<Integer> getListCountBuses() {
        ArrayList<Integer> listCountBuses = new ArrayList<Integer>();

        for (int i = 0; i < listDepots.size(); i++) {
            listCountBuses.add(listDepots.get(i).getListBuses().size());
        }

        return listCountBuses;
    }

    //Método para obtener la lista de los ID de los depósitos.
    public ArrayList<String> getListIdDepots() {
        int countDepots = listDepots.size();
        ArrayList<String> listIdDepots = new ArrayList<String>();

        for (int i = 0; i < countDepots; i++) {
            listIdDepots.add(listDepots.get(i).getIdDepot());
        }

        return listIdDepots;
    }

    //Método que dado un ID devuelve su posición.
    public int getPosElement(String idElement) throws IllegalArgumentException {
        if (!idElement.equals(null)) {
            int i = 0;
            boolean found = false;
            int posElement = -1;
            int countBusesStop = listBusesStop.size();
            int countDepots = listDepots.size();

            while ((i < countDepots) && (!found)) {
                if (listDepots.get(i).getIdDepot().equals(idElement)) {
                    posElement = i + countBusesStop;
                    found = true;
                } else {
                    i++;
                }
            }

            i = 0;
            while ((i < countBusesStop) && (!found)) {
                if (listBusesStop.get(i).getIdBusStop().equals(idElement)) {
                    posElement = i;
                    found = true;
                } else {
                    i++;
                }
            }

            return posElement;
        } else {
            throw new IllegalArgumentException();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////
    public int get_cant_bus_stop() {

        return listBusesStop.size();

    }

    public int get_cant_passenger() {

        return listPassengers.size();

    }

    public ArrayList<HashMap<String, String>> passengers_info() {

        ArrayList<HashMap<String, String>> passengers = new ArrayList<>();

        for (Passenger p : this.listPassengers) {
            HashMap<String, String> values = new HashMap<>();
            values.put("id passenger", p.getIdPassenger());
            values.put("coordinates", p.get_coordinates());

            passengers.add(values);
        }

        return passengers;

    }

    //Lista de paradas
    public ArrayList<HashMap<String, String>> bus_stop_info() {

        ArrayList<HashMap<String, String>> bus_stop = new ArrayList<>();

        for (int i = 0; i < this.listBusesStop.size(); i++) {

            HashMap<String, String> values = new HashMap<>();

            values.put("id bus stop", this.listBusesStop.get(i).getIdBusStop());
            values.put("maximum passenger", this.listBusesStop.get(i).getCapacityBusStop()+ "");
            values.put("cant passenger assigned", this.listBusesStop.get(i).getListPassengers().size() + "");
            values.put("coordinates", this.listBusesStop.get(i).get_coordinates());

            bus_stop.add(values);

        }

        return bus_stop;

    }


    public void initializate_data_assigntion(ArrayList<String> listBusesStop) {

        for (int i = 0; i < listBusesStop.size(); i++) {

            String[] line = listBusesStop.get(i).trim().split("\\s+");

            String id_bus_stop = line[0];

            int capacity = Integer.parseInt(line[1]);

            double coordinate_x = Double.parseDouble(line[2]);

            double coordinate_y = Double.parseDouble(line[3]);

            Location l = new Location(coordinate_x, coordinate_y);

            BusStop b = new BusStop(id_bus_stop, capacity, l);

            for (int j = 4; j < line.length; j += 3) {

                String passengerId = line[j];
                Double coordinateX = Double.parseDouble(line[j+1]);
                Double coordinateY = Double.parseDouble(line[j+2]);
                
                Location passengerLocation = new Location(coordinateX, coordinateY);
                
                Passenger p = new Passenger(passengerId, passengerLocation);
                
                this.listPassengers.add(p);
                
                b.insertPassenger(p.getIdPassenger());

            }//for2

            this.listBusesStop.add(b);

        }//for1
        int y = 0;
    }

    //Lista de pasajeros asignados dado una parada
    public HashMap<String, ArrayList<String>> passenger_assigned_by_bus_stop(String bus_stop) throws ItemNoFoundException {

        ArrayList<String> passengers = new ArrayList<String>();
        HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();

        int count = 0;

        int bus_position = this.get_bus_stop_position(bus_stop);

        passengers = this.listBusesStop.get(bus_position).getListPassengers();

        for (String p : passengers) {

           // int passenger = this.getPosElement(passengers.get(i));

            //double distance = Math.round(this.passanger_stop_matrix.getItem(passenger, bus_position) * 100.0) / 100.0;

            //Passenger p = this.listPassengers.get(passenger);

            ArrayList<String> data = new ArrayList<String>();

            //data.add(p.getLocationPassenger().getAddress());
            //data.add((Math.round(distance * 100.0) / 100.00) + "");
            
            data.add(0.00+"");

            values.put(p, data);

        }

        return values;

    }
    
        //Obtener la posicion de una parada dado su identificador
    public int get_bus_stop_position(String id) throws ItemNoFoundException {

        int count = 0;
        boolean found = false;

        do {

            if (this.listBusesStop.get(count).getIdBusStop().equalsIgnoreCase(id)) {
                found = true;
            } else {
                count++;
            }

        } while (count < this.listBusesStop.size() && !found);

        if (!found) {
            throw new ItemNoFoundException("El vehiculo de identificador: " + id + " no existe");
        }

        return count;

    }

    //Lista de paradas alcanzables para un pasajero
    public ArrayList<String> alcanzable_bus_stop_for_passenger(String id) throws ItemNoFoundException {

        ArrayList<String> alcanzable_bus_stop = new ArrayList<>();

        int passenger_position = get_passenger_position(id);

        NumericArray list = this.costMatrix.getRow(passenger_position);

        for (int i = 0; i < list.getLength(); i++) {

            String bus_stop;

            if (list.getItem(i) != Double.POSITIVE_INFINITY) {

                bus_stop = this.listBusesStop.get(i).getIdBusStop();

                alcanzable_bus_stop.add(bus_stop);

            }

        }//for

        return alcanzable_bus_stop;

    }
    
     //Obtener la posicion de un pasajero dado su identificador
    private int get_passenger_position(String id) throws ItemNoFoundException {

        int count = 0;

        boolean found = false;

        do {

            if (this.listPassengers.get(count).getIdPassenger().equalsIgnoreCase(id)) {
                found = true;
            } else {
                count++;
            }

        } while (count < this.listPassengers.size() && !found);

        if (!found) {
            throw new ItemNoFoundException("El pasajero con identificacion: " + id + " no existe");
        }

        return count;
    }
    
    //Lista de pasajeros que pueden alcanzar una parada
    public ArrayList<HashMap<String, String>> passenger_alcanzable_bus_stop(String id) throws ItemNoFoundException {

        ArrayList<HashMap<String, String>> passenger_alcanzable_bus_stop = new ArrayList<>();

        int bus_stop_position = this.get_bus_stop_position(id);

        NumericArray passengers = this.costMatrix.getCol(bus_stop_position);

        for (int i = 0; i < passengers.getLength(); i++) {

            Double distance = passengers.getItem(i);

            Passenger p = listPassengers.get(i);

            if (distance != Double.POSITIVE_INFINITY && !this.passenger_assigned(p.getIdPassenger())) {

                HashMap<String, String> values = new HashMap<>();

                values.put("id passenger", p.getIdPassenger());
                values.put("distance walk", ""+(Math.round(distance * 100.0) / 100.0));
                values.put("coordinates", p.get_coordinates());

                passenger_alcanzable_bus_stop.add(values);

            }

        }

        return passenger_alcanzable_bus_stop;

    }
    
    //Comprobar que un pasajero este asignado
    private boolean passenger_assigned(String id) throws ItemNoFoundException {

        int position = this.get_passenger_position(id);

        boolean found = false;

        int count = 0;

        if (position > -1) {

            do {

                found = listBusesStop.get(count++).getListPassengers().contains(id);

            } while (!found && count < listBusesStop.size());

        }

        return found;

    }
    
    //Lista de paradas con pasajeros asignados
    public ArrayList<HashMap<String, String>> bus_stop_with_passengers_assigned() {

        ArrayList<HashMap<String, String>> alcanzable_bus_stop = new ArrayList<>();

        for (int i = 0; i < this.listBusesStop.size(); i++) {

            if (!this.listBusesStop.get(i).getListPassengers().isEmpty()) {

                HashMap<String, String> bus_stop = new HashMap<>();

                bus_stop.put("id bus stop", this.listBusesStop.get(i).getIdBusStop());
                bus_stop.put("maximum passenger", this.listBusesStop.get(i).getCapacityBusStop() + "");
                bus_stop.put("cant passenger assigned", this.listBusesStop.get(i).getListPassengers().size() + "");
                bus_stop.put("coordinates", this.listBusesStop.get(i).get_coordinates());

                alcanzable_bus_stop.add(bus_stop);

            }

        }

        return alcanzable_bus_stop;

    }
    
    public ArrayList<String> export_assignation_info() {

        ArrayList<String> values = new ArrayList<>();

        values.add(this.distance_used.toUpperCase());

        values.add(this.get_cant_bus_stop_with_passenger_assigned() + " " + this.listDepots.get(0).getListBuses().size() + " " + this.listDepots.get(0).get_vehicle_capacity() + " " + this.maximumWalkDistance);

        values.add(listDepots.get(0).getLocationDepot().getAxisX() + " " + listDepots.get(0).getLocationDepot().getAxisY());

        for (BusStop b : listBusesStop) {

            if (!b.getListPassengers().isEmpty()) {

                String info = b.getIdBusStop() + " " + b.getCapacityBusStop() + " " + b.getLocationBusStop().getAxisX() + " " + b.getLocationBusStop().getAxisY() + " ";

                for (String p : b.getListPassengers()) {
                    Passenger passenger = this.getPassengerByIdPassenger(p);
                    info = info + passenger.getIdPassenger() + " " + passenger.getLocationPassenger().getAxisX() + " " + passenger.getLocationPassenger().getAxisY() + " ";
                }

                values.add(info);

            }

        }//Recorrer las paradas

        return values;

    }
    
    public int get_cant_bus_stop_with_passenger_assigned() {

        int count = 0;

        for (BusStop b : listBusesStop) {
            if (!b.getListPassengers().isEmpty()) {
                count++;
            }
        }

        return count;

    }
    

    //LLenar matriz de distancias ( pasajero vs parada )
    public void create_cost_matrix_passenger_vs_bus_stop(String distance_type) {

        this.costMatrix = new NumericMatrix(listPassengers.size(), listBusesStop.size());

        IDistance calculate_distance = FactoryDistance.createDistance(distance_type);

        distance_used = distance_type;

        int i = 0;

        for (Passenger p : listPassengers) {

            int j = 0;

            for (BusStop b : listBusesStop) {

                double walking_distance = calculate_distance.calculateDistance(p.getLocationPassenger().getAxisX(), p.getLocationPassenger().getAxisY(), b.getLocationBusStop().getAxisX(), b.getLocationBusStop().getAxisY());

                if (walking_distance <= maximumWalkDistance) {
                    costMatrix.setItem(i, j, walking_distance);
                } else {
                    costMatrix.setItem(i, j, Double.POSITIVE_INFINITY);
                }

                j++;

            }

            i++;

        }

    }
    
    //Asignacion de los pasajeros a las paradas correspondientes automaticamente
    public void automatic_assignment(HeuristicType heuristicType, int rlc) throws ItemNoFoundException {

        NumericMatrix matrix = new NumericMatrix(costMatrix);
        
        if(heuristicType.equals(HeuristicType.NearestNeighborWithRLC)){
            RowCol shortest_cell = matrix.indexLowerValue();

            BusStop b = listBusesStop.get(shortest_cell.getCol());

            Passenger p = listPassengers.get(shortest_cell.getRow());

            if (b.insertPassenger(p.getIdPassenger())) {
                matrix.fillValue(shortest_cell.getRow(), 0, shortest_cell.getRow(), matrix.getColCount() - 1, Double.POSITIVE_INFINITY);
            }

            if (!b.hasCapacity()) {
                matrix.fillValue(0, shortest_cell.getCol(), matrix.getRowCount() - 1, shortest_cell.getCol(), Double.POSITIVE_INFINITY);
            }
                
            ArrayList<RowCol> shortestCellsInMatrix = new ArrayList<>();
            RowCol randomShortestCell;
            
            do {
                for(int i = 0; i < rlc; i++){
                    shortest_cell = matrix.indexLowerValue();
                    
                    shortestCellsInMatrix.add(shortest_cell);
                }
                randomShortestCell = getRandomRowColShortest(shortestCellsInMatrix);

                if (b.insertPassenger(p.getIdPassenger())) {
                   matrix.fillValue(shortest_cell.getRow(), 0, shortest_cell.getRow(), matrix.getColCount() - 1, Double.POSITIVE_INFINITY);
                }   

                if (!b.hasCapacity()) {
                    matrix.fillValue(0, shortest_cell.getCol(), matrix.getRowCount() - 1, shortest_cell.getCol(), Double.POSITIVE_INFINITY);
                    randomShortestCell = getRandomRowColShortest(shortestCellsInMatrix);

                    if (b.insertPassenger(p.getIdPassenger())) {
                       matrix.fillValue(shortest_cell.getRow(), 0, shortest_cell.getRow(), matrix.getColCount() - 1, Double.POSITIVE_INFINITY);
                    }
                }

            } while (!matrix.fullMatrix(Double.POSITIVE_INFINITY));

        }
        else{
            RowCol randomCell;
            ArrayList<String> listBusesStopAlcanzables = new ArrayList<>();
            String busStop;
            do{
                randomCell = getRandomRowCol(); 
                BusStop b = listBusesStop.get(randomCell.getCol());
                Passenger p = listPassengers.get(randomCell.getRow());
                listBusesStopAlcanzables = alcanzable_bus_stop_for_passenger(p.getIdPassenger());
                
                busStop = getRandomBusStop(listBusesStopAlcanzables);
                
                if (b.insertPassenger(p.getIdPassenger())) {
                    matrix.fillValue(randomCell.getRow(), 0, randomCell.getRow(), matrix.getColCount() - 1, Double.POSITIVE_INFINITY);
                }
                
                if(!b.hasCapacity()){
                    matrix.fillValue(0, randomCell.getCol(), matrix.getRowCount() - 1, randomCell.getCol(), Double.POSITIVE_INFINITY);
                    randomCell = getRandomRowCol();

                    if (b.insertPassenger(p.getIdPassenger())) {
                       matrix.fillValue(randomCell.getRow(), 0, randomCell.getRow(), matrix.getColCount() - 1, Double.POSITIVE_INFINITY);
                    }
                }
            } while(!matrix.fullMatrix(Double.POSITIVE_INFINITY));
        }
        
    }
    
    private String getRandomBusStop(ArrayList<String> listBusesStop){
	String busStop;		
	Random random = new Random();
	int index = -1;
		
	index = random.nextInt(listBusesStop.size());
	busStop = listBusesStop.get(index);
		
	return busStop;
    }
    
    private RowCol getRandomRowColShortest(ArrayList<RowCol> shortestCellsInMatrix){
	RowCol cell = new RowCol();		
	Random random = new Random();
	int index = -1;
		
	index = random.nextInt(shortestCellsInMatrix.size());
	cell = shortestCellsInMatrix.get(index);
		
	return cell;
    }
    
    private RowCol getRandomRowCol(){		
	Random random = new Random();
	int row = -1, col = -1;
	
        row = random.nextInt(listPassengers.size());
        col = random.nextInt(listBusesStop.size());
	RowCol cell = new RowCol(row, col);
		
	return cell;
    }
    
    //**Mutiples pasajeros** Asignacion de los pasajeros a las paradas correspondiente manualmente
    public void manual_assigment_multiple(ArrayList<String> passengers, String id_bus_stop, String heuristicType) throws WithoutCapacityException, DistanceNotAccessibleException, ItemNoFoundException {

        for (int i = 0; i < passengers.size(); i++) {
            this.manual_assigment_single(passengers.get(i), id_bus_stop, heuristicType);
        }

    }

    //Asignacion de los pasajeros a las paradas correspondiente manualmente
    public void manual_assigment_single(String id_passenger, String id_bus_stop, String heuristicType) throws WithoutCapacityException, DistanceNotAccessibleException, ItemNoFoundException {

        int bus_stop_position = get_bus_stop_position(id_bus_stop);

        BusStop b = listBusesStop.get(bus_stop_position);

        if (!b.hasCapacity()) {
            throw new WithoutCapacityException("La parada " + b.getIdBusStop() + " ha alcanzado su maximo de capacidad (" + b.getCapacityBusStop() + "-" + b.getListPassengers().size() + ")");
        }

        int passenger_position = this.get_passenger_position(id_passenger);

        if (!this.passenger_assigned(id_passenger)) {

            double distance = this.costMatrix.getItem(passenger_position, bus_stop_position);

            Passenger p = listPassengers.get(passenger_position);

            if (distance > this.maximumWalkDistance) {
                throw new DistanceNotAccessibleException("La parada " + b.getIdBusStop() + " no es alcanzable para el pasajero " + p.getIdPassenger());
            }

            b.insertPassenger(p.getIdPassenger());

        }//Pasajero no insertado

    }
    
    //Método encargado de llenar la matriz de costo.
    public NumericMatrix fillCostMatrix(ArrayList<BusStop> listBusesStop, ArrayList<Depot> listDepots, String typeDistance) 
                                        throws IllegalArgumentException, SecurityException, ClassNotFoundException, 
                                        InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int size = listBusesStop.size() + listDepots.size(); 
	NumericMatrix costMatrix = new NumericMatrix(size, size);
	IDistance distance = FactoryDistance.createDistance(typeDistance);

	int row = -1;
	int col = -1;
	int lastBusStop = 0;
	double costInDistance = 0.0;

	for(int i = 0; i < size; i++)
	{
            if(i < listBusesStop.size())
		row = getPosElement(listBusesStop.get(i).getIdBusStop());
            else
		row = getPosElement(listDepots.get(i - listBusesStop.size()).getIdDepot());

            for(int j = (i + 1); j <= size; j++)
            {
		if(j < listBusesStop.size())
		{
                    col = getPosElement(listBusesStop.get(j).getIdBusStop());
                    costInDistance = distance.calculateDistance(listBusesStop.get(i).getLocationBusStop().getAxisX(),
                                                                listBusesStop.get(i).getLocationBusStop().getAxisY(), 
                                                                listBusesStop.get(j).getLocationBusStop().getAxisX(), 
                                                                listBusesStop.get(j).getLocationBusStop().getAxisY());
		}	//aquí calcular la distancia por la fórmula de Euclidean.
		else
		{
                    col = getPosElement(listDepots.get(lastBusStop).getIdDepot());

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
    
    public int cant_bus(){
    
        return this.listDepots.get(0).listBuses.size();
        
    }
    
    public int bus_capacity(){
    
        return this.listDepots.get(0).get_vehicle_capacity();
        
    }
}
