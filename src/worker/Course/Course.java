package worker.Course;

public class Course {
    private int courseID;
    private String courseName;
    private String classroom;
    private int credit;
    private String semester;
    private String day;
    private String time;
    private int professorID;

    public Course(int courseID, String courseName, String classroom, int credit, String semester, String day, String time, int professorID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.classroom = classroom;
        this.credit = credit;
        this.semester = semester;
        this.day = day;
        this.time = time;
        this.professorID = professorID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getClassroom() {
        return classroom;
    }

    public int getCredit() {
        return credit;
    }

    public String getSemester() {
        return semester;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public int getProfessorID() {
        return professorID;
    }
}
