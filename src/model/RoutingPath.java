package model;

public class RoutingPath {
		private final String id;
		private final EntryPoint origin;
		private final EntryPoint destination;
		private final int duration;

		public RoutingPath(String id, EntryPoint origin, EntryPoint destination, int duration) {
			this.id = id;
			this.origin = origin;
			this.destination = destination;
			this.duration = duration;
		}

		public String getId() {
			return id;
		}

		public EntryPoint getDestination() {
			return destination;
		}

		public EntryPoint getOrigin() {
			return origin;
		}

		public int getDuration() {
			return duration;
		}

}
