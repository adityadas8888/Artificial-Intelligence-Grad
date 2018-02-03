package com.search.uniformcost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class UniformCostSearch {

	ArrayList<Node> nodeList = new ArrayList<Node>();
	public Integer[][] connectedCities = new Integer[Constants.MAX_CITIES][Constants.MAX_CITIES];
	public HashMap<Integer, Node> cities = new HashMap<Integer, Node>();
	ArrayList<String> cityList = new ArrayList<String>();

	public int resultDistance = 0;
	String result;

	public UniformCostSearch(String inputFile) {
		init(inputFile);
	}

	private void init(String inputFile) {
		
		@SuppressWarnings("resource")
		Scanner reader;
		try {
			reader = new Scanner(new File(inputFile));
			while (reader.hasNext()) {
				String str = reader.nextLine();
				if (!str.equals(Constants.END_OF_INPUT)) {
					StringTokenizer st = new StringTokenizer(str);

					if (st.countTokens() == 3) {
						String city1 = st.nextToken();
						if (!cityList.contains(city1)) {
							cityList.add(city1);
						}
						int index1 = cityList.indexOf(city1);

						String city2 = st.nextToken();
						if (!cityList.contains(city2)) {
							cityList.add(city2);
						}
						int index2 = cityList.indexOf(city2);
						connectedCities[index1][index2] = Integer.valueOf(st.nextToken());
					} else {
						System.out.println("Input file formatting is incorrect!");
						System.exit(0);
					}
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < cityList.size(); i++) {
			Node node = new Node(i, cityList.get(i));
			nodeList.add(node);
		}

		for (int i = 0; i < cityList.size(); i++) {
			this.cities.put(i, nodeList.get(i));
		}
	}

	/**
	 * Returns all nodes
	 */
	private ArrayList<Node> getAdjacentNodes(int n) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < Constants.MAX_CITIES; i++) {
			if (connectedCities[n][i] != null) {
				nodes.add(cities.get(i));
			}
			if (connectedCities[i][n] != null) {
				nodes.add(cities.get(i));
			}
		}
		return nodes;
	}

	public void applyUniformCostSearch(Node start, Node destination) {
		Comparator<Node> comparator = new NodeComparator();
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(Constants.MAX_CITIES, comparator);
		priorityQueue.add(start);
		
		ArrayList<Node> visited = new ArrayList<Node>();
		
		while (!priorityQueue.isEmpty()) {
			
			Node currentCity = priorityQueue.remove();
			currentCity.visited = true;
			visited.add(currentCity);
			
			// check if destination reached
			if (currentCity.city == destination.city) {
				resultDistance = currentCity.getCost();
				result = currentCity.path;
				return;
			}
			// expanding
			currentCity.expandNode(getAdjacentNodes(currentCity.city));

			ArrayList<Node> childNodes = currentCity.adjacentNodes;
			
			if (!childNodes.isEmpty()) {
				for (int i = 0; i < childNodes.size(); i++) {
					Node child = childNodes.get(i);
					if (!visited.contains(child) && !priorityQueue.contains(child) && !child.visited) {
						
						if(connectedCities[currentCity.city][child.city] != null) {
							child.updateCost(currentCity.getCost() + connectedCities[currentCity.city][child.city]);
							child.updatePath(currentCity.path + "\n" +
									currentCity.name + " " + child.name + " "
									+ String.valueOf(connectedCities[currentCity.city][child.city]));
						} else if(connectedCities[child.city][currentCity.city] != null) {
							child.updateCost(currentCity.getCost() + connectedCities[child.city][currentCity.city]);
							child.updatePath(currentCity.path + "\n" +
									currentCity.name + " " + child.name + " "
									+ String.valueOf(connectedCities[child.city][currentCity.city]));
						}
						priorityQueue.add(child);
					}
				}
			}
		}
		// no path between cities
		resultDistance = -1;
		result = "none";

	}

}
