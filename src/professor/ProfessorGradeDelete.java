package professor;

/*
 * 교수 - 성적 삭제: 교수는 자신이 맡은 강의만 삭제가 가능하다. 
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ProfessorGradeDelete extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JTextField searchTextField;
	private JButton btnSearch;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private JButton btnOkay;
	private JTextField studentIdField;
	private JTextField courseIdField;
	private JTextField gradeField;
	private JTextField semesterField;

	private static StartProfessor start_professor_frame = null;

	private static final String url = "jdbc:mysql://localhost:3306/DB2024team04";
	private static final String username = "";
	private static final String password = "";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorGradeDelete frame = new ProfessorGradeDelete();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorGradeDelete() {

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

		title = new JLabel("성적 삭제");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 101, 55);
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

		homeButton = new JButton("home"); // StartProfessor로 가는 홈 버튼 생성했다.
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
		comboBox.setSelectedItem("학번"); // 기본 선택을 학번으로 설정했다.
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 150);
		panel.add(table);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(50, 150, 700, 150);
		panel.add(pane);

		table.addMouseListener(new MouseAdapter() { // table에서 MouseListener를 생성해 선택한 값을 가져올 수 있게 하였다.
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					studentIdField.setText(table.getValueAt(selectedRow, 0).toString());
					courseIdField.setText(table.getValueAt(selectedRow, 1).toString());
					gradeField.setText(table.getValueAt(selectedRow, 2).toString());
					semesterField.setText(table.getValueAt(selectedRow, 3).toString());
				}
			}
		});

		btnOkay = new JButton("확인"); // 확인을 누르면 deleteGrade 메서드가 실행된다.
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteGrade();
			}
		});
		btnOkay.setBounds(675, 457, 75, 29);
		panel.add(btnOkay);

		JLabel studentIdLabel = new JLabel("학번:");
		studentIdLabel.setBounds(50, 320, 100, 25);
		panel.add(studentIdLabel);
		studentIdField = new JTextField();
		studentIdField.setBounds(150, 320, 150, 25);
		studentIdField.setEditable(false); // 학번 수정 안되게 false로 설정해주었다.
		panel.add(studentIdField);

		JLabel courseIdLabel = new JLabel("강의ID:");
		courseIdLabel.setBounds(50, 360, 100, 25);
		panel.add(courseIdLabel);
		courseIdField = new JTextField();
		courseIdField.setBounds(150, 360, 150, 25);
		courseIdField.setEditable(false); // 강의ID 수정 안되게 false로 설정해주었다.
		panel.add(courseIdField);

		JLabel gradeLabel = new JLabel("성적:");
		gradeLabel.setBounds(50, 400, 100, 25);
		panel.add(gradeLabel);
		gradeField = new JTextField();
		gradeField.setBounds(150, 400, 150, 25);
		panel.add(gradeField);

		JLabel semesterLabel = new JLabel("학기:");
		semesterLabel.setBounds(350, 320, 100, 25);
		panel.add(semesterLabel);
		semesterField = new JTextField();
		semesterField.setBounds(450, 320, 150, 25);
		panel.add(semesterField);

		JLabel repetitionLabel = new JLabel("재수강:");
		repetitionLabel.setBounds(350, 360, 100, 25);
	}

	private void searchGrade(String selectedItem, String searchText) {
		// 자신이 맡은 강의만 삭제 가능하게 위해 검색에 조건을 두었다.

		String id = Professor.getInstance().getId();
		String sql = "";

		if (selectedItem.equalsIgnoreCase("학번")) { // 학번으로 입력을 받아왔을 때와 강의 id로 입력을 받아왔을 때 쿼리를 다르게 작성해주었다.
			sql = "SELECT g.StudentID, g.CourseID, g.Grade, g.Semester, g.Repetition "
					+ "FROM DB2024_Grade g, DB2024_Course c "
					+ "WHERE g.CourseID = c.CourseID AND c.ProfessorID = ? AND g.StudentID = ?";

		} else if (selectedItem.equalsIgnoreCase("강의id")) {
			sql = "SELECT g.StudentID, g.CourseID, g.Grade, g.Semester, g.Repetition "
					+ "FROM DB2024_Grade g, DB2024_Course c "
					+ "WHERE g.CourseID = c.CourseID AND c.ProfessorID = ? AND g.CourseID = ?";
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, searchText);

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

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void deleteGrade() {

		String id = Professor.getInstance().getId();

		Connection connection = null;

		String sql = "DELETE FROM DB2024_Grade WHERE StudentID = ? AND CourseID = ? AND Grade = ? ";

		// Grade도 조건으로 두어서 재수강 같은 학번, 강의id가 같은 데이터를 구별해서 삭제할 수 있게 했다.

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false); // 트랜잭션을 위해 AutoCommit false로 설정했다.

			PreparedStatement statement = connection.prepareStatement(sql);

			int studentID = Integer.parseInt(studentIdField.getText());
			int courseID = Integer.parseInt(courseIdField.getText());
			statement.setInt(1, studentID);
			statement.setInt(2, courseID);
			statement.setString(3, gradeField.getText());

			System.out.println(studentIdField.getText());
			System.out.println(courseIdField.getText());
			System.out.println(gradeField.getText());

			int rowsDeleted = statement.executeUpdate(); // 영향을 받은 갯수를 통해 삭제 여부를 확인할 수 있다.

			if (rowsDeleted > 0) { // 0보다 크다면 삭제가 된 것이다.
				System.out.println(rowsDeleted);
				JOptionPane.showMessageDialog(this, "성적이 삭제되었습니다.");
				connection.commit();
			} else { // 0이하라면 삭제된 행이 존재하지 않다.
				JOptionPane.showMessageDialog(this, "성적의 삭제에 실패했습니다.");
				connection.rollback(); // 성적 삭제에 실패하면 rollback을 통해 트랜잭션을 취소해준다.
			}
			statement.close();

		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback(); // 예외 발생 시 롤백해서 트랜잭션을 취소해준다.
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
					System.out.println("connection close하기");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
