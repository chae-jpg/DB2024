
package worker;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;

import main.Start;
import professor.Professor;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class WorkerLogin extends JFrame{
	private JPanel contentPane;
	private JTextField idField;
	private JPasswordField passField;
	public static WorkerStart worker_start_frame = null;
	public static Start start_frame = null;
	
	public static final String workerID = "";

	
	private static final String url = "jdbc:mysql://localhost:3306/DB2024Team04";
	private static final String username = "";
	private static final String password = "";
	
	public WorkerLogin() {

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
		
		JLabel idLabel = new JLabel("사번");
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
				
				// id랑 password 가져와준다.
				String id = idField.getText();
				String pw = new String(passField.getPassword());
				
				if (Login(id, pw)) {
					Professor.getInstance().setId(id);
					worker_start_frame = new WorkerStart();
					worker_start_frame.setVisible(true);
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(contentPane, "회원 정보가 존재하지 않습니다.", "로그인 실패",
							JOptionPane.ERROR_MESSAGE); // 오류창을 띄워준다.
				}
			}
		});
		loginBtn.setBounds(190, 206, 97, 23);
		panel.add(loginBtn);
		
		JLabel title = new JLabel("직원 로그인");
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
			
			String sql = "SELECT count(*) FROM DB2024_Staff WHERE StaffID = ? AND Password = ?"; // id랑 password 맞는 행의 갯수를 구하는 쿼리를 구현해주었다.
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, pw);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int cnt = resultSet.getInt("count(*)"); // cnt가 0보다 크면 로그인 성공이다. (db에 정보가 있기 때문)
				return cnt > 0;
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException | SQLException ec1) {
			ec1.printStackTrace();
		}
		return false;
	}
}

