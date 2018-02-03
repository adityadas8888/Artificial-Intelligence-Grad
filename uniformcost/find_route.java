package com.search.uniformcost;


public class find_route {

	public static void main(String[] args) {
		
		if(!isValid(args[0]) || !isValid(args[1]) || !isValid(args[2])) {
			System.out.println("Input arguments are not correct! Please check them!");
			return;
		}
		
		UniformCostSearch uniformCostSearch = new UniformCostSearch(args[0]);
		
		if(!uniformCostSearch.cityList.contains(args[1]) || !uniformCostSearch.cityList.contains(args[2])) {
			
			return;
		} 
		
		Node src = uniformCostSearch.cities.get(uniformCostSearch.cityList.indexOf(args[1]));
		Node dest = uniformCostSearch.cities.get(uniformCostSearch.cityList.indexOf(args[2]));
		
//		if(src == null || dest ==null) {
//			System.out.println("Input arguments are not correct! Please check them!");
//			return;
//		}
		
		uniformCostSearch.applyUniformCostSearch(src, dest);
		System.out.println("distance: "+ (uniformCostSearch.resultDistance>=0? 
				uniformCostSearch.resultDistance + " km"
				:"infinity"));
		System.out.println("route: ");
		System.out.println(uniformCostSearch.result);
	}
	
	private static boolean isValid(String argument) {
		return !argument.isEmpty() && argument != null;
	}

}