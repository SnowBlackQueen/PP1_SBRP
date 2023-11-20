/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cujae.inf.citi.om.fileAccess;

import cujae.inf.citi.om.data.Problem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OMAR
 */
public class Access {

    public static HashMap<String, Object> load_file(String path){
        
        HashMap<String, Object> map = null;
        
        try {
            RandomAccessFile access = new RandomAccessFile(path, "r");
            
            map = new HashMap<String, Object>();
            
            ArrayList<String> passenger = new ArrayList();
            ArrayList<String> bus_stop = new ArrayList();
            ArrayList<String> depot = new ArrayList();
                   
            String[] info = access.readLine().trim().split("\\s+");
            
            map.put("Maximum_Walking_Distance", info[4]);
            
            depot.add(access.readLine());
            depot.add(info[2]);
            depot.add(info[3]);
            
            for(int i = 0; i < Integer.parseInt(info[0]); i++)
                bus_stop.add(access.readLine().trim());
            
            for(int i = 0; i < Integer.parseInt(info[1]); i++)
                passenger.add(access.readLine().trim() );
            
            map.put("Depot", depot);
            map.put("Bus_Stop", bus_stop);
            map.put("Passenger", passenger);
            
            access.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    
    }
    
    public static HashMap<String, Object> loadFileAssignation(String path) {

        HashMap<String, Object> map = null;

        try {
            RandomAccessFile access = new RandomAccessFile(path, "r");

            map = new HashMap<String, Object>();

            ArrayList<String> passenger = new ArrayList();
            ArrayList<String> bus_stop = new ArrayList();
            ArrayList<String> depot = new ArrayList();

            map.put("Distance_used", access.readLine().trim());

            String[] info = access.readLine().trim().split("\\s+");

            map.put("Maximum_Walking_Distance", info[3]);

            depot.add(access.readLine());
            depot.add(info[1]);
            depot.add(info[2]);

            for (int i = 0; i < Integer.parseInt(info[0]); i++) {
                String BstopInfo = access.readLine().trim();
                if (!BstopInfo.isEmpty() && BstopInfo != null) {
                    bus_stop.add(BstopInfo);
                }
            }

            map.put("Depot", depot);
            map.put("Bus_Stop", bus_stop);

            access.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;

    }

}
