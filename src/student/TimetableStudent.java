package student;
import main.DatabaseConnection;
import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class TimetableStudent extends JFrame{
	//JDBC driver name and database URL
		public static StudentStart start_frame = null;
	private Panel mon12;
	private Panel fri56;
	private Panel fri45;
	private Panel fri34;
	private Panel fri23;
	private Panel fri12;
	private Panel thu56;
	private Panel thu45;
	private Panel thu34;
	private Panel thu23;
	private Panel thu12;
	private Panel wed56;
	private Panel wed45;
	private Panel wed34;
	private Panel wed23;
	private Panel wed12;
	private Panel tue56;
	private Panel tue45;
	private Panel tue34;
	private Panel tue23;
	private Panel tue12;
	private Panel mon56;
	private Panel mon45;
	private Panel mon34;
	private Panel mon23;
	private JLabel title;
	
	
	public TimetableStudent() {
		setBounds(100, 100, 905, 609);
		getContentPane().setLayout(null);
		
		JButton homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new StudentStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		homeButton.setBounds(25, 32, 85, 29);
		getContentPane().add(homeButton);
		
		title = new JLabel("홍길동님의 시간표");
		title.setFont(new Font("Dialog", Font.PLAIN, 27));
		title.setBounds(338, 53, 244, 55);
		getContentPane().add(title);
		
		mon12 = new Panel();
		mon12.setBounds(119, 178, 126, 101);
		getContentPane().add(mon12);
		mon12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		JLabel lblNewLabel = new JLabel("월");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel.setBounds(179, 103, 32, 44);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("화");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(304, 103, 32, 44);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("수");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(445, 103, 32, 44);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("목");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(566, 103, 32, 44);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("금");
		lblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(695, 103, 32, 44);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("1교시");
		lblNewLabel_5.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(25, 178, 75, 44);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("2교시");
		lblNewLabel_5_1.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_5_1.setBounds(25, 232, 75, 44);
		getContentPane().add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("3교시");
		lblNewLabel_5_2.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_5_2.setBounds(25, 293, 75, 44);
		getContentPane().add(lblNewLabel_5_2);
		
		JLabel lblNewLabel_5_3 = new JLabel("4교시");
		lblNewLabel_5_3.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_5_3.setBounds(25, 352, 75, 44);
		getContentPane().add(lblNewLabel_5_3);
		
		JLabel lblNewLabel_5_4 = new JLabel("5교시");
		lblNewLabel_5_4.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_5_4.setBounds(25, 406, 75, 44);
		getContentPane().add(lblNewLabel_5_4);
		
		JLabel lblNewLabel_5_5 = new JLabel("6교시");
		lblNewLabel_5_5.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_5_5.setBounds(25, 460, 75, 44);
		getContentPane().add(lblNewLabel_5_5);
		
		mon23 = new Panel();
		mon23.setBounds(119, 236, 126, 101);
		getContentPane().add(mon23);
		mon23.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		mon34 = new Panel();
		mon34.setBounds(119, 295, 126, 101);
		getContentPane().add(mon34);
		mon34.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		mon45 = new Panel();
		mon45.setBounds(119, 349, 126, 101);
		getContentPane().add(mon45);
		mon45.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		mon56 = new Panel();
		mon56.setBounds(119, 403, 126, 101);
		getContentPane().add(mon56);
		mon56.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tue12 = new Panel();
		tue12.setBounds(251, 178, 126, 101);
		getContentPane().add(tue12);
		tue12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tue23 = new Panel();
		tue23.setBounds(251, 236, 126, 101);
		getContentPane().add(tue23);
		tue23.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tue34 = new Panel();
		tue34.setBounds(251, 295, 126, 101);
		getContentPane().add(tue34);
		tue34.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tue45 = new Panel();
		tue45.setBounds(251, 349, 126, 101);
		getContentPane().add(tue45);
		tue45.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tue56 = new Panel();
		tue56.setBounds(251, 403, 126, 101);
		getContentPane().add(tue56);
		tue56.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		wed12 = new Panel();
		wed12.setBounds(383, 178, 126, 101);
		getContentPane().add(wed12);
		wed12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		wed23 = new Panel();
		wed23.setBounds(383, 236, 126, 101);
		getContentPane().add(wed23);
		wed23.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		wed34 = new Panel();
		wed34.setBounds(383, 295, 126, 101);
		getContentPane().add(wed34);
		wed34.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		wed45 = new Panel();
		wed45.setBounds(383, 349, 126, 101);
		getContentPane().add(wed45);
		wed45.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		wed56 = new Panel();
		wed56.setBounds(383, 403, 126, 101);
		getContentPane().add(wed56);
		wed56.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		thu12 = new Panel();
		thu12.setBounds(515, 178, 126, 101);
		getContentPane().add(thu12);
		thu12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		thu23 = new Panel();
		thu23.setBounds(515, 236, 126, 101);
		getContentPane().add(thu23);
		thu23.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		thu34 = new Panel();
		thu34.setBounds(515, 295, 126, 101);
		getContentPane().add(thu34);
		thu34.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		thu45 = new Panel();
		thu45.setBounds(515, 349, 126, 101);
		getContentPane().add(thu45);
		thu45.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		thu56 = new Panel();
		thu56.setBounds(515, 403, 126, 101);
		getContentPane().add(thu56);
		thu56.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		fri12 = new Panel();
		fri12.setBounds(647, 178, 126, 101);
		getContentPane().add(fri12);
		fri12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		fri23 = new Panel();
		fri23.setBounds(647, 236, 126, 101);
		getContentPane().add(fri23);
		fri23.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		fri34 = new Panel();
		fri34.setBounds(647, 295, 126, 101);
		getContentPane().add(fri34);
		fri34.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		fri45 = new Panel();
		fri45.setBounds(647, 349, 126, 101);
		getContentPane().add(fri45);
		fri45.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		fri56 = new Panel();
		fri56.setBounds(647, 403, 126, 101);
		getContentPane().add(fri56);
		fri56.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Panel[][] pArr = {
				{mon12, mon23, mon34, mon45, mon56},
				{tue12, tue23, tue34, tue45, tue56},
				{wed12, wed23, wed34, wed45, wed56},
				{thu12, thu23, thu34, thu45, thu56},
				{fri12, fri23, fri34, fri45, fri56}
		};
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				pArr[i][j].setVisible(false);
			}
		}
		showTable(pArr);
		
		
	}


	private void showTable(Panel[][] pArr) {
		// TODO Auto-generated method stub
		String sql = "SELECT courseid from DB2024_REGISTER WHERE studentid = ?";
		String sqlname = "SELECT name from DB2024_STUDENT WHERE studentid = ?";
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(sql);
			PreparedStatement pStmtname = conn.prepareStatement(sqlname);
			pStmt.setInt(1, Integer.parseInt(Student.getInstance().getStudentId()));
			pStmtname.setInt(1, Integer.parseInt(Student.getInstance().getStudentId()));
			ResultSet rs = pStmt.executeQuery();
			ResultSet rsname = pStmtname.executeQuery();
			while (rsname.next()) {
				String stName = rsname.getString(1);
				title.setText(stName + "님의 시간표");
			}
			
			while (rs.next()) {
				int cid = rs.getInt(1);
				String sql2 = "Select * from db2024_course where courseid = ?";
				PreparedStatement pStmt2 = conn.prepareStatement(sql2);
				pStmt2.setInt(1, cid);
				ResultSet crs = pStmt2.executeQuery();
				colorTable(pArr, crs);
			}
			
		}catch (SQLException se) {
			se.printStackTrace();
		}
		
		
	}


	private void colorTable(Panel[][] pArr, ResultSet crs) {
		// TODO Auto-generated method stub
		int day = 0, time = 0;
		try {
			while (crs.next()) {
				System.out.println(crs.getString(6));
				System.out.println(crs.getString(7));
				switch(crs.getString(6)) {
				case "월요일":
					day = 0;
					break;
				case "화요일":
					day = 1;
					break;
				case "수요일":
					day = 2;
					break;
				case "목요일" :
					day=3;
					break;
				case "금요일" :
					day=4;
					break;
				}
				switch(crs.getString(7)) {
				case "1-2":
					time = 0;
					break;
				case "2-3":
					time=1;
					break;
				case "3-4":
					time=2;
					break;
				case "4-5":
					time=3;
					break;
				case "5-6":
					time=4;
					break;
				}
				Panel sel = pArr[day][time];
				sel.setVisible(true);
				sel.setBackground(Color.GREEN);			
				String cname = crs.getString(2);
				String room = crs.getString(3);
				String t = crs.getString(7);
				String pname = "";
				int pid = crs.getInt(8);
				String psql = "Select name from db2024_professor where professorid = ?";
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ppStmt = conn.prepareStatement(psql);
				ppStmt.setInt(1, pid);
				ResultSet pset = ppStmt.executeQuery();
				while (pset.next()) {
					pname = pset.getString(1);
				}
				JLabel cnameLabel = new JLabel(cname);
				JLabel roomLabel = new JLabel(room);
				JLabel tLabel = new JLabel(t);
				JLabel pnameLabel = new JLabel(pname);
				cnameLabel.setFont(new Font("굴림", Font.PLAIN, 15));
				roomLabel.setFont(new Font("굴림", Font.PLAIN, 17));
				tLabel.setFont(new Font("굴림", Font.PLAIN, 17));
				pnameLabel.setFont(new Font("굴림", Font.PLAIN, 17));
				sel.add(cnameLabel);
				sel.add(roomLabel);
				sel.add(tLabel);
				sel.add(pnameLabel);
			}
		} catch(SQLException se) {
			se.printStackTrace();
		}
		
		
	}
}
