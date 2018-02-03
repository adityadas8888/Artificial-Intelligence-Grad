package com.search.uniformcost;

import java.util.ArrayList;


public class Node{
	public int city;
	public String name;
	public ArrayList<Node> adjacentNodes;
	private int cost = 0;
	public String path = "";
	public boolean visited;

	public Node(int value, String name){
		this.city = value;
		this.name = name;
	}
	
	public void updateCost(int cost){
		this.cost += cost;
	}
	
	public int getCost(){
		return this.cost;
	}
	
	public void updatePath(String name) {
		path = path + name;
	}
	
	@SuppressWarnings("unused")
	public void resetCost(){
		cost = 0;
	}

	public void expandNode(ArrayList<Node> nodes){
		this.adjacentNodes = nodes;
	}
}
