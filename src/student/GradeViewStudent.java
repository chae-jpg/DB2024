package student;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import worker.GradeRegister;

import javax.swing.JTable;
import javax.swing.JComboBox;

/*
 * 학생은 자신의 성적만 조회 가능하다. 
 * 이때 학기 별 성적, 총 성적 확인 가능해야 한다. 
 * 학기선택 -> 2024-2~2018-1까지 성적 조회 가능하다. 
 * 전체선택 -> 총 성적 조회 가능 
 * 
 */

public class GradeViewStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JButton homeButton;
	private JComboBox comboBox;
	private JTable table;
	private JLabel averageLabel;

	public static StudentStart start_frame = null;
	public static GradeRegister grade_register_frame = null;

	private static final String url = "jdbc:mysql://localhost:3306/DB2024team04";
	private static final String username = "";
	private static final String password = "";

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
				System.out.println(selectedItem);
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
		comboBox.setSelectedItem("전체"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		panel.add(table);

		averageLabel = new JLabel("평균 학점 : ");
		averageLabel.setBounds(50, 460, 200, 25);
		panel.add(averageLabel);

	}

	private void searchGrade(String selectedItem) {

		String id = Student.getInstance().getStudentId();

		List<String> grades = new ArrayList<>(); // 성적이 들어갈 ArrayList
		List<Integer> credits = new ArrayList<>(); // 학점 수가 들어갈 ArrayList

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);

			String sql;

			if (selectedItem.equalsIgnoreCase("전체")) { // 전체라면 모든 학기 평점 다 보여주기
				sql = "SELECT c.CourseName, g.Grade, g.Semester, c.Credit " // 강의 테이블의 학점 이름에 따라 hours 바꿔야함.
						+ "FROM DB2024_Grade g, DB2024_Course c "
						+ "WHERE g.CourseID = c.CourseID AND g.StudentId = ? ";
			} else { // 전체가 아니면 선택한 학기만 보여주기
				sql = "SELECT c.CourseName, g.Grade, g.Semester, c.Credit " + "FROM DB2024_Grade g, DB2024_Course c "
						+ "WHERE g.CourseID = c.CourseID AND g.StudentId = ? AND g.Semester = ? ";
			}

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, id);
			if (!selectedItem.equalsIgnoreCase("전체")) { // 전체 학기 평점 조회하는게 아니라면 학기 설정 해주기
				statement.setString(2, selectedItem);
			}

			ResultSet resultSet = statement.executeQuery();

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
				grades.add(resultSet.getString("Grade")); // grade ArrayList에 성적 추가 ex [A+, A0]
				credits.add(resultSet.getInt("Credit")); // hours ArrayList에 학점 수 추가 [3,3]
			}

			List<Float> scores = new ArrayList<>(); // 학점 변환을 위한 scores ArrayList
			for (String grade : grades) {
				scores.add(convertGradeToScore(grade)); // 학점 변환 로직 수행한다. ex) A+->4.3
			}

			float average = calculateAverage(scores, credits); // 평점을 계산을 계산해준다.

			System.out.println("Grades: " + grades);
			System.out.println("Hours: " + credits);
			System.out.println("Average grade: " + average);

			averageLabel.setText(String.format("%s - 평균 학점 : %.2f", selectedItem, average)); // 평균 학점을 라벨을 통해 나타내주었다.

			resultSet.close();
			statement.close();
			connection.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private float convertGradeToScore(String grade) {
		switch (grade) {
		case "A+":
			return 4.3f;
		case "A0":
			return 4.0f;
		case "A":
			return 4.0f;
		case "A-":
			return 3.7f;
		case "B+":
			return 3.3f;
		case "B0":
			return 3.0f;
		case "B":
			return 3.0f;
		case "B-":
			return 2.7f;
		case "C+":
			return 2.3f;
		case "C0":
			return 2.0f;
		case "C":
			return 2.0f;
		case "C-":
			return 1.7f;
		case "D+":
			return 1.3f;
		case "D0":
			return 1.0f;
		case "D":
			return 1.0f;
		case "F":
		case "P":
		case "NP":
			return 0.0f;
		default:
			throw new IllegalArgumentException();
		}
	}

	private float calculateAverage(List<Float> scores, List<Integer> credits) { // 평점 계산해주는 메서드이다.

		float sum_score = 0;
		int sum_credits = 0;

		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) > 0) { // F, P와 NP는 제외하기 위한 조건이다.
				sum_score += scores.get(i) * credits.get(i); // 학점 수 * 성적의 합
				sum_credits += credits.get(i); // 총 전체 학점 수
			}
		}

		float result = sum_score / sum_credits;
		return result;
	}

}
