/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.factory.interfaces;

import cujae.inf.citi.om.generator.heuristic.Heuristic;

/**
 *
 * @author nanda
 */

//Interfaz que define como crear una objeto Heuristic.

public interface IFactoryHeuristic {
	Heuristic createHeuristic(HeuristicType heuristicType);
}
