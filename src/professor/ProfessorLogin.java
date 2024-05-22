package professor;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Start;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ProfessorLogin extends JFrame{
	private JPanel contentPane;
	private JTextField idField;
	private JTextField passField;
	public static StartProfessor student_start_frame = null;
	public static Start start_frame = null;
	 
	
	public ProfessorLogin() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(125, 118, 371, 277);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel passLabel = new JLabel("비밀번호");
		passLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		passLabel.setBounds(40, 137, 73, 29);
		panel.add(passLabel);
		
		JLabel idLabel = new JLabel("학번");
		idLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		idLabel.setBounds(40, 59, 73, 29);
		panel.add(idLabel);
		
		idField = new JTextField();
		idField.setBounds(180, 64, 116, 21);
		panel.add(idField);
		idField.setColumns(10);
		
		passField = new JTextField();
		passField.setColumns(10);
		passField.setBounds(180, 142, 116, 21);
		panel.add(passField);
		
		JButton loginBtn = new JButton("로그인");
	
	//교수 로그인 구현 start
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/DB2024Team04", "root", "root");
						 Statement stmt = conn.createStatement();
						 )
						 {
						 String id= idField.getText();
						 String pass= passField.getText();
						 //로그인 쿼리
						 String sql_query = String.format("select Password from DB2024_Professor where ProfessorID='%s' and Password='%s'",id,pass);

						 ResultSet rset = stmt.executeQuery(sql_query);
						 
						 if( rset.next()) {
							 //만약 받아온 레코드의 Password와 pw 텍스트 필드의 문자열이 같다면 교수start 페이지로 넘어감 만약 틀리면 넘어가지지 않음
						  if(pass.equals(rset.getString("Password"))){
						  */
						     student_start_frame = new StartProfessor();
						     student_start_frame.setVisible(true);
						      setVisible(false);	
						      /*
						 }					  
						 }
						 else JOptionPane.showMessageDialog(null, "로그인 실패!");


						 }
						 catch (SQLException sqle) {
						 System.out.println("SQLException : " + sqle);
						 }
				*/
				
				
			}
		}
		);
		
	//교수 로그인 구현 end
		
		loginBtn.setBounds(190, 206, 97, 23);
		panel.add(loginBtn);
		
		JLabel title = new JLabel("교수 로그인");
		title.setFont(new Font("Dialog", Font.PLAIN, 27));
		title.setBounds(228, 33, 158, 55);
		getContentPane().add(title);
		
		JButton homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new Start();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		homeButton.setBounds(22, 10, 85, 29);
		getContentPane().add(homeButton);
		
		
	}
	
	
}