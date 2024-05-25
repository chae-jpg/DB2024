package student;

import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseEvaluationAdd extends JFrame {
	private JTextField score;
	private JTextArea review;
	private JScrollPane scrollPane;
	private JButton modifyBtn;
	private JTextField courseID;
	private JLabel dateLabel;
	Date today = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public void AddResult() {
		String query = "INSERT INTO db2024_evaluation(score, comment, date, courseid, studentID) values (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = DatabaseConnection.getConnection();
			pStmt = conn.prepareStatement(query);

			conn.setAutoCommit(false);
			pStmt.setInt(1, Integer.parseInt(score.getText()));
			pStmt.setString(2, review.getText());
			pStmt.setString(3, dateFormat.format(today));
			pStmt.setInt(4, Integer.parseInt(courseID.getText()));
			pStmt.setInt(5, Integer.parseInt(Student.getInstance().getStudentId()));
			pStmt.executeUpdate();
			conn.commit();
			System.out.println("등록 완료");
			setVisible(false);
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("execute rollback");
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		} finally {
			try {
				if (pStmt != null) {
					pStmt.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public CourseEvaluationAdd() {
		setBounds(100, 100, 641, 439);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("점수");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel.setBounds(61, 204, 46, 32);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("강의평가 등록");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 26));
		lblNewLabel_1.setBounds(208, 34, 165, 43);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("강의평");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(61, 246, 68, 32);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("학수번호");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(61, 118, 97, 32);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_3_2 = new JLabel("날짜(xxxx-xx-xx)");
		lblNewLabel_3_2.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_3_2.setBounds(61, 162, 181, 32);
		getContentPane().add(lblNewLabel_3_2);

		score = new JTextField();
		score.setFont(new Font("굴림", Font.PLAIN, 20));
		score.setBounds(275, 204, 104, 29);
		getContentPane().add(score);
		score.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(275, 246, 291, 89);
		getContentPane().add(scrollPane);

		review = new JTextArea();
		review.setLineWrap(true);
		scrollPane.setViewportView(review);

		modifyBtn = new JButton("등록");
		modifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddResult();
			}
		});
		modifyBtn.setBounds(503, 356, 97, 23);
		getContentPane().add(modifyBtn);

		courseID = new JTextField();
		courseID.setFont(new Font("굴림", Font.PLAIN, 20));
		courseID.setColumns(10);
		courseID.setBounds(275, 118, 104, 29);
		getContentPane().add(courseID);

		dateLabel = new JLabel("null");
		dateLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		dateLabel.setBounds(275, 162, 181, 32);
		getContentPane().add(dateLabel);
		dateLabel.setText(dateFormat.format(today));
	}
}
