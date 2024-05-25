package worker.Grade;

public class Grade {
    private int gradeID;
    private int studentID;
    private int courseID;
    private String grade;
    private String semester;
    private boolean repetition;

    public Grade(int gradeID, int studentID, int courseID, String grade, String semester, boolean repetition) {
        this.gradeID = gradeID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
        this.semester = semester;
        this.repetition = repetition;
    }

    // Getters
    public int getGradeID() {
        return gradeID;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getGrade() {
        return grade;
    }

    public String getSemester() {
        return semester;
    }

    public boolean isRepetition() {
        return repetition;
    }
}
