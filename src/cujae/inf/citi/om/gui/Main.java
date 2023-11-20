/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cujae.inf.citi.om.gui;
import cujae.inf.citi.om.data.Problem;
import cujae.inf.citi.om.data.ProblemType;
import cujae.inf.citi.om.factory.interfaces.DistanceType;
import cujae.inf.citi.om.factory.interfaces.HeuristicType;
import cujae.inf.citi.om.fileAccess.Access;
import cujae.inf.citi.om.generator.controller.Controller;
import cujae.inf.citi.om.generator.heuristic.NearestNeighborWithRLC;
import cujae.inf.citi.om.generator.solution.Solution;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author nanda
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            Controller c = Controller.getController();
            String path = "C:\\Ananda\\New folder\\Ficheros\\inst-2595-Assign_Save.xpress_save";
            //c.initializateDataAssignation("C:\\Ananda\\New folder\\Ficheros\\inst-247-Assign_Save.xpress_save"); YA PINCHA!!!!
            c.initializateDataAssignation(path);

            Problem p = c.getProblem();
            HashMap<String, Object> values = Access.loadFileAssignation(path);
            String typeDistance = String.valueOf(values.get("Distance_used"));
            ArrayList<String> idPassengers = p.getListIdPassengers();
            Solution result = new Solution();
            HeuristicType heuristicType = HeuristicType.RandomMethod;
            int rlc = 2;
            
            
            if(c.loadProblemWithDataAssignation(p, ProblemType.SBRP, typeDistance)){
                if(heuristicType.equals(HeuristicType.NearestNeighborWithRLC)){
                    NearestNeighborWithRLC n = new NearestNeighborWithRLC();
                    n.setSizeRLC(rlc, p);
                    c.executeHeuristic(3, heuristicType, p);
                    result = c.getBestSolution();
                }
                else{
                    c.executeHeuristic(3, heuristicType, p);
                    result = c.getBestSolution();
                }
                
                System.out.println("-------------SOLUCIÓN----------------------");
                
                for(int i = 0; i < result.getListRoutes().size(); i++){
                    System.out.print("Ruta " + (i+1) + ": ");
                    for(int j = 0; j < result.getListRoutes().get(i).getListIdBusesStop().size(); j++){
                        System.out.print(result.getListRoutes().get(i).getListIdBusesStop().get(j));
                        if(j != result.getListRoutes().get(i).getListIdBusesStop().size() - 1)
                            System.out.print(" - ");
                    }
                    System.out.println();
                }
                
                System.out.println();
                System.out.println("Costo: " + result.getCostSolution());
                System.out.println("Distancia utilizada: " + typeDistance);
                System.out.println("Heurística empleada: " + heuristicType);
                
                System.out.println("-------------------------------------------");
            }
        }
        catch(Exception e){
                e.printStackTrace(); 
                }
                
    }
    
}
