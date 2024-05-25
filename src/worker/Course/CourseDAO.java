package worker.Course;

import main.DatabaseConnection;
import worker.Student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Student> getStudentsByCourseName(String courseName) { // 강의명으로 수강자 조회
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

    public List<Student> getStudentsByCourseID(int courseID) { // 학수번호로 수강자 조회
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

    public List<Course> getCoursesByStudentName(String studentName) { // 학생 이름으로 수강 과목 조회
        List<Course> courses = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT c.* FROM DB2024_Course c " +
                    "JOIN DB2024_Register r ON c.CourseID = r.CourseID " +
                    "JOIN DB2024_Student s ON r.StudentID = s.StudentID " +
                    "WHERE s.Name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + studentName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Course> getCoursesByStudentID(int studentID) { // 학번으로 수강 과목 조회
        List<Course> courses = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT c.* FROM DB2024_Course c " +
                    "JOIN DB2024_Register r ON c.CourseID = r.CourseID " +
                    "JOIN DB2024_Student s ON r.StudentID = s.StudentID " +
                    "WHERE s.StudentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Course> getCoursesByName(String courseName) { // 강의명으로 강의 조회
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

    public List<Course> getCoursesByID(int courseID) { // 학수 번호로 강의 조회
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

    public List<Course> getCoursesByProfessor(String professorName) { // 교수 명으로 강의 조회
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

    public void addCourse(Course course) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO DB2024_Course (CourseID, CourseName, Classroom, Credit, Semester, Day, Time, ProfessorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, course.getCourseID());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getClassroom());
            preparedStatement.setInt(4, course.getCredit());
            preparedStatement.setString(5, course.getSemester());
            preparedStatement.setString(6, course.getDay());
            preparedStatement.setString(7, course.getTime());
            preparedStatement.setInt(8, course.getProfessorID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(Course course) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE DB2024_Course SET CourseName = ?, Classroom = ?, Credit = ?, Semester = ?, Day = ?, Time = ?, ProfessorID = ? WHERE CourseID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getClassroom());
            preparedStatement.setInt(3, course.getCredit());
            preparedStatement.setString(4, course.getSemester());
            preparedStatement.setString(5, course.getDay());
            preparedStatement.setString(6, course.getTime());
            preparedStatement.setInt(7, course.getProfessorID());
            preparedStatement.setInt(8, course.getCourseID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(int courseID) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "DELETE FROM DB2024_Course WHERE CourseID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, courseID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Course";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(mapCourse(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }
    public List<Student> getAllStudentsByProfessor(String professorName) {
        List<Student> students = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT s.StudentID, s.Name, s.Department, s.Email, s.Contact " +
                    "FROM DB2024_Student s " +
                    "JOIN DB2024_Register r ON s.StudentID = r.StudentID " +
                    "JOIN DB2024_Course c ON r.CourseID = c.CourseID " +
                    "JOIN DB2024_Professor p ON c.ProfessorID = p.ProfessorID " +
                    "WHERE p.Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, professorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                String email = resultSet.getString("Email");
                String contact = resultSet.getString("Contact");
                students.add(new Student(studentID, name, department, email, contact, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
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
