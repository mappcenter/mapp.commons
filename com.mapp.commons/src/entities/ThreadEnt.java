/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 *
 * @author liempt
 */
public class ThreadEnt extends Thread{
    
    public ThreadEnt(String name)
    {
        super(name);
    }
    
    @Override
    public void run()
    {
        System.out.println("Thread Name:"+getName());
        for(int i=0;i<5;i++)
        {
            try {
                sleep(100); //--Sleep 100ms
            } catch (InterruptedException e) {
                //--Ko làm gì cả nếu có ngoại lệ
            }
            System.out.println("i="+i);
        }
    }
    
}
