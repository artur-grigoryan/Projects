import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.lang.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import java.math.*;
import java.text.*;

class Duder {
    // the graph of the world, which is initialized in the function setUp
	public int numNodes;
    node world[] = new node[100];
	
    // your connection to the JiaoTong server
    public Socket s;
	public BufferedReader sin;
	public PrintWriter sout;
    
    Random generator = new Random();
    
    // *********************** information variables ***********************
    // you may want to do this in a better way than me
    static Double travelCost, payout, tollCharge;
    int currentNode;
    Double currentUtilitiesforVisitingNodes[] = new Double[4];
    Double aveOnLink;
    int capacity;
    // *********************** end of information variables ***********************
    
    public Duder(String args[]) {
        
		getConnected(args);
		
        play();
        
        try {
            sin.close();
            sout.close();
            s.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
	public static void main(String args[]) {
        new Duder(args);
    }

    // This function selects the next link to traverse from currentNode
    //      0 means the agent selects link[0]
    //      1 means the agent selects link[1]
    // ********* You will need to modify this function
	static boolean firststep = true;
	
	static ArrayList<Integer>[] links = new ArrayList[4];
	static{
		for(int i=0; i<links.length; i++){
			links[i]=new ArrayList<Integer>();
			switch(i){
			case 0: 
				links[0].add(0);
				links[0].add(1);
				break;
			
			case 1:
				links[1].add(0);
				links[1].add(1);
				break;
			
			case 2:
				links[2].add(0);
				links[2].add(1);
				break;
			
			case 3:
				links[3].add(0);
				break;
			}
		}
	}
	static ArrayList<Double>[] linkWeights = new ArrayList[4];
	static ArrayList<Double>[] payouts = new ArrayList[4];

	static{
		for (int i=0; i<linkWeights.length; i++){
			linkWeights[i]=new ArrayList<Double>();
			payouts[i]=new ArrayList<Double>();
			


			linkWeights[i].add(new Double(0));
			linkWeights[i].add(new Double(0));
				
			payouts[i].add(new Double(0));
			payouts[i].add(new Double(0));
				
			
				
			
		}
	}
	static int prevNode;
	static int prevLink;
	
	static double averageLoad1 = 0;
	static double averageLoad2 = 0;
	static double previousLoad = 0;
	
	static double payout1;
	static double payout2;
    public int selectAction() {
    	if(firststep){
    		int nextLink = links[currentNode].get(0);
    		prevNode=currentNode;
    		prevLink=nextLink;
    		firststep = false;
    
    		return nextLink;
    	}
    	previousLoad = aveOnLink*1.0/capacity;
    	linkWeights[prevNode].set(prevLink, (linkWeights[prevNode].get(prevLink)+previousLoad)/2);
    	payouts[prevNode].set(prevLink, new Double(payout-tollCharge-travelCost));
    	System.out.println(prevNode+" "+prevLink+" "+linkWeights[prevNode]+" "+payouts[prevNode]);
    	if(currentNode==3){
    		prevNode=3;
    		prevLink=0;
    		return 0;
    	}
    	else{
    		averageLoad1=linkWeights[currentNode].get(0);
    		averageLoad2=linkWeights[currentNode].get(1);
    		payout1=payouts[currentNode].get(0);
    		payout2=payouts[currentNode].get(1);
    		System.out.println(averageLoad1+" "+averageLoad2+" "+payout1+" "+payout2);
    		
    		if(averageLoad1==0){  // || payout2==0
    			prevNode=currentNode;
        		prevLink=0;
    			return 0;
    		}
    		if(averageLoad2==0){
    			prevNode=currentNode;
        		prevLink=1;
    			return 1;
    		}
    		
    		if(averageLoad1<=averageLoad2){
    			if(payout1>=payout2){
    				prevNode=currentNode;
    				prevLink=0;
    				return 0;
    			}
    			else{
    			
    				
    				if(payout1<0 && payout2<=0){
    					if(averageLoad1/averageLoad2>=payout2/payout1){
    						prevNode=currentNode;
    	    				prevLink=1;
    	    				return 1;
    					}
    					else{
    						prevNode=currentNode;
    	    				prevLink=0;
    	    				return 0;
    					}
    				}
    				if(payout1>=0 && payout2>0){
    					if(averageLoad1/averageLoad2>=payout1/payout2){
    						prevNode=currentNode;
    	    				prevLink=1;
    	    				return 1;
    					}
    					else{
    						prevNode=currentNode;
    	    				prevLink=0;
    	    				return 0;
    					}
    				}
    				if(payout1<0 && payout2>0){
    					
    						prevNode=currentNode;
    	    				prevLink=1;
    	    				return 1;
    					
    					
    				}
    				
    			}
    		}
    		
    		if(averageLoad2<averageLoad1){
    			if(payout2>=payout1){
    				prevNode = currentNode;
    				prevLink=1;
    				return 1;
    			}
    			else{
    				if(payout2<0 && payout1<=0){
    					if(averageLoad1/averageLoad2>=payout1/payout2){
    						prevNode=currentNode;
    	    				prevLink=0;
    	    				return 0;
    					}
    					else{
    						prevNode=currentNode;
    	    				prevLink=1;
    	    				return 1;
    					}
    				}
    				if(payout2>=0 && payout1>0){
    					if(averageLoad1/averageLoad2>=payout2/payout1){
    						prevNode=currentNode;
    	    				prevLink=0;
    	    				return 0;
    					}
    					else{
    						prevNode=currentNode;
    	    				prevLink=1;
    	    				return 1;
    					}
    				}
    				if(payout2<0 && payout1>0){
    					
						prevNode=currentNode;
	    				prevLink=0;
	    				return 0;
					
					
    				}
    			}
    		}
    		return 0;
    		/*
    		if(payout2<0){
    			if(payout1>0){
    				prevNode = currentNode;
        			prevLink = 0;
        			return 0;
    			}
    			else{
    				if(payout1/payout2<=averageLoad1/averageLoad2){
    	    			prevNode=currentNode;
    	            	prevLink=1;
    	            	return 1;
    	    		}
    	    		else{
    	    			prevNode=currentNode;
    	            	prevLink=0;
    	            	return 0;
    	    		}
    			} 
    		}    		
    		
    		if(payout1<0){
    			if(payout2>0){
    				prevNode = currentNode;
        			prevLink = 1;
        			return 1;
    			}
    			else{
    				if(payout1/payout2<=averageLoad1/averageLoad2){
    	    			prevNode=currentNode;
    	            	prevLink=1;
    	            	return 1;
    	    		}
    	    		else{
    	    			prevNode=currentNode;
    	            	prevLink=0;
    	            	return 0;
    	    		}
    			}
    		}
    		//end of code added by me
    		
    		if(payout1/payout2<=averageLoad1/averageLoad2){
    			prevNode=currentNode;
            	prevLink=1;
            	return 1;
    		}
    		else{
    			prevNode=currentNode;
            	prevLink=0;
            	return 0;
    		}*/
    			
    		
    		
    		
    	}
    	

    }
    
    // This function is called after new information is obtained about the world
    // Information is stored in the information variables defined at the beginning of the class
    // ********* You should use this function to update you agent's estimates/model of the world (if you choose to model the world)
    public void updateInternalModels() {
    }
    
    // You shouldn't need to modify this function    
	public void play() {
        
        while (true) {
            if (!readStatusMessage())
                break;
            
            updateInternalModels();
            
            act();
        }
	}
    
    // You shouldn't need to modify this function
    public void act() {
        int a = selectAction();
        
        // visit the node if it has positive utility
        String buf;
        int destination = world[currentNode].links[a];
        if (currentUtilitiesforVisitingNodes[destination] > 0)
            buf = a + " Y\n";
        else    
            buf = a + " N\n";
        
        System.out.print("Sent: " + buf);
        sout.println(buf);
    }
    
    // You shouldn't modify this function
    // This function gets an update from the server each time your car reaches a new node
    // The server supplies you with the following information:
    //      1. Reward/cost information: travelCost: the cost (measured in happiness units) you incurred for traveling over the last link
    //                               payout: the number of happiness units you got for arriving at the current node
    //                               tollCharge: the amount of toll charge you incurred for traveling the latest link
    //      2. currentNode: the current node position your car is at in the world (value between 0-3)
    //      3. currentUtilitiesforVisitingNode [array of 4]: The number of happiness units you will get in the future for arriving at each of the 4 nodes    
    public boolean readStatusMessage() {
        try {
//            System.out.println(sin.readLine());
            String [] buf = sin.readLine().split(" ");
            
            if (buf[0].equals("quit"))
                return false;
            
            travelCost = Double.valueOf(buf[1]);
            payout = Double.valueOf(buf[2]);
            tollCharge = Double.valueOf(buf[3]);
            System.out.println("Results: " + travelCost + " " + payout + " " + tollCharge);
            
            // parse my current position in the world
            String [] buf2 = sin.readLine().split(" ");
            currentNode = Integer.valueOf(buf2[1]);
            System.out.println("Position: " + currentNode);
            
            // parse my utilities for visiting each node
            String [] buf3 = sin.readLine().split(" ");
            int i;
            for (i = 0; i < numNodes; i++)
                currentUtilitiesforVisitingNodes[i] = Double.valueOf(buf3[i+1]);
            System.out.println("Utilities: " + currentUtilitiesforVisitingNodes[0] + " " +
                               currentUtilitiesforVisitingNodes[1] + " " +
                               currentUtilitiesforVisitingNodes[2] + " " +
                               currentUtilitiesforVisitingNodes[3]);
            
            // parse linkState stuff
            String [] buf4 = sin.readLine().split(" ");
            aveOnLink = Double.valueOf(buf4[1]);
            capacity = Integer.parseInt(buf4[2]);
            System.out.println("linkState: " + aveOnLink + " " + capacity);
        }
        catch (IOException e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    // **********************************************************************************
    //
    // Theoretically, you shouldn't have to alter anything below this point in this file
    //      unless you want to change the color of your agent
    //
    // **********************************************************************************
    public void getConnected(String args[]) {
		try{
			// initial connection
			int port = 3000 + Integer.parseInt(args[1]);
			s = new Socket(args[0], port);
            sout = new PrintWriter(s.getOutputStream(), true);
			sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
            // read in the map of the world
			numNodes = Integer.parseInt(sin.readLine());
            int i, j;
            for (i = 0; i < numNodes; i++) {
                world[i] = new node();
                String [] buf = sin.readLine().split(" ");
                world[i].posx = Double.valueOf(buf[0]);
                world[i].posy = Double.valueOf(buf[1]);
                world[i].numLinks = Integer.parseInt(buf[2]);
                //System.out.println(world[i].posx + ", " + world[i].posy);
                for (j = 0; j < 4; j++) {
                    if (j < world[i].numLinks) {
                        world[i].links[j] = Integer.parseInt(buf[3+j]);
                        //System.out.println("Linked to: " + world[i].links[j]);
                    }
                    else
                        world[i].links[j] = -1;
                }
            }
            currentNode = Integer.parseInt(sin.readLine());
            
            String myinfo = args[2] + "\n" + "0.0 0.0 0.9\n";  // name + rgb values; i think this is color is pink
            // send the agents name and color
            sout.println(myinfo);
		}
		catch (IOException e) {
			System.out.println(e);
		}
    }
}