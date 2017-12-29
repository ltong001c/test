package model;

public class Baggage {
	
	private final String baggageId;
	private final EntryPoint entryPoint;
	private final String flightId;

	public Baggage(String baggageId, EntryPoint entryPoint, String flightId) {
		this.baggageId = baggageId;
		this.entryPoint = entryPoint;
		this.flightId = flightId;
	}

	public String getBaggageId() {
		return baggageId;
	}

	public EntryPoint getEntryPoint() {
		return entryPoint;
	}

	public String getFlightId() {
		return flightId;
	}


}
