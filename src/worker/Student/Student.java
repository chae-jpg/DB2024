package worker.Student;

public class Student {
    private int studentID;
    private String name;
    private String department;
    private String email;
    private String contact;
    private String password;

    public Student(int studentID, String name, String department, String email, String contact, String password) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

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
