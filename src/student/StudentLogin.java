package student;
import main.DatabaseConnection;
import main.Start;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class StudentLogin extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JPasswordField passField;
	public static StudentStart student_start_frame = null;
	public static Start start_frame = null;
	
	private JLabel rejectLabel;
	public static final String studentID="";
	


	
	public StudentLogin() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(221, 127, 371, 277);
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
		
		passField = new JPasswordField();
		passField.setColumns(10);
		passField.setBounds(180, 142, 116, 21);
		panel.add(passField);
		
	
		
		JButton loginBtn = new JButton("로그인");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //id랑 password 가져오기 
                String studentId = idField.getText();
                String pw = new String(passField.getPassword());
                System.out.println("student id: " + studentId);
                System.out.println("pass: " + pw);
                
                if (Login(studentId, pw)) {
                	Student.getInstance().setStudentId(studentId); // 로그인 성공 시 ID 저장한다. 
	                student_start_frame = new StudentStart();
					student_start_frame.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "회원 정보가 존재하지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE); //로그인 실패 시 오류 창 띄워준다. 
                }
            } 
        });
		
		loginBtn.setBounds(190, 206, 97, 23);
		panel.add(loginBtn);
		
		JLabel title = new JLabel("학생 로그인");
		title.setFont(new Font("Dialog", Font.PLAIN, 27));
		title.setBounds(324, 42, 158, 55);
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
	private boolean Login(String id, String pw) {

		try {
			String sql = "SELECT count(*) FROM DB2024_Student WHERE StudentID = ? AND Password = ?";// sql문 만들어서 존재여부 확인한다. 
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, pw);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int cnt = resultSet.getInt("count(*)"); //cnt가 0보다 크면 로그인 성공이다. (db에 정보가 있기 때문 )
				
				if (cnt>0) {
					return true;
				}
				else {					
					return false;
				}
				
			} 
		} catch (ClassNotFoundException | SQLException ec1) {
			ec1.printStackTrace();
		}
		return rootPaneCheckingEnabled;
		
	}


}

