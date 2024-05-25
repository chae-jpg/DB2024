package student;

import main.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public List<Professor> searchProfessors(String searchType, String searchText) {
        List<Professor> professors = new ArrayList<>();
        String query = "";

        switch (searchType) {
            case "교수명":
                query = "SELECT * FROM DB2024_Professor WHERE Name LIKE ?";
                break;
            case "교수id":
                query = "SELECT * FROM DB2024_Professor WHERE ProfessorID = ?";
                break;
            case "학과":
                query = "SELECT * FROM DB2024_Professor WHERE Department LIKE ?";
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
                int professorID = resultSet.getInt("ProfessorID");
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
