package professor;

import main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    /**
     * 주어진 강의명(courseName)을 수강하는 학생 목록을 조회합니다.
     *
     * @param courseName 강의명
     * @return 학생 목록
     */
    public List<Student> getStudentsByCourseName(String courseName) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.StudentID, s.Name, s.Department, s.Email, s.Contact " +
                "FROM DB2024_Student s " +
                "JOIN DB2024_Register r ON s.StudentID = r.StudentID " +
                "JOIN DB2024_Course c ON r.CourseID = c.CourseID " +
                "WHERE c.CourseName = ? AND c.ProfessorID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // 강의명과 교수ID를 설정하여 쿼리를 실행합니다.
            statement.setString(1, courseName);
            statement.setInt(2, Integer.parseInt(Professor.getInstance().getId()));
            ResultSet resultSet = statement.executeQuery();

            // 결과를 순회하며 학생 정보를 리스트에 추가합니다.
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

    /**
     * 주어진 학수번호(courseID)를 수강하는 학생 목록을 조회합니다.
     *
     * @param courseID 학수번호
     * @return 학생 목록
     */
    public List<Student> getStudentsByCourseID(int courseID) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.StudentID, s.Name, s.Department, s.Email, s.Contact " +
                "FROM DB2024_Student s " +
                "JOIN DB2024_Register r ON s.StudentID = r.StudentID " +
                "JOIN DB2024_Course c ON r.CourseID = c.CourseID " +
                "WHERE c.CourseID = ? AND c.ProfessorID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // 학수번호와 교수ID를 설정하여 쿼리를 실행합니다.
            statement.setInt(1, courseID);
            statement.setInt(2, Integer.parseInt(Professor.getInstance().getId()));
            ResultSet resultSet = statement.executeQuery();

            // 결과를 순회하며 학생 정보를 리스트에 추가합니다.
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
