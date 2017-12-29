package model;

import java.util.List;

public class RoutingLayout {
	
	private final List<EntryPoint> entryPoints;
	private final List<RoutingPath> routingPaths;

	public RoutingLayout(List<EntryPoint> entryPoints, List<RoutingPath> routingPaths) {
		this.entryPoints = entryPoints;
		this.routingPaths = routingPaths;
	}

	public List<EntryPoint> getEntryPoints() {
		return entryPoints;
	}

	public List<RoutingPath> getRoutingPaths() {
		return routingPaths;
	}

}
