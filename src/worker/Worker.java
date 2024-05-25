package worker;

public class Worker {
	
	private static Worker worker;
	private String id;

	public static Worker getInstance() {
		if (worker == null) {
			worker = new Worker();
		}
		return worker;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
