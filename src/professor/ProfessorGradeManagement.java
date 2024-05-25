package professor;
import main.DatabaseConnection;
import professor.ProfessorGradeDelete;
import professor.ProfessorGradeEdit;
import professor.ProfessorGradeRegister;
import professor.StartProfessor;
/* 교수님은 자기가 담당하는 과목 성적의 입력, 수정, 삭제 가능하다. 
 * 단, 조회는 다 가능하게 구현해주었다.  */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProfessorGradeManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox comboBox;
	private JTable table;
	private JButton btnRegister;
	private JButton btnModify;
	private JButton btnDelete;

	public static StartProfessor start_professor_frame = null;
	public static ProfessorGradeRegister grade_register_frame = null;
	public static ProfessorGradeEdit grade_edit_frame = null;
	public static ProfessorGradeDelete grade_delete_frame = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorGradeManagement frame = new ProfessorGradeManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorGradeManagement() {
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

		title = new JLabel("성적 관리");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 101, 55);
		panel.add(title);

		searchTextField = new JTextField("학번 or 강의id를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // 검색을 통해 모든 학생의 성적이 조회 가능하다.
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				System.out.println(selectedItem);
				System.out.println(searchText);
				searchGrade(selectedItem, searchText);

			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
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
		homeButton.setBounds(6, 6, 63, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "학번", "강의id" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("학번"); // 기본 선택을 학번으로 설정해주었다.
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		panel.add(table);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(50, 150, 700, 300);
		panel.add(pane);

		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 등록 버튼 눌렀을 때
				grade_register_frame = new ProfessorGradeRegister();
				grade_register_frame.setVisible(true);
				setVisible(false);
			}
		});
		btnRegister.setBounds(537, 457, 75, 29);
		panel.add(btnRegister);

		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 수정 버튼 눌렀을 때
				grade_edit_frame = new ProfessorGradeEdit();
				grade_edit_frame.setVisible(true);
				setVisible(false);
			}
		});
		btnModify.setBounds(605, 457, 75, 29);
		panel.add(btnModify);

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 삭제 버튼 눌렀을 때
				grade_delete_frame = new ProfessorGradeDelete();
				grade_delete_frame.setVisible(true);
				setVisible(false);

			}
		});
		btnDelete.setBounds(675, 457, 75, 29);
		panel.add(btnDelete);

	}

	private void searchGrade(String selectedItem, String searchText) {

		String sql = "";

		if (selectedItem.equalsIgnoreCase("학번")) {
			sql = "SELECT g.CourseID, c.CourseName, g.StudentID, s.Name, g.Grade, g.Semester, g.Repetition " // 학생명과
					// 강의명도
					// 나오게
					// 수정했습니다.
					+ "FROM DB2024_Grade g, DB2024_Student s, DB2024_Course c "
					+ "WHERE g.StudentID = s.StudentID AND g.CourseID = c.CourseID AND g.StudentID = ?";
		} else if (selectedItem.equalsIgnoreCase("강의id")) {
			sql = "SELECT g.CourseID, c.CourseName, g.StudentID, s.Name, g.Grade, g.Semester, g.Repetition "// 학생명과 강의명도
					// 나오게
					// 수정했습니다.
					+ "FROM DB2024_Grade g, DB2024_Student s, DB2024_Course c "
					+ "WHERE g.StudentID = s.StudentID AND g.CourseID = c.CourseID AND g.CourseID = ?";
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DatabaseConnection.getConnection(); // 변경된 부분

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, searchText);
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
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
