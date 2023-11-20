/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.factory.interfaces;

import cujae.inf.citi.om.distance.IDistance;

/**
 *
 * @author nanda
 */

//Interfaz que define como crear un objeto Distance.

public interface IFactoryDistance {
	public IDistance createDistance(DistanceType typeDistance);

}
