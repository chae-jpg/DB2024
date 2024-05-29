package worker.Student;

//아래와 같은 private 멤버 변수를 가지고 있는 Student 클래스
public class Student {
    private int studentID;
    private String name;
    private String department;
    private String email;
    private String contact;
    private String password;
//다음의 생성자는  학생의 정보를 받아와서 해당 정보로 학생 객체를 초기화함.
    public Student(int studentID, String name, String department, String email, String contact, String password) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }
// 다음의 getter 메서드들은 각각 학생 정보를 반환함.
    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }
}
