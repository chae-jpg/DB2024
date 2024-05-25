package student.domain;

public class StudentDomain {

    private long studentID;
    private String name;
    private String department;
    private String email;
    private String contact;
    private String pw;

    public StudentDomain(long studentID, String name, String department, String email, String contact, String pw) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.email = email;
        this.contact = contact;
        this.pw = pw;
    }

    public long getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment(){
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getPw() {
        return pw;
    }
}
