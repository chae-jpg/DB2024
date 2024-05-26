package worker.Professor;

public class Professor {
	//교수의 정보를 이용하는 클래스
    private int professorID;
    private String name;
    private String department;
    private String email;
    private String phone;
    private String password;

    public Professor(int professorID, String name, String department, String email, String phone, String password) {//교수의 정보를 받아서 저장한다
        //교수의 정보를 받아와서 저장한다.
    	this.professorID = professorID;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getProfessorID() {
        return professorID;//교수의 아이디를 줌
    }

    public String getName() {
        return name;//교수의 이름을 줌
    }

    public String getDepartment() {
        return department;//교수의 학과를 줌
    }

    public String getEmail() {
        return email;//교수의 이메일을 줌
    }

    public String getPhone() {
        return phone;//교수의 핸드폰 번호를 줌
    }

    public String getPassword() {
        return password;// 교수의 비밀번호를 줌
    }
}
