package professor;

// 교수 클래스 생성
public class Professor {

	private static Professor professor;
	private String id;
	private String name;
	private String department;
	private String email;
	private String phone;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
