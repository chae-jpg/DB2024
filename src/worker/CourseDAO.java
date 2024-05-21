package worker;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Student> getStudentsByCourseName(String courseName) {
        List<Student> students = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT s.StudentID, s.Name, s.Department, s.Email, s.Contact, s.Password " +
                    "FROM DB2024_Student s " +
                    "JOIN DB2024_Register r ON s.StudentID = r.StudentID " +
                    "JOIN DB2024_Course c ON r.CourseID = c.CourseID " +
                    "WHERE c.CourseName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, courseName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                String email = resultSet.getString("Email");
                String contact = resultSet.getString("Contact");
                String password = resultSet.getString("Password");
                students.add(new Student(studentID, name, department, email, contact, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> getStudentsByCourseID(int courseID) {
        List<Student> students = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT s.StudentID, s.Name, s.Department, s.Email, s.Contact, s.Password " +
                    "FROM DB2024_Student s " +
                    "JOIN DB2024_Register r ON s.StudentID = r.StudentID " +
                    "JOIN DB2024_Course c ON r.CourseID = c.CourseID " +
                    "WHERE c.CourseID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                String email = resultSet.getString("Email");
                String contact = resultSet.getString("Contact");
                String password = resultSet.getString("Password");
                students.add(new Student(studentID, name, department, email, contact, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
    public List<Course> getCoursesByName(String courseName) {
        List<Course> courses = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Course WHERE CourseName LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + courseName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Course> getCoursesByID(int courseID) {
        List<Course> courses = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Course WHERE CourseID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Course> getCoursesByProfessor(String professorName) {
        List<Course> courses = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT c.* FROM DB2024_Course c " +
                    "JOIN DB2024_Professor p ON c.ProfessorID = p.ProfessorID " +
                    "WHERE p.Name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + professorName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    private Course mapCourse(ResultSet resultSet) throws Exception {
        int courseID = resultSet.getInt("CourseID");
        String courseName = resultSet.getString("CourseName");
        String classroom = resultSet.getString("Classroom");
        int credit = resultSet.getInt("Credit");
        String semester = resultSet.getString("Semester");
        String day = resultSet.getString("Day");
        String time = resultSet.getString("Time");
        int professorID = resultSet.getInt("ProfessorID");
        return new Course(courseID, courseName, classroom, credit, semester, day, time, professorID);
    }
}
