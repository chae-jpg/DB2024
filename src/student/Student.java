package student;

public class Student {

	private static Student student;
	private String studentId;

	public static Student getInstance() {
		if (student == null) {
			student = new Student();
		}
		return student;
	}

	public void makeNull() {
		// 싱글톤 개체 초기화
		student = null;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

}
