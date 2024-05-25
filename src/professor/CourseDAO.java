package professor;

import main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Student> getStudentsByCourseName(String courseName) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.StudentID, s.Name, s.Department, s.Email, s.Contact " +
                "FROM DB2024_Student s " +
                "JOIN DB2024_Register r ON s.StudentID = r.StudentID " +
                "JOIN DB2024_Course c ON r.CourseID = c.CourseID " +
                "WHERE c.CourseName = ? AND c.ProfessorID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, courseName);
            statement.setInt(2, Integer.parseInt(Professor.getInstance().getId()));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                String email = resultSet.getString("Email");
                String contact = resultSet.getString("Contact");

                students.add(new Student(studentID, name, department, email, contact));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public List<Student> getStudentsByCourseID(int courseID) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.StudentID, s.Name, s.Department, s.Email, s.Contact " +
                "FROM DB2024_Student s " +
                "JOIN DB2024_Register r ON s.StudentID = r.StudentID " +
                "JOIN DB2024_Course c ON r.CourseID = c.CourseID " +
                "WHERE c.CourseID = ? AND c.ProfessorID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, courseID);
            statement.setInt(2, Integer.parseInt(Professor.getInstance().getId()));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                String email = resultSet.getString("Email");
                String contact = resultSet.getString("Contact");

                students.add(new Student(studentID, name, department, email, contact));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}
