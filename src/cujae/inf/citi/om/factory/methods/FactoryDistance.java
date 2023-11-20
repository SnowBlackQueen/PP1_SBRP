/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.factory.methods;

import java.lang.reflect.InvocationTargetException;

import cujae.inf.citi.om.factory.interfaces.DistanceType;
import cujae.inf.citi.om.factory.interfaces.IFactoryDistance;
import cujae.inf.citi.om.distance.IDistance;

/**
 *
 * @author nanda
 */

//Clase que implementa el Patrón Factory para la carga dinámica de una determinada distancia.

public class FactoryDistance implements IFactoryDistance {
	
	public IDistance createDistance(DistanceType typeDistance) {
		
		String className = "cujae.inf.citi.om.distance." + typeDistance;
		IDistance distance = null;
		
		try {
			distance = (IDistance) FactoryLoader.getInstance(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return distance;
	}
}
