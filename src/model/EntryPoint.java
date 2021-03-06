package model;

public class EntryPoint {
	private final String id;
	private final String name;

	public EntryPoint(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return ((id == null) ? 0 : id.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		EntryPoint vertex = (EntryPoint) obj;
		if (id == null) {
			if (vertex.id != null)
				return false;
		} else if (!id.equals(vertex.id))
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
