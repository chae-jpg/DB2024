package worker.Professor;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public List<Professor> getProfessorsByName(String name) {
        List<Professor> professors = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Professor WHERE Name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                professors.add(mapProfessor(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professors;
    }

    public List<Professor> getProfessorsById(int id) {
        List<Professor> professors = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Professor WHERE ProfessorID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                professors.add(mapProfessor(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professors;
    }

    public List<Professor> getProfessorsByDepartment(String department) {
        List<Professor> professors = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM DB2024_Professor WHERE Department LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + department + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                professors.add(mapProfessor(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professors;
    }

    public void addProfessor(Professor professor) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO DB2024_Professor (ProfessorID, Name, Department, Email, Phone, Password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, professor.getProfessorID());
            preparedStatement.setString(2, professor.getName());
            preparedStatement.setString(3, professor.getDepartment());
            preparedStatement.setString(4, professor.getEmail());
            preparedStatement.setString(5, professor.getPhone());
            preparedStatement.setString(6, professor.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProfessor(Professor professor) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE DB2024_Professor SET Name = ?, Department = ?, Email = ?, Phone = ?, Password = ? WHERE ProfessorID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setString(2, professor.getDepartment());
            preparedStatement.setString(3, professor.getEmail());
            preparedStatement.setString(4, professor.getPhone());
            preparedStatement.setString(5, professor.getPassword());
            preparedStatement.setInt(6, professor.getProfessorID());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProfessor(int id) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "DELETE FROM DB2024_Professor WHERE ProfessorID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Professor mapProfessor(ResultSet resultSet) throws Exception {
        int professorID = resultSet.getInt("ProfessorID");
        String name = resultSet.getString("Name");
        String department = resultSet.getString("Department");
        String email = resultSet.getString("Email");
        String phone = resultSet.getString("Phone");
        String password = resultSet.getString("Password");
        return new Professor(professorID, name, department, email, phone, password);
    }
}
