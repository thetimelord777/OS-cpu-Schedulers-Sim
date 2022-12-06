/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.os.cpu.schedulers.sim;

import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author 10
 */
public class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int exitTime;
    int turnAroundTime;
    int waitingTime;
    int remainingTime;
    int piority = -1;
    
    public Process(){
        this.enterNormalProcess();
    }
    
    public Process(String n,int a,int b){
        name =n;
        arrivalTime = a;
        burstTime = b;
    };
    
    public Process(String n,int a,int b,int p){
        name =n;
        arrivalTime = a;
        burstTime = b;
        piority = p;
    };
    
    public void enterNormalProcess(){
        Scanner scan = new Scanner(System.in);
        String tempS;
        int tempI;
        System.out.println("Please Enter Process Name: ");
        tempS = scan.nextLine();
        name = tempS;
        
        System.out.println("Please Enter Process Arrival Time: ");
        tempI = scan.nextInt();
        arrivalTime = tempI;
        
        System.out.println("Please Enter Process Burst Time: ");
        tempI = scan.nextInt();
        burstTime = tempI;
        remainingTime = burstTime;
    };
    
    public void enterPiorityProcess(){
        this.enterNormalProcess();
        
        Scanner scan = new Scanner(System.in);
        int tempI;
        System.out.println("Please Enter Process Piority: ");
        tempI = scan.nextInt();
        piority = tempI;
    };
    
    public void calcTimes(){
        turnAroundTime = exitTime - arrivalTime;
        waitingTime = turnAroundTime - burstTime;
    }
}

class SortByRemainingTime implements Comparator<Process>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Process a, Process b)
    {
        return a.remainingTime - b.remainingTime;
    }
}