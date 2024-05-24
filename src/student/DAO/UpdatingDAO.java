package student.DAO;

import student.domain.StudentDomain;

import java.sql.*;


public class UpdatingDAO {

    private Connection connection;
    //생성자: db 연결
    public UpdatingDAO(){
        try {
            // JDBC 드라이버 로드
            Class.forName("your.jdbc.Driver");
            // 데이터베이스 연결 정보 설정
            String url = "jdbc:your_database_url";
            String username = "your_username";
            String password = "your_password";
            // 데이터베이스 연결
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // 학생 데이터 베이스 수정
    public Long insertStudent(StudentDomain student) throws Exception {
        Long id = null;
        connection.setAutoCommit(false); // 트랜잭션 시작
        try {
            String sql = "INSERT INTO students (studentID, name, department, email, contact, pw) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, student.getStudentID());
            statement.setString(2, student.getName());
            statement.setString(3,student.getDepartment());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getContact());
            statement.setString(6, student.getPw());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) { //예외: 영향을 받은 행이 없다면, 학생 추가 실패.
                throw new Exception("학생 추가에 실패했습니다.");
            }
            // getGeneratedKeys() 메서드: 자동으로 생성된 키 값 얻기.
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback(); // 예외 발생 시 롤백.
            throw e; // 예외를 상위 메서드로 던짐.
        }finally {
            connection.setAutoCommit(true); // 트랜잭션 종료
            connection.close(); // 커넥션 닫기
        }
        return id;

    }


    // 학생 데이터 베이스 삭제
    public void deleteStudent(Long id) throws Exception{
        try {
            connection.setAutoCommit(false); // 트랜잭션 시작
            // 학생 id 존재 여부 확인.
            String query = "SELECT COUNT(*) FROM students WHERE StudentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new Exception("해당 학생이 존재하지 않습니다.");
            }
            //학생 id 존재한다면, 학생 행 삭제.
            String sql = "DELETE FROM students WHERE StudentID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("학생 삭제에 실패했습니다.");
            }
            connection.commit(); // 트랜잭션 커밋
        }catch (Exception e){
            connection.rollback(); // 예외 발생 시 롤백
            throw e;
        } finally {
            connection.setAutoCommit(true); // 트랜잭션 종료
            connection.close(); // 커넥션 닫기
        }
    }
}
