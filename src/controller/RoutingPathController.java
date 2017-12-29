package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.EntryPoint;
import model.RoutingLayout;
import model.RoutingPath;

public class RoutingPathController {
	private List<EntryPoint> entryPoints;
	private List<RoutingPath> routingPaths;
	private Set<EntryPoint> visitedEntryPoints;
	private Set<EntryPoint> unvisitedEntryPoints;
	private Map<EntryPoint, EntryPoint> predecessors;
	private Map<EntryPoint, Integer> duration;

	public RoutingPathController(RoutingLayout layout) {
		this.entryPoints = new ArrayList<EntryPoint>(layout.getEntryPoints());
		this.routingPaths = new ArrayList<RoutingPath>(layout.getRoutingPaths());
	} 

	public void run(EntryPoint entryPoint) {
		visitedEntryPoints = new HashSet<EntryPoint>();
		unvisitedEntryPoints = new HashSet<EntryPoint>();
		duration = new HashMap<EntryPoint, Integer>();
		predecessors = new HashMap<EntryPoint, EntryPoint>();
		duration.put(entryPoint, 0);
		unvisitedEntryPoints.add(entryPoint);
		while (unvisitedEntryPoints.size() > 0) {
			EntryPoint ep = getMinimum(unvisitedEntryPoints);
			visitedEntryPoints.add(ep);
			unvisitedEntryPoints.remove(ep);
			findMinimalDurations(ep);
		}
	}

	public void findMinimalDurations(EntryPoint entryPoint) {
		List<EntryPoint> adjacentEntryPoints = getNeighbors(entryPoint);
		for (EntryPoint target : adjacentEntryPoints) {
			if (getShortestDuration(target) > getShortestDuration(entryPoint)
					+ getDuration(entryPoint, target)) {
				duration.put(target,
						getShortestDuration(entryPoint) + getDuration(entryPoint, target));
				predecessors.put(target, entryPoint);
				unvisitedEntryPoints.add(target);
			}
		}
	}

	public int getDuration(EntryPoint start, EntryPoint target) {
		for (RoutingPath routingPath : routingPaths) {
			if (routingPath.getOrigin().equals(start)
					&& routingPath.getDestination().equals(target)) {
				return routingPath.getDuration();
			}
		}
		throw new RuntimeException("Can not find the distance between two points");
	}

	public List<EntryPoint> getNeighbors(EntryPoint entryPoint) {
		List<EntryPoint> neighbors = new ArrayList<EntryPoint>();
		for (RoutingPath routingPath : routingPaths) {
			if (routingPath.getOrigin().equals(entryPoint)
					&& !isSettled(routingPath.getDestination())) {
				neighbors.add(routingPath.getDestination());
			}
		}
		return neighbors;
	}

	public EntryPoint getMinimum(Set<EntryPoint> entryPoints) {
		EntryPoint minimum = null;
		for (EntryPoint entryPoint : entryPoints) {
			if (minimum == null) {
				minimum = entryPoint;
			} else {
				if (getShortestDuration(entryPoint) < getShortestDuration(minimum)) {
					minimum = entryPoint;
				}
			}
		}
		return minimum;
	}

	public boolean isSettled(EntryPoint entryPoint) {
		return visitedEntryPoints.contains(entryPoint);
	}

	public int getShortestDuration(EntryPoint destination) {
		Integer d = duration.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public Map<Integer,LinkedList<EntryPoint>> getPath(EntryPoint target) {
		LinkedList<EntryPoint> path = new LinkedList<EntryPoint>();
		EntryPoint step = target;
		int totalSum = 0;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			totalSum += getDuration(step, predecessors.get(step));
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		Map<Integer,LinkedList<EntryPoint>> routeMap = new HashMap<>();
		routeMap.put(Integer.valueOf(totalSum), path);
		
		return routeMap;
	}

	public List<EntryPoint> getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(List<EntryPoint> entryPoints) {
		this.entryPoints = entryPoints;
	}

	public List<RoutingPath> getRoutingPaths() {
		return routingPaths;
	}

	public void setRoutingPaths(List<RoutingPath> routingPaths) {
		this.routingPaths = routingPaths;
	}

	public Set<EntryPoint> getVisitedEntryPoints() {
		return visitedEntryPoints;
	}

	public void setVisitedEntryPoints(Set<EntryPoint> visitedEntryPoints) {
		this.visitedEntryPoints = visitedEntryPoints;
	}

	public Set<EntryPoint> getUnvisitedEntryPoints() {
		return unvisitedEntryPoints;
	}

	public void setUnvisitedEntryPoints(Set<EntryPoint> unvisitedEntryPoints) {
		this.unvisitedEntryPoints = unvisitedEntryPoints;
	}

	public Map<EntryPoint, EntryPoint> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(Map<EntryPoint, EntryPoint> predecessors) {
		this.predecessors = predecessors;
	}

	public Map<EntryPoint, Integer> getDuration() {
		return duration;
	}

	public void setDuration(Map<EntryPoint, Integer> duration) {
		this.duration = duration;
	}

}
