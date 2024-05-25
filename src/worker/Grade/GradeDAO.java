package worker.Grade;

import main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

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
