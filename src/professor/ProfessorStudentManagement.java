package professor;

import professor.StartProfessor;
import main.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorStudentManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;

	public static StartProfessor start_professor_frame = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorStudentManagement frame = new ProfessorStudentManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorStudentManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(12, 6, 771, 492);
		contentPane.add(panel);
		panel.setLayout(null);

		title = new JLabel("학생 정보 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(325, 24, 189, 55);
		panel.add(title);

		searchTextField = new JTextField("학생명 or 학번 or 학과를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchStudents(selectedItem, searchText);
			}
		});
		btnSearch.setBounds(574, 97, 75, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_professor_frame = new StartProfessor();
				start_professor_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 85, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "학생명", "학번", "학과" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("학생명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		panel.add(table);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(50, 150, 700, 300);
		panel.add(pane);
	}

	private void searchStudents(String searchType, String searchText) {
		List<Student> students = new ArrayList<>();
		String query = "";

		switch (searchType) {
			case "학생명":
				query = "SELECT * FROM DB2024_Student WHERE Name LIKE ?";
				break;
			case "학번":
				query = "SELECT * FROM DB2024_Student WHERE StudentID = ?";
				break;
			case "학과":
				query = "SELECT * FROM DB2024_Student WHERE Department LIKE ?";
				break;
		}

		try (Connection connection = DatabaseConnection.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {

			if (searchType.equals("학번")) {
				statement.setInt(1, Integer.parseInt(searchText));
			} else {
				statement.setString(1, "%" + searchText + "%");
			}

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int studentID = resultSet.getInt("StudentID");
				String name = resultSet.getString("Name");
				String department = resultSet.getString("Department");
				String email = resultSet.getString("Email");
				String contact = resultSet.getString("Contact");

				students.add(new Student(studentID, name, department, email, contact));
			}

			displayStudents(students);

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "학생 정보 조회 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void displayStudents(List<Student> students) {
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);

		model.addColumn("StudentID");
		model.addColumn("Name");
		model.addColumn("Department");
		model.addColumn("Email");
		model.addColumn("Contact");

		for (Student student : students) {
			model.addRow(new Object[]{student.getStudentID(), student.getName(), student.getDepartment(), student.getEmail(), student.getContact()});
		}
	}
}
