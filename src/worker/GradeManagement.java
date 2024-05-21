package worker;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnRegister;
	private JButton btnModify;
	private JButton btnDelete;

	public static WorkerStart start_frame = null;
	public static GradeRegister grade_register_frame = null;
	public static GradeEdit grade_edit_frame = null;

	private static final String url = "jdbc:mysql://localhost:3306/DB2024";
	private static final String username = "your_username";
	private static final String password = "your_password";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GradeManagement frame = new GradeManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GradeManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
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
		title.setBounds(317, 21, 124, 55);
		panel.add(title);

		searchTextField = new JTextField("학번 or 강의id를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchGrade(selectedItem, searchText);
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new WorkerStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 75, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "학번", "강의id" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("학번"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "GradeID", "StudentID", "CourseID", "Grade", "Semester", "Repetition" });
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 150, 700, 300);
		panel.add(scrollPane);

		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grade_register_frame = new GradeRegister();
				grade_register_frame.setVisible(true);
				setVisible(false);
			}
		});
		btnRegister.setBounds(537, 457, 75, 29);
		panel.add(btnRegister);

		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int gradeID = (int) tableModel.getValueAt(selectedRow, 0);
					Grade grade = getGradeById(gradeID);
					grade_edit_frame = new GradeEdit(grade);
					grade_edit_frame.setVisible(true);
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(panel, "수정할 성적을 선택하세요.", "수정 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModify.setBounds(605, 457, 75, 29);
		panel.add(btnModify);

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int gradeID = (int) tableModel.getValueAt(selectedRow, 0);
					deleteGrade(gradeID);
					searchGrade("", ""); // 모든 성적을 다시 로드
				} else {
					JOptionPane.showMessageDialog(panel, "삭제할 성적을 선택하세요.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.setBounds(675, 457, 75, 29);
		panel.add(btnDelete);
	}

	private void searchGrade(String selectedItem, String searchText) {
		String sql = "";
		if (selectedItem.equals("학번")) {
			sql = "SELECT * FROM DB2024_Grade WHERE StudentID=?";
		} else if (selectedItem.equals("강의id")) {
			sql = "SELECT * FROM DB2024_Grade WHERE CourseID=?";
		}

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, searchText);
			ResultSet resultSet = statement.executeQuery();

			tableModel.setRowCount(0); // 기존 행 제거

			while (resultSet.next()) {
				Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					row[i - 1] = resultSet.getObject(i);
				}
				tableModel.addRow(row);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Grade getGradeById(int gradeID) {
		String sql = "SELECT * FROM DB2024_Grade WHERE GradeID=?";
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, gradeID);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int studentID = resultSet.getInt("StudentID");
				int courseID = resultSet.getInt("CourseID");
				String grade = resultSet.getString("Grade");
				String semester = resultSet.getString("Semester");
				boolean repetition = resultSet.getBoolean("Repetition");
				resultSet.close();
				statement.close();
				connection.close();
				return new Grade(gradeID, studentID, courseID, grade, semester, repetition);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void deleteGrade(int gradeID) {
		String sql = "DELETE FROM DB2024_Grade WHERE GradeID=?";
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, gradeID);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
