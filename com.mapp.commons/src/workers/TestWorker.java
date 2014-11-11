/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workers;

import com.nct.framework.queue.QueueCommand;
import com.nct.framework.queue.QueueManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author liempt
 */
public class TestWorker {
    private static final  QueueManager qm = QueueManager.getInstance("liem");
    static{
        qm.init(4, 1000000);
        qm.process();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        for(int i = 0; i <1000 ; i++){
            qm.put(new Test1(i, "name" + i));
            qm.put(new Test2(i, "name" + i));
        }
        while(qm.hasJobRunning()){
            System.out.println("waiting");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.exit(0);
    }
    
    public static class Test1 implements QueueCommand{
        long id;
        String name;
        public Test1(long id, String name){
            this.id = id;
            this.name = name;
        }
        @Override
        public void execute() {
            System.out.println("OUT id=" + this.id + " name: " + this.name);
        }
        
    }
    public static class Test2 implements QueueCommand{
        long id;
        String name;
        public Test2(long id, String name){
            this.id = id;
            this.name = name;
        }
        @Override
        public void execute() {
            System.out.println("DATABASE id=" + this.id + " name: " + this.name);
        }
        
    }
}
