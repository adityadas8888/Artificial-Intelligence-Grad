package com.search.uniformcost;

public class Edge{
	public Node source;
	public Node destination;
	public int cost;

	public Edge(Node src, Node dest, int cost){
		this.source = src;
		this.destination = dest;
		this.cost = cost;
	}
}
