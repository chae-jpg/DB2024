package worker;

import main.DatabaseConnection;
import main.Start;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WorkerLogin extends JFrame {
	private JPanel contentPane;
	private JTextField idField;
	private JTextField passField;
	public static WorkerStart worker_start_frame = null;
	public static Start start_frame = null;

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

		passField = new JTextField();
		passField.setColumns(10);
		passField.setBounds(180, 142, 116, 21);
		panel.add(passField);

		JButton loginBtn = new JButton("로그인");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String workerId = idField.getText();
				String password = passField.getText();
				if (authenticateWorker(workerId, password)) {
					worker_start_frame = new WorkerStart();
					worker_start_frame.setVisible(true);
					setVisible(false);
				} else {
					// 로그인 실패 시 메시지 출력 (실제로는 사용자에게 친절하게 알려주기)
					JOptionPane.showMessageDialog(null, "로그인 실패: 잘못된 사번 또는 비밀번호입니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
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

	private boolean authenticateWorker(String workerId, String password) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			String query = "SELECT * FROM DB2024_Staff WHERE StaffID = ? AND Password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, workerId);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
