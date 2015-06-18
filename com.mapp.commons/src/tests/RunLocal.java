package tests;

import commonUtils.CommonUtils;
import databaseUtils.DatabaseServiceUtils;
import domains.domainUtils;
import entities.TestEnt;
import java.util.ArrayList;
import java.util.List;
import serviceUtils.FileCacheUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liempt
 */
public class RunLocal {
    private static final int M = 10;
    private static final int V = 1000;
    private static final int K = 100;
        
    public static void main(String[] args) {
        boolean setRes = FileCacheUtils.setCacheFile("home.1.IOS", "tao lao bi dao");
        System.out.println(setRes);
        String setResult = FileCacheUtils.getMemcache("home.1.IOS");
        System.out.println(setResult);
        System.exit(0);
        
        System.out.println("Hello!");
        String lstDomain = "";
        List<String> listDomains = CommonUtils.ParseStringToListString(lstDomain);
        if(listDomains!=null&&listDomains.size()>0){
            for(String tmpDomain : listDomains){
                System.out.println(tmpDomain+ " - >"+ domainUtils.isFree(tmpDomain));
            }
        }
        System.exit(0);
        try{
            for (int i = 0; i < M; i++){
                int numSave = 1;
                List<TestEnt> listTestEnt = new ArrayList<TestEnt>();
                for (int j = 0; j < V; j++){
                    TestEnt tmpTestEnt = new TestEnt();
                    tmpTestEnt.Num01 = i;
                    tmpTestEnt.Num02 = j;
                    tmpTestEnt.NumTotal = i+j;
                    listTestEnt.add(tmpTestEnt);
                    numSave++;
                    
                    if(numSave==K){
                        long id = DatabaseServiceUtils.CreateByNameEn(listTestEnt);
                        numSave = 1;
                        listTestEnt = new ArrayList<TestEnt>();
                        System.out.println("saveDB:" + i + "::" + j + "->" + id);
                    }
                }
                
            }

        } catch (Exception e){
                System.out.println("Error while saving topic distribution file for this model: " + e.getMessage());
                e.printStackTrace();
        }
        
        
        System.exit(0);
        
    }
}
