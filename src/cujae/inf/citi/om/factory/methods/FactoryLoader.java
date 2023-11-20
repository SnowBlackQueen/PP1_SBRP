/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.factory.methods;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author nanda
 */

//Clase que construye una instancia de un objeto.

public class FactoryLoader {

	public static Object getInstance(String className) throws ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		@SuppressWarnings("rawtypes")
		Class c = null;
		
		try{
			c = Class.forName(className); 
		} catch (ClassNotFoundException e){
			System.out.println("El nombre de la clase no existe en el classpath");
			e.printStackTrace();
		}
		
		Object instance = null;
		
		try {
			instance = c.newInstance();
		} catch (InstantiationException e) {
			System.out.println("Ha ocurrido un error al invocar el constructor de la clase");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Esta clase no tiene constructores disponibles");
			e.printStackTrace();
		}
		
		return instance;
	}
}
