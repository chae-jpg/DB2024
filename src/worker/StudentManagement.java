package worker;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtStudentID;
	private JTextField txtName;
	private JTextField txtDepartment;
	private JTextField txtEmail;
	private JTextField txtContact;
	private JTextField txtPassword;
	private DefaultTableModel tableModel;
	private StudentDAO studentDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentManagement frame = new StudentManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentManagement() {
		studentDAO = new StudentDAO();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel title = new JLabel("학생 관리");
		title.setFont(new Font("굴림", Font.PLAIN, 27));
		title.setBounds(320, 30, 200, 50);
		contentPane.add(title);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 100, 700, 200);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] { "StudentID", "Name", "Department", "Email", "Contact", "Password" }
		);
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);

		txtStudentID = new JTextField();
		txtStudentID.setBounds(50, 320, 100, 26);
		contentPane.add(txtStudentID);
		txtStudentID.setColumns(10);

		txtName = new JTextField();
		txtName.setBounds(160, 320, 100, 26);
		contentPane.add(txtName);
		txtName.setColumns(10);

		txtDepartment = new JTextField();
		txtDepartment.setBounds(270, 320, 100, 26);
		contentPane.add(txtDepartment);
		txtDepartment.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(380, 320, 150, 26);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtContact = new JTextField();
		txtContact.setBounds(540, 320, 100, 26);
		contentPane.add(txtContact);
		txtContact.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setBounds(650, 320, 100, 26);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);

		JButton btnAdd = new JButton("추가");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStudent();
			}
		});
		btnAdd.setBounds(50, 370, 117, 29);
		contentPane.add(btnAdd);

		JButton btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStudent();
			}
		});
		btnUpdate.setBounds(200, 370, 117, 29);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteStudent();
			}
		});
		btnDelete.setBounds(350, 370, 117, 29);
		contentPane.add(btnDelete);

		JButton btnView = new JButton("조회");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewStudents();
			}
		});
		btnView.setBounds(500, 370, 117, 29);
		contentPane.add(btnView);

		JButton btnBack = new JButton("뒤로가기");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkerStart workerStartFrame = new WorkerStart();
				workerStartFrame.setVisible(true);
				setVisible(false);
			}
		});
		btnBack.setBounds(650, 370, 117, 29);
		contentPane.add(btnBack);

		// 초기 데이터 로드
		viewStudents();
	}

	private void addStudent() {
		int studentID = Integer.parseInt(txtStudentID.getText());
		String name = txtName.getText();
		String department = txtDepartment.getText();
		String email = txtEmail.getText();
		String contact = txtContact.getText();
		String password = txtPassword.getText();
		studentDAO.addStudent(studentID, name, department, email, contact, password);
		viewStudents();
	}

	private void updateStudent() {
		int studentID = Integer.parseInt(txtStudentID.getText());
		String name = txtName.getText();
		String department = txtDepartment.getText();
		String email = txtEmail.getText();
		String contact = txtContact.getText();
		String password = txtPassword.getText();
		studentDAO.updateStudent(studentID, name, department, email, contact, password);
		viewStudents();
	}

	private void deleteStudent() {
		int studentID = Integer.parseInt(txtStudentID.getText());
		studentDAO.deleteStudent(studentID);
		viewStudents();
	}

	private void viewStudents() {
		List<Student> students = studentDAO.getAllStudents();
		tableModel.setRowCount(0); // Clear existing rows
		for (Student student : students) {
			tableModel.addRow(new Object[] {
					student.getStudentID(), student.getName(), student.getDepartment(), student.getEmail(), student.getContact(), student.getPassword()
			});
		}
	}
}
