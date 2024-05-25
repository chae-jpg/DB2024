package student;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CourseEvaluationModify extends JFrame {
	private JLabel courseName;
	private JLabel date;
	private JTextField score;
	private JTextArea review;
	private JScrollPane scrollPane;
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/DB2024team04";
	//Database credentials
	// MySQL 계정과 암호 입력
	static final String USER = "root";
	static final String PASS = "root";
	private JButton modifyBtn;
	
	
	
	public void showInfo(int id) {
		String nameText = "SELECT coursename FROM DB2024_COURSE c, DB2024_EVALUATION e "
				+ "WHERE c.courseid = e.courseid "
				+ "AND e.evaluationid = ?";
		String query = "SELECT * FROM DB2024_EVALUATION WHERE evaluationid = ?";
		try {
			Connection conn = DriverManager.getConnection(DB_URL,USER, PASS);
			Statement stmt1 = conn.createStatement();
			PreparedStatement pStmt = conn.prepareStatement(nameText);
			pStmt.setInt(1,  id);
			ResultSet rs1 = pStmt.executeQuery();

			while(rs1.next()) {
				nameText = rs1.getString(1);
				courseName.setText(nameText);
			}
			System.out.println("name fin");
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, id);
			rs1 = pStmt.executeQuery();
			while(rs1.next()) {
				System.out.println(rs1.getString(3));
				date.setText(rs1.getString(3));
				score.setText(rs1.getString(2));
				review.setText(rs1.getString(4));
			}
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	public void updateResult(int id) {
		String query = "UPDATE db2024_evaluation "
				+ "SET score = ?, comment = ? "
				+ "WHERE evaluationid = ?";
		try(
			Connection conn = DriverManager.getConnection(DB_URL,USER, PASS);
			PreparedStatement pStmt = conn.prepareStatement(query);
			
		){
			
			try {
				conn.setAutoCommit(false);
				pStmt.setString(1, score.getText());
				pStmt.setString(2, review.getText());
				pStmt.setLong(3, id);
				pStmt.executeUpdate();
				conn.commit();
				System.out.println(id);
			} catch(SQLException se) {
				se.printStackTrace();
				System.out.println("execute rollback");
				try {
					if(conn!=null) conn.rollback();
				}catch(SQLException se2) {
					se2.printStackTrace();
				}
			}
			conn.setAutoCommit(true);
		
		}catch(SQLException se) {
			se.printStackTrace();
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
		modifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		modifyBtn.setBounds(503, 356, 97, 23);
		getContentPane().add(modifyBtn);
	}
	
	public CourseEvaluationModify(int id) {
		this();
		showInfo(id);
		System.out.println(id);
		modifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateResult(id);
				setVisible(false);
			}
		});
	}
}
