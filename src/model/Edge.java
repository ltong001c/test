package model;

public class Edge {
		private final String id;
		private final Vertex origin;
		private final Vertex destination;
		private final int weight;

		public Edge(String id, Vertex origin, Vertex destination, int weight) {
			this.id = id;
			this.origin = origin;
			this.destination = destination;
			this.weight = weight;
		}

		public String getId() {
			return id;
		}

		public Vertex getDestination() {
			return destination;
		}

		public Vertex getOrigin() {
			return origin;
		}

		public int getWeight() {
			return weight;
		}

}
