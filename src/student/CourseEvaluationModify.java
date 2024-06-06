package student;

import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseEvaluationModify extends JFrame {
	private JLabel courseName;
	private JLabel date;
	private JTextField score;
	private JTextArea review;
	private JScrollPane scrollPane;
	private JButton modifyBtn;

	//화면에 수정하고자 하는 데이터를 보여주는 메소드
	public void showInfo(int id) {
		//조인쿼리를 사용해 evaluationid에 해당하는 강의명을 찾는 쿼리
		String nameText = "SELECT coursename FROM DB2024_COURSE c, DB2024_EVALUATION e WHERE c.courseid = e.courseid AND e.evaluationid = ?";
		String query = "SELECT * FROM DB2024_EVALUATION WHERE evaluationid = ?";
		try (Connection conn = DatabaseConnection.getConnection()) {
			try (PreparedStatement pStmt = conn.prepareStatement(nameText)) {
				pStmt.setInt(1, id);
				try (ResultSet rs1 = pStmt.executeQuery()) {
					if (rs1.next()) {
						courseName.setText(rs1.getString(1));
					}
				}
			}
			try (PreparedStatement pStmt = conn.prepareStatement(query)) {
				pStmt.setInt(1, id);
				try (ResultSet rs1 = pStmt.executeQuery()) {
					if (rs1.next()) {
						date.setText(rs1.getString("date"));
						score.setText(rs1.getString("score"));
						review.setText(rs1.getString("comment"));
					}
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	//사용자가 입력한 정보에 따라 데이터를 수정하는 메소드
	public void updateResult(int id) {
		String query1 = "UPDATE db2024_evaluation SET score = ? WHERE evaluationid = ?";
		String query2 = "UPDATE db2024_evaluation SET comment = ? WHERE evaluationid = ?";
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement pStmt = conn.prepareStatement(query1)) {
			//트랜젝션을 위해 오토커밋 해제
			conn.setAutoCommit(false);
			pStmt.setInt(1, Integer.parseInt(score.getText()));
			pStmt.setInt(2, id);
			pStmt.executeUpdate();
			PreparedStatement pStmt2 = conn.prepareStatement(query2);
			pStmt2.setString(1, review.getText());
			pStmt2.setInt(2, id);
			pStmt2.executeUpdate();
			conn.commit();
			System.out.println("수정 완료");
			//오토커밋 설정
			conn.setAutoCommit(true);
		} catch (SQLException se) {
			se.printStackTrace();
			try (Connection conn = DatabaseConnection.getConnection()) {
				if (conn != null) {
					//연결에 문제가 있을 시 -> 롤백
					conn.rollback();
				}
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
	}

	public CourseEvaluationModify() {
		setBounds(100, 100, 641, 439);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("점수");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel.setBounds(61, 204, 46, 32);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("강의평가 수정");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 26));
		lblNewLabel_1.setBounds(208, 34, 165, 43);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("강의평");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(61, 246, 68, 32);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("강의명");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(61, 118, 68, 32);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_3_2 = new JLabel("날짜(xxxx-xx-xx)");
		lblNewLabel_3_2.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_3_2.setBounds(61, 162, 181, 32);
		getContentPane().add(lblNewLabel_3_2);

		courseName = new JLabel("New label");
		courseName.setFont(new Font("굴림", Font.PLAIN, 20));
		courseName.setBounds(275, 118, 218, 32);
		getContentPane().add(courseName);

		date = new JLabel("New label");
		date.setFont(new Font("굴림", Font.PLAIN, 20));
		date.setBounds(275, 162, 218, 32);
		getContentPane().add(date);

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

		modifyBtn = new JButton("수정");
		modifyBtn.setBounds(503, 356, 97, 23);
		getContentPane().add(modifyBtn);
	}

	public CourseEvaluationModify(int id) {
		this();
		showInfo(id);
		modifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateResult(id);
				setVisible(false);
			}
		});
	}
}
