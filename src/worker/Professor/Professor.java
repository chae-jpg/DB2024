package worker.Professor;

public class Professor {
    private int professorID;
    private String name;
    private String department;
    private String email;
    private String phone;
    private String password;

    public Professor(int professorID, String name, String department, String email, String phone, String password) {
        this.professorID = professorID;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getProfessorID() {
        return professorID;
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

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
