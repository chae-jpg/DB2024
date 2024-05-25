package professor;

import student.Student;

public class Professor {

	private static Professor professor;
	private String professorId;

	public static Professor getInstance() {
		if (professor == null) {
			professor = new Professor();
		}
		return professor;
	}

	public String getProfessorId() {
		return professorId;
	}

	public void setId(String professorId) {
		this.professorId = professorId;
	}
}