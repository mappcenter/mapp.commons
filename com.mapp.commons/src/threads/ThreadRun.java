/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import entities.ThreadEnt;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author liempt
 */
public class ThreadRun {
    
    public static void main(String[] args) {
        System.out.println("Run Thread!");
        
        ThreadEnt testThread =new ThreadEnt("TestNe");        
        testThread.start();
        
        System.out.println("End Thread!");
        for(int i=0;i<100;i++){
            System.out.println("is RUN:" + testThread.isAlive());
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadRun.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
