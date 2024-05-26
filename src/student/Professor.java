package student;

public class Professor {// 교수의 정보를 이용하는 클래스
    private int professorID;
    private String name;
    private String department;
    private String email;
    private String phone;

    public Professor(int professorID, String name, String department, String email, String phone) {
    	//교수의 정보를 받아와 저장
        this.professorID = professorID;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    public int getProfessorID() {
        return professorID;// 교수의 아이디
    }

    public String getName() {
        return name;//교수의이름
    }

    public String getDepartment() {
        return department;//교수의 학과
    }

    public String getEmail() {
        return email;//교수의 이메일
    }

    public String getPhone() {
        return phone;//교수의 전화번호
    }
}
