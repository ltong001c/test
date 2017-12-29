package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import controller.RoutingPathController;
import model.Baggage;
import model.EntryPoint;
import model.RoutingLayout;
import model.RoutingPath;

public class Application {
	
	private static List<EntryPoint> entryPoints;
	private static List<RoutingPath> routingPaths;
	private static RoutingLayout layout;
	private static RoutingPathController controller;
	private static Map<String,EntryPoint> departures;
	private static List<Baggage> baggages;

	public static void main(String args[]) {
		entryPointsConfig();
		routingPathConfig();
		departureMapConfig();
		layout = new RoutingLayout(entryPoints, routingPaths);
		controller = new RoutingPathController(layout);
		
		baggages = new LinkedList<>();
	    Baggage baggage_1 = new Baggage("0001", entryPoints.get(0), "UA12");
	    Baggage baggage_2 = new Baggage("0002", entryPoints.get(5), "UA17");
	    Baggage baggage_3 = new Baggage("0003", entryPoints.get(2), "UA10");
	    Baggage baggage_4 = new Baggage("0004", entryPoints.get(8), "UA18");
	    Baggage baggage_5 = new Baggage("0005", entryPoints.get(7), "ARRIVAL");
	    baggages.add(baggage_1);
	    baggages.add(baggage_2);
	    baggages.add(baggage_3);
	    baggages.add(baggage_4);
	    baggages.add(baggage_5);
	    
	    for (Baggage baggage : baggages) {
			getPathDuration(baggage);
		}
	}

	private static void entryPointsConfig() {
		entryPoints = new ArrayList<EntryPoint>();
		
		for (int i = 0; i <= 11; i++) {
			if (i==0) {
				EntryPoint entryPoint = new EntryPoint("Concourse_A_Ticketing", "Concourse_A_Ticketing");
				entryPoints.add(entryPoint);
			} else if (i==11) {
				EntryPoint entryPoint = new EntryPoint("BaggageClaim", "BaggageClaim");
				entryPoints.add(entryPoint);
			} else {
			    EntryPoint entryPoint = new EntryPoint("A" + i, "A" + i);
			    entryPoints.add(entryPoint);
			}
	    }
	}
	
	private static void routingPathConfig() {
		routingPaths = new ArrayList<RoutingPath>();
		//Add routingPaths
		addPath("Path_0", 0, 5, 5);
		addPath("Path_1", 5, 11, 5);
		addPath("Path_2", 5, 10, 4);
		addPath("Path_3", 5, 1, 6);
		addPath("Path_4", 1, 2, 1);
		addPath("Path_5", 2, 3, 1);
		addPath("Path_6", 3, 4, 1);
		addPath("Path_7", 10, 9, 1);
		addPath("Path_8", 9, 8, 1);
		addPath("Path_9", 8, 7, 1);
		addPath("Path_10", 7, 6, 1);
	}
	
	private static void addPath(String pathId, int sourceLocNo, int destLocNo,
			int duration) {
		
		//Bidirectional Path
		RoutingPath lane = new RoutingPath(pathId, entryPoints.get(sourceLocNo),
				entryPoints.get(destLocNo), duration);
		routingPaths.add(lane);
		
		lane = new RoutingPath(pathId, entryPoints.get(destLocNo),
				entryPoints.get(sourceLocNo), duration);
		routingPaths.add(lane);
	}
	
	private static void departureMapConfig() {
		departures = new HashMap<>();
		departures.put("UA11", entryPoints.get(1));
		departures.put("UA12", entryPoints.get(1));
		departures.put("UA13", entryPoints.get(2));
		departures.put("UA14", entryPoints.get(2));
		departures.put("UA10", entryPoints.get(1));
		departures.put("UA15", entryPoints.get(2));
		departures.put("UA16", entryPoints.get(3));
		departures.put("UA17", entryPoints.get(4));
		departures.put("UA18", entryPoints.get(5));
		departures.put("ARRIVAL", entryPoints.get(11));
	}
	
	private static void getPathDuration(Baggage baggage) {
		
		String baggageId = baggage.getBaggageId();
		controller.run(baggage.getEntryPoint());
		Map<Integer,LinkedList<EntryPoint>> path = controller.getPath(departures.get(baggage.getFlightId()));
		
		for (Map.Entry<Integer,LinkedList<EntryPoint>> pathDistance : path.entrySet()) {
			System.out.println(baggageId +"\t" + pathDistance.getValue().toString() + ": "+pathDistance.getKey());
		}
	}
}
