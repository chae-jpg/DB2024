package worker.Student;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Student WHERE Name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> getStudentsById(int id) {
        List<Student> students = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Student WHERE StudentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> getStudentsByDepartment(String department) {
        List<Student> students = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Student WHERE Department LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + department + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(mapStudent(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public void addStudent(Student student) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO DB2024_Student (StudentID, Name, Department, Email, Contact, Password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getStudentID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getDepartment());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getContact());
            preparedStatement.setString(6, student.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE DB2024_Student SET Name = ?, Department = ?, Email = ?, Contact = ?, Password = ? WHERE StudentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getDepartment());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getContact());
            preparedStatement.setString(5, student.getPassword());
            preparedStatement.setInt(6, student.getStudentID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "DELETE FROM DB2024_Student WHERE StudentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Student mapStudent(ResultSet resultSet) throws Exception {
        int studentID = resultSet.getInt("StudentID");
        String name = resultSet.getString("Name");
        String department = resultSet.getString("Department");
        String email = resultSet.getString("Email");
        String contact = resultSet.getString("Contact");
        String password = resultSet.getString("Password");
        return new Student(studentID, name, department, email, contact, password);
    }
}
