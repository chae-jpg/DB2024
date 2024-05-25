package student;

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

public class GradeViewStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private JLabel averageLabel;

	public static StudentStart start_frame = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GradeViewStudent frame = new GradeViewStudent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GradeViewStudent() {
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

		title = new JLabel("성적 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 127, 55);
		panel.add(title);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				searchGrade(selectedItem);
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new StudentStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 77, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "전체", "2024-2", "2024-1", "2023-2", "2023-1", "2022-2", "2022-1", "2021-2", "2021-1",
				"2020-2", "2020-1", "2019-2", "2019-1", "2018-2", "2018-1" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("전체");
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		panel.add(table);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(50, 150, 700, 300);
		panel.add(pane);

		averageLabel = new JLabel("평균 학점 : ");
		averageLabel.setBounds(50, 460, 200, 25);
		panel.add(averageLabel);
	}

	private void searchGrade(String selectedItem) {
		String id = Student.getInstance().getStudentId();
		List<String> grades = new ArrayList<>();
		List<Integer> credits = new ArrayList<>();

		String sql;
		if (selectedItem.equalsIgnoreCase("전체")) {
			sql = "SELECT c.CourseName, g.Grade, g.Semester, c.Credit " +
					"FROM DB2024_Grade g " +
					"JOIN DB2024_Course c ON g.CourseID = c.CourseID " +
					"WHERE g.StudentId = ?";
		} else {
			sql = "SELECT c.CourseName, g.Grade, g.Semester, c.Credit " +
					"FROM DB2024_Grade g " +
					"JOIN DB2024_Course c ON g.CourseID = c.CourseID " +
					"WHERE g.StudentId = ? AND g.Semester = ?";
		}

		try (Connection connection = DatabaseConnection.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, id);
			if (!selectedItem.equalsIgnoreCase("전체")) {
				statement.setString(2, selectedItem);
			}

			try (ResultSet resultSet = statement.executeQuery()) {
				DefaultTableModel model = new DefaultTableModel();
				table.setModel(model);

				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					model.addColumn(resultSet.getMetaData().getColumnName(i));
				}

				while (resultSet.next()) {
					Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
					for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
						row[i - 1] = resultSet.getObject(i);
					}
					model.addRow(row);
					grades.add(resultSet.getString("Grade"));
					credits.add(resultSet.getInt("Credit"));
				}
			}

			float average = calculateAverage(grades, credits);
			averageLabel.setText(String.format("%s - 평균 학점 : %.2f", selectedItem, average));

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "성적 조회 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	private float convertGradeToScore(String grade) {
		switch (grade) {
			case "A+":
				return 4.3f;
			case "A0":
			case "A":
				return 4.0f;
			case "A-":
				return 3.7f;
			case "B+":
				return 3.3f;
			case "B0":
			case "B":
				return 3.0f;
			case "B-":
				return 2.7f;
			case "C+":
				return 2.3f;
			case "C0":
			case "C":
				return 2.0f;
			case "C-":
				return 1.7f;
			case "D+":
				return 1.3f;
			case "D0":
			case "D":
				return 1.0f;
			case "F":
			case "P":
			case "NP":
				return 0.0f;
			default:
				throw new IllegalArgumentException("Invalid grade: " + grade);
		}
	}

	private float calculateAverage(List<String> grades, List<Integer> credits) {
		float sum_score = 0;
		int sum_credits = 0;

		for (int i = 0; i < grades.size(); i++) {
			float score = convertGradeToScore(grades.get(i));
			if (score > 0) {
				sum_score += score * credits.get(i);
				sum_credits += credits.get(i);
			}
		}

		return sum_credits > 0 ? sum_score / sum_credits : 0;
	}
}
