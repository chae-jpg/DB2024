package worker.Grade;

import main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    /**
     * 주어진 학생 ID로 성적을 조회합니다.
     * 
     * @param studentID 학생의 ID
     * @return 해당 학생의 성적 리스트
     */
    public List<Grade> getGradesByStudentID(int studentID) {
        List<Grade> grades = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM DB2024_Grade WHERE StudentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int gradeID = resultSet.getInt("GradeID");
                int courseID = resultSet.getInt("CourseID");
                String grade = resultSet.getString("Grade");
                String semester = resultSet.getString("Semester");
                boolean repetition = resultSet.getBoolean("Repetition");
                grades.add(new Grade(gradeID, studentID, courseID, grade, semester, repetition));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    /**
     * 주어진 강의 ID로 성적을 조회합니다.
     * 
     * @param courseID 강의의 ID
     * @return 해당 강의의 성적 리스트
     */
    public List<Grade> getGradesByCourseID(int courseID) {
        List<Grade> grades = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM DB2024_Grade WHERE CourseID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int gradeID = resultSet.getInt("GradeID");
                int studentID = resultSet.getInt("StudentID");
                String grade = resultSet.getString("Grade");
                String semester = resultSet.getString("Semester");
                boolean repetition = resultSet.getBoolean("Repetition");
                grades.add(new Grade(gradeID, studentID, courseID, grade, semester, repetition));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    /**
     * 모든 성적을 조회합니다.
     * 
     * @return 전체 성적 리스트
     */
    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM DB2024_Grade";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int gradeID = resultSet.getInt("GradeID");
                int studentID = resultSet.getInt("StudentID");
                int courseID = resultSet.getInt("CourseID");
                String grade = resultSet.getString("Grade");
                String semester = resultSet.getString("Semester");
                boolean repetition = resultSet.getBoolean("Repetition");
                grades.add(new Grade(gradeID, studentID, courseID, grade, semester, repetition));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    /**
     * 주어진 성적 ID로 성적을 삭제합니다.
     * 
     * @param gradeID 성적의 ID
     */
    public void deleteGrade(int gradeID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM DB2024_Grade WHERE GradeID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gradeID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
