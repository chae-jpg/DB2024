package professor;

/*
 * 교수 - 성적 수정: 교수는 자신이 맡은 강의만 수정이 가능하다. 
 * 트랜잭션을 통해 원자성, 지속성, 일관성, 고립성을 유지하게 해준다. 
 */
import main.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ProfessorGradeEdit extends JFrame {

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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorGradeEdit frame = new ProfessorGradeEdit();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorGradeEdit() {

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

		title = new JLabel("성적 수정");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 139, 55);
		panel.add(title);

		searchTextField = new JTextField("학번 or 강의id를 입력하세요"); // 입력으로 학번 또는 강의 id를 받아온다.
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색"); // 자신의 강의만 검색이 가능하다.
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchGrade(selectedItem, searchText);
			}
		});
		btnSearch.setBounds(554, 97, 75, 29);
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
		homeButton.setBounds(6, 6, 91, 29);
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
					studentIdField.setText(table.getValueAt(selectedRow, 2).toString());
					courseIdField.setText(table.getValueAt(selectedRow, 0).toString());
					gradeField.setText(table.getValueAt(selectedRow, 4).toString());
					semesterField.setText(table.getValueAt(selectedRow, 5).toString());
				}
			}
		});

		btnOkay = new JButton("확인");// 확인을 누르면 updateGrade 메서드가 실행된다.
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGrade();
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
		semesterField.setEditable(false); // 수정 안되게 false로 설정해주었다.
		panel.add(semesterField);

		JLabel repetitionLabel = new JLabel("재수강:");
		repetitionLabel.setBounds(350, 360, 100, 25);
	}

	private void searchGrade(String selectedItem, String searchText) {

		String id = Professor.getInstance().getId();
		String sql = "";

		// 선택을 학번, 강의 id로 했을 때 바꾸었다.
		// 뷰를 사용해 간결하게 sql문을 나타내고, 필요한 부분만 보이게 했다.
		if (selectedItem.equalsIgnoreCase("학번")) {
			sql = "SELECT * FROM DB2024_Grade_View WHERE ProfessorID = ? AND StudentID = ?";
		} else if (selectedItem.equalsIgnoreCase("강의id")) {
			sql = "SELECT * FROM DB2024_Grade_View WHERE ProfessorID = ? AND CourseID = ?";
		}

		try {
			Connection connection = DatabaseConnection.getConnection();
			if (connection == null) {
				throw new SQLException("Database connection failed");
			}

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id); // ?의 첫 번재를 id로 채워준다.
			statement.setString(2, searchText); // ?의 두 번쨰를 searchText로 채워준다.

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

			// 성적을 찾을 때는 트랜잭션을 사용하지 않았다.

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateGrade() {

		Connection connection = null;

		try {

			connection = DatabaseConnection.getConnection();
			if (connection == null) {
				throw new SQLException("Database connection failed");
			}
			connection.setAutoCommit(false);// 트랜잭션을 위해 AutoCommit false로 설정했다.

			// 선택한 것 중에 GradeID가 가장 최신 거 가져왔다. (재수강 데이터를 존재할 수 있기 때문)
			String sql1 = "SELECT GradeID FROM DB2024_Grade WHERE StudentID = ? AND CourseID = ? ORDER BY GradeID DESC LIMIT 1";

			int gradeID = 0;

			PreparedStatement statement1 = connection.prepareStatement(sql1);
			statement1.setString(1, studentIdField.getText()); // 첫 번째 ?를 studentIdField에서 text로 가져와 채워주었다.
			statement1.setString(2, courseIdField.getText()); // 두 번째 ?를 courseIdField에서 text로 가져와 채워주었다.

			ResultSet resultSet = statement1.executeQuery();

			if (resultSet.next()) {
				gradeID = resultSet.getInt("GradeID");
			} else {
				connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
				return;
			}

			// 이 sql은 업데이트 하기 위한 것이다.
			String sql2 = "UPDATE DB2024_Grade SET Grade = ? WHERE GradeId = ?";
			try (PreparedStatement statement2 = connection.prepareStatement(sql2)) {
				statement2.setString(1, gradeField.getText());
				statement2.setLong(2, gradeID);

				int rowsUpdated = statement2.executeUpdate(); // 영향을 받은 갯수를 통해 수정 여부를 확인할 수 있다.

				if (rowsUpdated > 0) { // 0보다 크다면 수정이 완료된 것이다.
					JOptionPane.showMessageDialog(this, "성적이 수정되었습니다.");
					connection.commit(); // connection을 commit해서 완료해준다.
				} else {
					JOptionPane.showMessageDialog(this, "성적의 수정에 실패했습니다.");
					connection.rollback(); // 트랜잭션 롤백해서 트랜잭션을 취소해준다.
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				if (connection != null) {
					connection.rollback(); // 트랜잭션 롤백해서 트랜잭션을 취소해준다.
				}
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		} finally {
			try {
				if (connection != null) {
					connection.setAutoCommit(true);// AutoCommit을 true로 돌려준다.
					connection.close(); // 마지막에 finally를 통해 connection이 null이 아니라면 close해준다.
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}