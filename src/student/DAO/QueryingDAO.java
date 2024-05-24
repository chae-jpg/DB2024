package student.DAO;

import student.domain.StudentDomain;

import java.sql.Connection;
import java.sql.DriverManager;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryingDAO {

    private Connection connection;

    // 생성자로 db 연결
    public QueryingDAO() {
        try {
            // JDBC 드라이버 로드
            Class.forName("your.jdbc.Driver");
            // 데이터베이스 연결 정보 설정
            String url = "jdbc:your_database_url";
            String username = "DB2024Team04";
            String password = "DB2024Team04";
            // 데이터베이스 연결
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



    // 학생 데이터베이스 조회
    public List<StudentDomain> findStudent(){
        List<StudentDomain> studentList = new ArrayList<>();
        String sql = "SELECT studentID, name, department, email, contact, pw FROM students";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                studentList.add(mapRow(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }



    private StudentDomain mapRow(ResultSet resultSet) throws SQLException {
        return new StudentDomain(
                resultSet.getLong("studentID"),
                resultSet.getString("name"),
                resultSet.getString("department"),
                resultSet.getString("email"),
                resultSet.getString("contact"),
                resultSet.getString("pw")
        );
    }
}


