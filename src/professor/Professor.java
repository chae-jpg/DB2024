package professor;


// 교수 클래스 생성
public class Professor {

	
	private static Professor professor;
	private String id;

	public static Professor getInstance() { //instance를 싱글톤으로 만들기 

		if (professor == null) {
			professor = new Professor();
		}
		return professor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

