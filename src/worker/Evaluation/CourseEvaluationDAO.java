package worker.Evaluation;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseEvaluationDAO {

    public List<Evaluation> getEvaluationsByCourseName(String courseName) {
        List<Evaluation> evaluations = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT e.EvaluationID, e.Score, e.Date, e.Comment, e.StudentID, e.CourseID " +
                    "FROM DB2024_Evaluation e " +
                    "JOIN DB2024_Course c ON e.CourseID = c.CourseID " +
                    "WHERE c.CourseName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int evaluationID = resultSet.getInt("EvaluationID");
                int score = resultSet.getInt("Score");
                java.sql.Date date = resultSet.getDate("Date");
                String comment = resultSet.getString("Comment");
                int studentID = resultSet.getInt("StudentID");
                int courseID = resultSet.getInt("CourseID");
                evaluations.add(new Evaluation(evaluationID, score, date, comment, studentID, courseID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    public List<Evaluation> getEvaluationsByCourseID(int courseID) {
        List<Evaluation> evaluations = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Evaluation WHERE CourseID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int evaluationID = resultSet.getInt("EvaluationID");
                int score = resultSet.getInt("Score");
                java.sql.Date date = resultSet.getDate("Date");
                String comment = resultSet.getString("Comment");
                int studentID = resultSet.getInt("StudentID");
                evaluations.add(new Evaluation(evaluationID, score, date, comment, studentID, courseID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    public List<Evaluation> getAllEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Evaluation";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int evaluationID = resultSet.getInt("EvaluationID");
                int score = resultSet.getInt("Score");
                java.sql.Date date = resultSet.getDate("Date");
                String comment = resultSet.getString("Comment");
                int studentID = resultSet.getInt("StudentID");
                int courseID = resultSet.getInt("CourseID");
                evaluations.add(new Evaluation(evaluationID, score, date, comment, studentID, courseID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    public void deleteEvaluation(int evaluationID) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "DELETE FROM DB2024_Evaluation WHERE EvaluationID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, evaluationID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
