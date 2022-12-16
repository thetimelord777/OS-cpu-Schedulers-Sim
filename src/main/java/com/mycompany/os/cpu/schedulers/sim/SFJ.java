/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.os.cpu.schedulers.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author 10
 */
public class SFJ {
    List<Process> processes;
    List<Process> activePs;
    List<Process> finishedPs;
    List<String> schedule;
    List<Integer> scheduleTimes;
    int processesNum;
    int contextSwitch;
    int currentTime = 0;
    String temp;
    
    public SFJ(){
        this.setUpSchedule();
    };
    
    public void setUpSchedule(){
        processes = new ArrayList<Process>();
        activePs = new ArrayList<Process>();
        finishedPs = new ArrayList<Process>();
        schedule = new ArrayList<String>();
        scheduleTimes = new ArrayList<Integer>();
        Scanner scan = new Scanner(System.in);
        int tempI;
        
        System.out.println("Please Enter Number Of Processes: ");
        tempI = scan.nextInt();
        processesNum = tempI;
        
        System.out.println("Please Enter Context Switching Overhead: ");
        tempI = scan.nextInt();
        contextSwitch = tempI;
        
        //Teting
        /*processes.add(new Process("P1",0,7));
        processes.add(new Process("P2",2,4));
        processes.add(new Process("P3",4,1));
        processes.add(new Process("P4",5,4));*/
        for(int i=0;i<processesNum;i++){
            Process p = new Process();
            p.enterNormalProcess();
            processes.add(p);
        }
    }
    
    //Each 1 time unit we check on the avail Jobs to see if a shortest one is available.
    public void run(){
        temp = "-1";
        while(!(activePs.isEmpty() && processes.isEmpty())){
            
            for(int i=0;i<processes.size();i++){
                if(processes.get(i).arrivalTime<=currentTime){
                    activePs.add(processes.get(i));
                    processes.remove(i);
                    i--;
                }
            }
            
            if(activePs.isEmpty()){currentTime+= 1;continue;}
            
            Collections.sort(activePs, new SortByRemainingTime());
            
            //If the previous Process is not the same as the current shortest one, then we will
            //push the new one in the schedule and add context switch overhead.
            if(!temp.equals(activePs.get(0).name)){
                //to not add context switching on first job
                if(!temp.equals("-1") && contextSwitch !=0){
                    scheduleTimes.add(currentTime);
                    currentTime+=contextSwitch;
                    schedule.add("--");
                }
                schedule.add(activePs.get(0).name);
                scheduleTimes.add(currentTime);
                temp = activePs.get(0).name;
            }
            
            currentTime+= 1;
            
            activePs.get(0).remainingTime -= 1;
            if(activePs.get(0).remainingTime <= 0){
                activePs.get(0).exitTime = currentTime;
                finishedPs.add(activePs.get(0));
                activePs.remove(0);
            }
            
        }
        scheduleTimes.add(currentTime);
    };
    
    public void printResult(){
        int totalTurnAround = 0;
        int totalWaiting = 0;
        System.out.print("  ");
        for(int i = 0;i<schedule.size();i++){
            System.out.print(schedule.get(i) + "  ");
        }
        System.out.println();
        for(int i = 0;i<scheduleTimes.size();i++){
            System.out.print(String.format("%02d",scheduleTimes.get(i)) + "  ");
        }
        System.out.println();
        System.out.println("Name : Turn Around : Waiting");
        for(int i = 0;i<finishedPs.size();i++){
            finishedPs.get(i).calcTimes();
            totalTurnAround+=finishedPs.get(i).turnAroundTime;
            totalWaiting+=finishedPs.get(i).waitingTime;
            
            System.out.println(finishedPs.get(i).name + " " + finishedPs.get(i).turnAroundTime + " " + finishedPs.get(i).waitingTime);
        }
        
        System.out.println("Average Turn Around Time = " + ((double)totalTurnAround/finishedPs.size()) +
                " Average Waiting Time = " + ((double)totalWaiting/finishedPs.size()));
    };
    
}
