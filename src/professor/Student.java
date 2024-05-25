package professor;

public class Student {
    private int studentID;
    private String name;
    private String department;
    private String email;
    private String contact;

    public Student(int studentID, String name, String department, String email, String contact) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.email = email;
        this.contact = contact;
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
}
