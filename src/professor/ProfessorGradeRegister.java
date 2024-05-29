package professor;

import main.DatabaseConnection;
/*
 * 교수는 자신이 수업하는 강의에 한해서 등록 가능하다. 
 * 트랜잭션을 통해 원자성, 일관성, 고립성, 지속성을 유지하게 해준다. */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProfessorGradeRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JLabel textGrade;
	private JRadioButton rdbtnAplus;
	private JRadioButton rdbtnA0;
	private JRadioButton rdbtnAminus;
	private JRadioButton rdbtnBplus;
	private JRadioButton rdbtnB0;
	private JRadioButton rdbtnBminus;
	private JRadioButton rdbtnCplus;
	private JRadioButton rdbtnC0;
	private JRadioButton rdbtnCminus;
	private JRadioButton rdbtF;
	private JRadioButton rdbtnP;
	private JRadioButton rdbtnNP;
	private JLabel textSemester;
	private JLabel textCourse;
	private JLabel textStudent;
	private JTextField textFieldSemester;
	private JTextField textFieldCourse;
	private JTextField textFieldStudent;
	private JButton homeButton;
	private JButton btnRegister;

	public static ProfessorGradeManagement grade_management_frame = null;
	public static StartProfessor start_professor_frame = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorGradeRegister frame = new ProfessorGradeRegister();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorGradeRegister() {
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

		title = new JLabel("성적 등록 ");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 115, 55);
		panel.add(title);

		textGrade = new JLabel("성적");
		textGrade.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textGrade.setBounds(94, 104, 39, 55);
		panel.add(textGrade);

		ButtonGroup bg = new ButtonGroup(); // ButtonGroup을 통해 여러 개의 RadioButton 중 하나만 선택할 수 있게 해준다.

		// 아래는 A+~NP까지의 성적을 라디오 버튼으로 만든 것이다.
		rdbtnAplus = new JRadioButton("A+");
		rdbtnAplus.setBounds(145, 122, 61, 23);
		bg.add(rdbtnAplus);
		panel.add(rdbtnAplus);

		rdbtnA0 = new JRadioButton("A0");
		rdbtnA0.setBounds(197, 122, 61, 23);
		bg.add(rdbtnA0);
		panel.add(rdbtnA0);

		rdbtnAminus = new JRadioButton("A-");
		rdbtnAminus.setBounds(252, 122, 61, 23);
		bg.add(rdbtnAminus);
		panel.add(rdbtnAminus);

		rdbtnBplus = new JRadioButton("B+");
		rdbtnBplus.setBounds(303, 122, 61, 23);
		bg.add(rdbtnBplus);
		panel.add(rdbtnBplus);

		rdbtnB0 = new JRadioButton("B0");
		rdbtnB0.setBounds(351, 122, 61, 23);
		bg.add(rdbtnB0);
		panel.add(rdbtnB0);

		rdbtnBminus = new JRadioButton("B-");
		rdbtnBminus.setBounds(402, 122, 61, 23);
		bg.add(rdbtnBminus);
		panel.add(rdbtnBminus);

		rdbtnCplus = new JRadioButton("C+");
		rdbtnCplus.setBounds(452, 122, 61, 23);
		bg.add(rdbtnCplus);
		panel.add(rdbtnCplus);

		rdbtnC0 = new JRadioButton("C0");
		rdbtnC0.setBounds(502, 122, 61, 23);
		bg.add(rdbtnC0);
		panel.add(rdbtnC0);

		rdbtnCminus = new JRadioButton("C-");
		rdbtnCminus.setBounds(552, 122, 61, 23);
		bg.add(rdbtnCminus);
		panel.add(rdbtnCminus);

		rdbtF = new JRadioButton("F");
		rdbtF.setBounds(605, 122, 61, 23);
		bg.add(rdbtF);
		panel.add(rdbtF);

		rdbtnP = new JRadioButton("P");
		rdbtnP.setBounds(653, 122, 61, 23);
		bg.add(rdbtnP);
		panel.add(rdbtnP);

		rdbtnNP = new JRadioButton("NP");
		rdbtnNP.setBounds(704, 122, 61, 23);
		bg.add(rdbtnNP);
		panel.add(rdbtnNP);

		textSemester = new JLabel("학기");
		textSemester.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textSemester.setBounds(94, 191, 39, 55);
		panel.add(textSemester);

		textCourse = new JLabel("강의");
		textCourse.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textCourse.setBounds(94, 281, 39, 55);
		panel.add(textCourse);

		textStudent = new JLabel("학생");
		textStudent.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textStudent.setBounds(94, 380, 39, 55);
		panel.add(textStudent);

		textFieldSemester = new JTextField();
		textFieldSemester.setBounds(252, 200, 281, 43);
		panel.add(textFieldSemester);
		textFieldSemester.setColumns(10);

		textFieldCourse = new JTextField();
		textFieldCourse.setColumns(10);
		textFieldCourse.setBounds(252, 290, 281, 43);
		panel.add(textFieldCourse);

		textFieldStudent = new JTextField();
		textFieldStudent.setColumns(10);
		textFieldStudent.setBounds(252, 392, 281, 43);
		panel.add(textFieldStudent);

		btnRegister = new JButton("확인");

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

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedrb = null;
				// getActionCommand를 통해 이벤트를 발생시킨 객체의 문자열을 가져와준다. 즉, A+에서 이벤트가 발생했다면, A+이라는 문자열을
				// 가져올 것이다.
				if (rdbtnAplus.isSelected()) {
					selectedrb = rdbtnAplus.getActionCommand();
				} else if (rdbtnA0.isSelected()) {
					selectedrb = rdbtnA0.getActionCommand();
				} else if (rdbtnAminus.isSelected()) {
					selectedrb = rdbtnAminus.getActionCommand();
				} else if (rdbtnBplus.isSelected()) {
					selectedrb = rdbtnBplus.getActionCommand();
				} else if (rdbtnB0.isSelected()) {
					selectedrb = rdbtnB0.getActionCommand();
				} else if (rdbtnBminus.isSelected()) {
					selectedrb = rdbtnBminus.getActionCommand();
				} else if (rdbtnCplus.isSelected()) {
					selectedrb = rdbtnCplus.getActionCommand();
				} else if (rdbtnC0.isSelected()) {
					selectedrb = rdbtnC0.getActionCommand();
				} else if (rdbtnCminus.isSelected()) {
					selectedrb = rdbtnCminus.getActionCommand();
				} else if (rdbtF.isSelected()) {
					selectedrb = rdbtF.getActionCommand();
				} else if (rdbtnP.isSelected()) {
					selectedrb = rdbtnP.getActionCommand();
				} else if (rdbtnNP.isSelected()) {
					selectedrb = rdbtnNP.getActionCommand();
				}

				String searchedSemester = textFieldSemester.getText(); // 학기를 가져온다. (ex 2024-1, 2024-2, 2023-1..)
				String searchedCourse = textFieldCourse.getText(); // 강의를 가져온다. (강의명 Or 강의id)
				String searchedStudent = textFieldStudent.getText(); // 학생을 가져온다. (학생명 Or 학생id)

				registerGrade(selectedrb, searchedSemester, searchedCourse, searchedStudent); // selectedrb,
																								// searchedSemester,
																								// searchedCourse,
																								// searchedStudent를 인자로
																								// 하여 registerGrade메서드를
																								// 호출한다.
			}
		});
		btnRegister.setBounds(653, 457, 117, 29);
		panel.add(btnRegister);

	}

	private void registerGrade(String selectedrb, String semester, String searchedCourse, String searchedStudent) {

		String id = Professor.getInstance().getId();
		String sql = "";
		int CourseID = 0;
		Connection connection = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DatabaseConnection.getConnection(); // 변경된 부분
			if (connection == null) {
				throw new SQLException("Database connection failed");
			}
			connection.setAutoCommit(false); // 트랜잭션을 위해 AutoCommit false로 설정했따.

			int CourseID_temp = Integer.parseInt(searchedCourse); // integer형으로 바뀐다면, 즉 강의명이 아닌 강의id라면

			try {
				sql = "SELECT CourseID FROM DB2024_Course WHERE ProfessorID= ?"; // sql문 만들어서 courseid를 찾아온다.

				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, id); // ?의 첫 번째 값으로 id로 채워준다.

				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					if (CourseID_temp == resultSet.getInt("CourseID")) {
						CourseID = resultSet.getInt("CourseID");
					} else {
						JOptionPane.showMessageDialog(this, "선택한 강의는 본인의 강의가 아닙니다.");
						connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
						return;
					}
				} else {
					JOptionPane.showMessageDialog(this, "선택한 강의는 본인의 강의가 아닙니다.");
					connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
					return;
				}
				resultSet.close();
				statement.close();

			} catch (SQLException e) {
				connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
				System.out.println(e.getMessage());
				return;
			}
		} catch (NumberFormatException e) { // Integer형으로 바뀌지 않는다는건 강의명을 입력으로 받아왔다는 뜻이다.
			try {
				sql = "SELECT CourseID FROM DB2024_Course WHERE CourseName=? AND ProfessorID= ?"; // sql문 만들어서 courseid
																									// 찾아오기

				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, searchedCourse); // ?의 첫 번재 값으로 searchedCours를 채워준다.
				statement.setString(2, id); // ?의 두 번째 값으로 id를 채워준다.

				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					CourseID = resultSet.getInt("CourseID");
					System.out.println("CourseID: " + CourseID);
				} else {
					System.out.println("결과가 없습니다.");
					connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
					return;
				}
				resultSet.close();
				statement.close();

			} catch (SQLException e1) {
				try {
					connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
				} catch (SQLException e2) {
					System.out.println(e2.getMessage());
				}
				System.out.println(e1.getMessage());
				return;
			}
		} catch (ClassNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		int StudentID = 0;

		try {
			StudentID = Integer.parseInt(searchedStudent); // integer형으로 바뀐다면, 즉 학생 이름이 아닌 학생id라면
		} catch (NumberFormatException e) { // 학생이름으로 입력 받아왔다면
			try {
				sql = "SELECT StudentID FROM DB2024_Student WHERE Name=?"; // sql문 만들어서 학생id 찾아오기

				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, searchedStudent); // ?의 첫 번째 값을 searchedStudent로 채워준다.
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					StudentID = resultSet.getInt("StudentID");
				} else {
					System.out.println("결과가 없습니다.");
					connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
					return;
				}
				resultSet.close();
				statement.close();

			} catch (SQLException e1) {
				try {
					connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
				} catch (SQLException e2) {
					System.out.println(e2.getMessage());
				}
				System.out.println(e1.getMessage());
				return;
			}
		}

		// 재수강 여부 확인 및 성적 등록
		try {
			sql = "INSERT INTO DB2024_Grade(StudentId, CourseId, Grade, Semester) VALUES (?, ?, ?, ?)";

			// 재수강여부 확인 (등록을 실행하기 전 먼저 studentid, courseid로 이미 데이터가 존재하는지 확인 후 넣기)
			String sql_re = "SELECT count(*) FROM DB2024_Grade WHERE StudentId=? AND CourseId=?";

			PreparedStatement statement_re = connection.prepareStatement(sql_re);
			statement_re.setInt(1, StudentID);
			statement_re.setInt(2, CourseID);

			ResultSet resultSet_re = statement_re.executeQuery();

			if (resultSet_re.next()) {
				int count = resultSet_re.getInt(1);
				if (count > 0) {
					// 재수강이면 이전 GRADE를 P로 바꾼다.
					String sql_grade_update = "UPDATE DB2024_Grade SET Grade = 'P' WHERE StudentId = ? AND CourseId = ?";
					PreparedStatement updateStatement1 = connection.prepareStatement(sql_grade_update);
					updateStatement1.setInt(1, StudentID); // ?의 첫 번재 값을 StudentID로 채운다.
					updateStatement1.setInt(2, CourseID); // ?의 두 번째 값을 CourseID로 채운다.
					updateStatement1.executeUpdate();// 실행시킨다.
					updateStatement1.close();

					sql = "INSERT INTO DB2024_Grade(StudentId, CourseId, Grade, Semester, Repetition) VALUES (?, ?, ?, ?, TRUE)";
				}
			}
			resultSet_re.close();
			statement_re.close();

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, StudentID);// ?의 첫 번째 값을 StudentID로 채워준다.
			statement.setInt(2, CourseID);// ?의 두 번째 값을 CourseID로 채워준다.
			statement.setString(3, selectedrb);// ?의 세 번째 값을 selectedrb로 채워준다.
			statement.setString(4, semester);// ?의 네 번째 값을 semester로 채워준다.
			statement.executeUpdate(); // 실행시켜준다.
			statement.close();

			connection.commit();// 모든 작업을 성공적으로 수행했다면(롤백이 되지 않았다면) 트랜잭션 커밋해서 저장해준다.

			grade_management_frame = new ProfessorGradeManagement();
			grade_management_frame.setVisible(true);
			setVisible(false);

		} catch (SQLException e) {
			try {
				connection.rollback(); // 롤백해서 트랜잭션 취소해준다.
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
		} finally { // finally에서 connection이 Null이 아니라면 close해준다.
			try {
				if (connection != null) {
					connection.setAutoCommit(true);// AutoCommit을 true로 돌려준다.
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
