package worker;

import main.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GradeDAO {

    public Grade getGradeById(int gradeID) {
        String sql = "SELECT * FROM DB2024_Grade WHERE GradeID=?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, gradeID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                int courseID = resultSet.getInt("CourseID");
                String grade = resultSet.getString("Grade");
                String semester = resultSet.getString("Semester");
                boolean repetition = resultSet.getBoolean("Repetition");
                resultSet.close();
                statement.close();
                connection.close();
                return new Grade(gradeID, studentID, courseID, grade, semester, repetition);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateGrade(Grade grade) {
        String sql = "UPDATE DB2024_Grade SET Grade=?, Semester=? WHERE GradeID=?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, grade.getGrade());
            statement.setString(2, grade.getSemester());
            statement.setInt(3, grade.getGradeID());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
