package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import controller.RoutingPathController;
import junit.framework.TestCase;
import model.Baggage;
import model.EntryPoint;
import model.RoutingLayout;
import model.RoutingPath;

public class TestRoutingPathController extends TestCase{
	
	private List<EntryPoint> entryPoints;
	private List<RoutingPath> routingPaths;
	private RoutingLayout layout;
	private RoutingPathController controller;
	private Map<String,EntryPoint> departures;
	private List<Baggage> baggages;
	
	@Override
	public void setUp() throws Exception {
		entryPointsConfig();
		routingPathConfig();
		departureMapConfig();
		layout = new RoutingLayout(entryPoints, routingPaths);
		controller = new RoutingPathController(layout);
	}

	private void entryPointsConfig() {
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
	
	private void routingPathConfig() {
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
	
	private void addPath(String pathId, int sourceLocNo, int destLocNo,
			int duration) {
		
		//Bidirectional Path
		RoutingPath lane = new RoutingPath(pathId, entryPoints.get(sourceLocNo),
				entryPoints.get(destLocNo), duration);
		routingPaths.add(lane);
		
		lane = new RoutingPath(pathId, entryPoints.get(destLocNo),
				entryPoints.get(sourceLocNo), duration);
		routingPaths.add(lane);
	}
	
	private void departureMapConfig() {
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
	
	@Test
	public void testGetDuration() {
		EntryPoint start_1 = new EntryPoint("Concourse_A_Ticketing", "Concourse_A_Ticketing");
		EntryPoint target_1 = new EntryPoint("A5", "A5");
		EntryPoint start_2 = new EntryPoint("A1", "A1");
		EntryPoint target_2 = new EntryPoint("A5", "A5");
		EntryPoint start_3 = new EntryPoint("A10", "A10");
		EntryPoint target_3 = new EntryPoint("A5", "A5");
		int duration_1 = controller.getDuration(start_1, target_1);
		int duration_2 = controller.getDuration(start_2, target_2);
		int duration_3 = controller.getDuration(start_3, target_3);
		Assert.assertEquals(5, duration_1);
		Assert.assertEquals(6, duration_2);
		Assert.assertEquals(4, duration_3);
	}
	
	@Test
	public void testGetMinimum() {
		EntryPoint entryPoint = new EntryPoint("A10", "A10");
		controller.run(entryPoint);
		Set<EntryPoint> unvisited = new HashSet<>();
		unvisited.add(new EntryPoint("A5", "A5"));
		unvisited.add(new EntryPoint("A9", "A9"));
		unvisited.add(new EntryPoint("Concourse_A_Ticketing", "Concourse_A_Ticketing"));
		unvisited.add(new EntryPoint("A3", "A3"));
		EntryPoint min = controller.getMinimum(unvisited);
		Assert.assertTrue(min.equals(new EntryPoint("A9", "A9")));
	}
	
	@Test
	public void testFindMinimalDurations() {
		EntryPoint entryPoint = new EntryPoint("A10", "A10");
		controller.run(entryPoint);
		int duration_1 = controller.getDuration().get(new EntryPoint("A1", "A1"));
		int duration_2 = controller.getDuration().get(new EntryPoint("A2", "A2"));
		int duration_3 = controller.getDuration().get(new EntryPoint("A3", "A3"));
		int duration_4 = controller.getDuration().get(new EntryPoint("A4", "A4"));
		int duration_5 = controller.getDuration().get(new EntryPoint("A5", "A5"));
		int duration_6 = controller.getDuration().get(new EntryPoint("A6", "A6"));
		int duration_7 = controller.getDuration().get(new EntryPoint("A7", "A7"));
		int duration_8 = controller.getDuration().get(new EntryPoint("A8", "A8"));
		int duration_9 = controller.getDuration().get(new EntryPoint("A9", "A9"));
		int duration_10 = controller.getDuration().get(new EntryPoint("A10", "A10"));
		int duration_11 = controller.getDuration().get(new EntryPoint("Concourse_A_Ticketing", "Concourse_A_Ticketing"));
		int duration_12 = controller.getDuration().get(new EntryPoint("BaggageClaim", "BaggageClaim"));
		Assert.assertEquals(10, duration_1);
		Assert.assertEquals(11, duration_2);
		Assert.assertEquals(12, duration_3);
		Assert.assertEquals(13, duration_4);
		Assert.assertEquals(4, duration_5);
		Assert.assertEquals(4, duration_6);
		Assert.assertEquals(3, duration_7);
		Assert.assertEquals(2, duration_8);
		Assert.assertEquals(1, duration_9);
		Assert.assertEquals(0, duration_10);
		Assert.assertEquals(9, duration_11);
		Assert.assertEquals(9, duration_12);
	}

	@Test
	public void testRun() {
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
	
	private void getPathDuration(Baggage baggage) {
		
		String baggageId = baggage.getBaggageId();
		controller.run(baggage.getEntryPoint());
		Map<Integer,LinkedList<EntryPoint>> path = controller.getPath(departures.get(baggage.getFlightId()));
		
		for (Map.Entry<Integer,LinkedList<EntryPoint>> pathDistance : path.entrySet()) {
			System.out.println(baggageId +"\t" + pathDistance.getValue().toString() + ": "+pathDistance.getKey());
		}
	}
}
