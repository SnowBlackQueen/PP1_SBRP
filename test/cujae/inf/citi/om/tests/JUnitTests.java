package cujae.inf.citi.om.tests;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import cujae.inf.citi.om.data.Problem;
import cujae.inf.citi.om.data.ProblemType;
import cujae.inf.citi.om.exceptions.DistanceNotAccessibleException;
import cujae.inf.citi.om.exceptions.ItemNoFoundException;
import cujae.inf.citi.om.exceptions.WithoutCapacityException;
import cujae.inf.citi.om.factory.interfaces.HeuristicType;
import cujae.inf.citi.om.fileAccess.Access;
import cujae.inf.citi.om.generator.controller.Controller;
import cujae.inf.citi.om.generator.solution.Solution;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author nanda
 */
public class JUnitTests {
    private Controller c;
    private String path;
    
    public JUnitTests() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    //Inicializar datos.
    public void setUp() throws Exception {
        this.c = Controller.getController();
        this.path = "C:\\Ananda\\New folder\\Ficheros\\inst-2595-Assign_Save.xpress_save";
        /*c.initializateDataAssignation(path);
        
        Problem p = c.getProblem();
        HashMap<String, Object> values = Access.loadFileAssignation(path);
        String typeDistance = String.valueOf(values.get("Distance_used"));*/
        
    }
    
    @AfterEach
    public void tearDown() {
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    //_________________________PRUEBAS DE CAJA NEGRA____________________________________
    //Particiones de equivalencia para el método loadAssignationData.
    @Test
    public final void testLoadAssignationDataSuccessfull() {
        try{
            this.c.initializateDataAssignation(path);
            HashMap<String, Object> values = Access.loadFileAssignation(path);
            assertNotNull(path);
            assertNotNull(values);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.print("asd");
    }
    
    @Test
    public final void testLoadAssignationDataFail(){
        try{
            this.c.initializateDataAssignation(null);
            fail();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public final void testLoadAssignationDataIncomplete(){
        try{
            this.path = "C:\\Ananda\\New folder\\Ficheros\\inst-116-Assign_Save.xpress_save";
            this.c.initializateDataAssignation(path);
            HashMap<String, Object> values = Access.loadFileAssignation(path);
            assertNotNull(path);
            assertNull(values);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Particiones de equivalencia para el método executeHeuristic.
    @Test
    public final void testExecuteHeuristicSuccessfull() throws DistanceNotAccessibleException, IllegalArgumentException, 
                                                               SecurityException, ClassNotFoundException, InstantiationException,
                                                               IllegalAccessException, InvocationTargetException, 
                                                               NoSuchMethodException, WithoutCapacityException, ItemNoFoundException{
        c.initializateDataAssignation(path);
        Problem p = c.getProblem();
        HashMap<String, Object> values = Access.loadFileAssignation(path);
        String typeDistance = String.valueOf(values.get("Distance_used"));
        Solution result = new Solution();
            
        if(c.loadProblem(p, ProblemType.SBRP, typeDistance)){
            c.executeHeuristic(3, HeuristicType.RandomMethod, p);
            result = c.getBestSolution();
        }
        assertNotNull(result);
    }
    
    @Test
    public final void testExecuteHeuristicFail() throws DistanceNotAccessibleException, IllegalArgumentException, SecurityException,
                                                        ClassNotFoundException, InstantiationException, IllegalAccessException, 
                                                        InvocationTargetException, NoSuchMethodException, WithoutCapacityException, ItemNoFoundException{
        c.initializateDataAssignation(path);
        Problem p = new Problem();
        HashMap<String, Object> values = Access.loadFileAssignation(path);
        String typeDistance = String.valueOf(values.get("Distance_used"));
        Solution result = new Solution();
            
        if(c.loadProblem(p, ProblemType.SBRP, typeDistance)){
            c.executeHeuristic(0, null, p);
            result = c.getBestSolution();
        }
        assertNull(result);
        fail();
    }
    
    @Test
    public final void testExecuteHeuristicIncomplete() throws DistanceNotAccessibleException, IllegalArgumentException, 
                                                              SecurityException, ClassNotFoundException, InstantiationException,
                                                              IllegalAccessException, InvocationTargetException, NoSuchMethodException, WithoutCapacityException, ItemNoFoundException{
        c.initializateDataAssignation(path);
        Problem p = c.getProblem();
        HashMap<String, Object> values = Access.loadFileAssignation(path);
        String typeDistance = String.valueOf(values.get("Distance_used"));
        Solution result = new Solution();
            
        if(c.loadProblem(p, ProblemType.SBRP, typeDistance)){
            c.executeHeuristic(2, null, p);
            result = c.getBestSolution();
        }
        assertNull(result);
        fail();
    }
    //__________________________________________________________________________________
    
    
    //_________________________PRUEBAS DE CAJA BLANCA___________________________________
    //Técnica de camino básico para el método executeHeuristic.
    @Test
    public final void testExecuteHeuristicTC1(){
        try{
            int countExecution = 0;
            HeuristicType heuristicType = HeuristicType.RandomMethod;
            c.initializateDataAssignation(this.path);
            Problem p = c.getProblem();
            Solution result = new Solution();
            c.setCalculateTime(false);
            Solution test = new Solution();
            
            c.executeHeuristic(countExecution, heuristicType, p);
            result = c.getBestSolution();
            
            assertEquals(test, result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public final void testExecuteHeuristicTC2(){
        try{
            int countExecution = 0;
            HeuristicType heuristicType = HeuristicType.RandomMethod;
            c.initializateDataAssignation(this.path);
            Problem p = c.getProblem();
            Solution result = new Solution();
            c.setCalculateTime(true);
            Solution test = new Solution();
            
            c.executeHeuristic(countExecution, heuristicType, p);
            result = c.getBestSolution();
            
            assertEquals(test, result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public final void testExecuteHeuristicTC3(){
        try{
            int countExecution = 1;
            HeuristicType heuristicType = HeuristicType.RandomMethod;
            c.initializateDataAssignation(this.path);
            Problem p = c.getProblem();
            Solution result = new Solution();
            c.setCalculateTime(false);
            Solution test = new Solution();
            
            c.executeHeuristic(countExecution, heuristicType, p);
            result = c.getBestSolution();
            test = c.getBestSolution();
            
            assertEquals(test, result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public final void testExecuteHeuristicTC4(){
        try{
            int countExecution = 1;
            HeuristicType heuristicType = HeuristicType.RandomMethod;
            c.initializateDataAssignation(this.path);
            Problem p = c.getProblem();
            Solution result = new Solution();
            c.setCalculateTime(true);
            Solution test = new Solution();
            
            c.executeHeuristic(countExecution, heuristicType, p);
            result = c.getBestSolution();
            test = c.getBestSolution();
            
            assertEquals(test, result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public final void testExecuteHeuristicTC5(){
        try{
            int countExecution = 3;
            HeuristicType heuristicType = HeuristicType.RandomMethod;
            c.initializateDataAssignation(this.path);
            Problem p = c.getProblem();
            Solution result = new Solution();
            c.setCalculateTime(false);
            Solution test = new Solution();
            
            c.executeHeuristic(countExecution, heuristicType, p);
            result = c.getBestSolution();
            test = c.getBestSolution();
            
            assertEquals(test, result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public final void testExecuteHeuristicTC6(){
        try{
            int countExecution = 3;
            HeuristicType heuristicType = HeuristicType.RandomMethod;
            c.initializateDataAssignation(this.path);
            Problem p = c.getProblem();
            Solution result = new Solution();
            c.setCalculateTime(true);
            Solution test = new Solution();
            
            c.executeHeuristic(countExecution, heuristicType, p);
            result = c.getBestSolution();
            test = c.getBestSolution();
            
            assertEquals(test, result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Técnica de camino básico para el método getSolutionInicial.
    
}
