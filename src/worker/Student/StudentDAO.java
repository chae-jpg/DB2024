package worker.Student;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getStudentsByName(String name) { //주어진 이름에 대한 학생들의 목록을 검색
        List<Student> students = new ArrayList<>(); //검색 결과를 저장할 객체 students 생성.
        Connection connection = DatabaseConnection.getConnection(); //디비 연결 설정을 위해 Connection 객체 가져오기.
        try {
            String query = "SELECT * FROM DB2024_Student WHERE Name LIKE ?"; //테이블에서 이름이 주어진 이름과 부분적으로 일치하는 학생들의 정보를 선택
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%"); // 첫 번째 파라미터에 주어진 이름을 포함하는 문자열 설정
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(mapStudent(resultSet)); //각 행의 데이터를 Student 객체로 변환하고, 이를 students 리스트에 추가
            }
        } catch (Exception e) { // 예외 발생 시 빈 students 리스트 출력.
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> getStudentsById(int id) {//주어진 학생 ID에 해당하는 학생을 검색
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

    public List<Student> getStudentsByDepartment(String department) {// 주어진 학과에 속한 학생들의 목록을 검색
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

    public void addStudent(Student student) {//새 학생을 데이터베이스에 추가
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

    public void updateStudent(Student student) { // 주어진 학생의 정보를 업데이트
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

    public void deleteStudent(int id) {// 주어진 학생 ID에 해당하는 학생 정보를 삭제
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

    private Student mapStudent(ResultSet resultSet) throws Exception {//데이터베이스 결과 집합에서 학생 객체로의 매핑을 수행
        int studentID = resultSet.getInt("StudentID");
        String name = resultSet.getString("Name");
        String department = resultSet.getString("Department");
        String email = resultSet.getString("Email");
        String contact = resultSet.getString("Contact");
        String password = resultSet.getString("Password");
        return new Student(studentID, name, department, email, contact, password);
    }
}
