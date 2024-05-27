package worker.Course;

public class Course {
    // 강의 ID
    private int courseID;
    
    // 강의 이름
    private String courseName;
    
    // 강의실
    private String classroom;
    
    // 학점
    private int credit;
    
    // 학기
    private String semester;
    
    // 요일
    private String day;
    
    // 시간
    private String time;
    
    // 교수 ID
    private int professorID;

    // 생성자: 모든 필드를 초기화
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

    // 각 필드에 대한 getter 메서드들
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
