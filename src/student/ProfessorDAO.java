package student;

import main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public List<Professor> searchProfessors(String searchType, String searchText) {
        List<Professor> professors = new ArrayList<>();
        String query = "";

        switch (searchType) {//텍스트필드의 값의 속성에따라 달라지는 쿼리
            case "교수명":
                query = "SELECT * FROM DB2024_Professor WHERE Name LIKE ?";//텍스트 필드의 정보가 이름일때
                break;
            case "교수id":
                query = "SELECT * FROM DB2024_Professor WHERE ProfessorID = ?";//텍스트 필드의 정보가 교수id일때
                break;
            case "학과":
                query = "SELECT * FROM DB2024_Professor WHERE Department LIKE ?";//텍스트 필드의 정보가 학과일때
                break;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (searchType.equals("교수id")) {
                statement.setInt(1, Integer.parseInt(searchText));
            } else {
                statement.setString(1, "%" + searchText + "%");
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int professorID = resultSet.getInt("ProfessorID");//쿼리 결과를 저장한다.
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");

                professors.add(new Professor(professorID, name, department, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professors;
    }
}
