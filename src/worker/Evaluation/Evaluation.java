package worker.Evaluation;

import java.sql.Date;

public class Evaluation {
    private int evaluationID;
    private int score;
    private Date date;
    private String comment;
    private int studentID;
    private int courseID;

    public Evaluation(int evaluationID, int score, Date date, String comment, int studentID, int courseID) {
        this.evaluationID = evaluationID;
        this.score = score;
        this.date = date;
        this.comment = comment;
        this.studentID = studentID;
        this.courseID = courseID;
    }

    public int getEvaluationID() {
        return evaluationID;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }
}
