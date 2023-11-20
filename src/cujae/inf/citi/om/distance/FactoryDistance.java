/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.distance;

/**
 *
 * @author OMAR
 */
public class FactoryDistance {
    
    public static IDistance createDistance(String distance_type){
    
       IDistance d = null;
       
       if(distance_type.equalsIgnoreCase("Euclidean"))
           d = new Euclidean();
       
       else if(distance_type.equalsIgnoreCase("Manhattan"))
           d = new Manhattan();
           
       else if(distance_type.equalsIgnoreCase("Haversine"))
           d = new Haversine();
           
       else if(distance_type.equalsIgnoreCase("Chebyshev"))
           d = new Chebyshev();
       
       return d;
    
    }
    
}
