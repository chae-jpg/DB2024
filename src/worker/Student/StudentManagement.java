package worker.Student;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
//StudentManagement 클래스는 JFrame을 상속하여 학생 정보를 관리하는 창을 만듦.
public class StudentManagement extends JFrame {
	//사용자 인터페이스 요소들
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnRegister;
	private JButton btnModify;
	private JButton btnDelete;
	public static WorkerStart start_frame = null;
	private StudentDAO studentDAO;
//main 메서드에서는 프로그램이 시작될 때 StudentManagement 클래스의 인스턴스를 생성하고 보여줌.
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

	public StudentManagement() {
		studentDAO = new StudentDAO(); // 데이터베이스 연결을 위한 객체 생성.
// UI 요소들을 초기화하고 배치.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(12, 6, 771, 492);
		contentPane.add(panel);
		panel.setLayout(null);

		title = new JLabel("학생 관리");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 116, 55);
		panel.add(title);

		searchTextField = new JTextField("학생명 or 학번 or 학과를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchStudents(selectedItem, searchText);
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_frame = new WorkerStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 81, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "학생명", "학번", "학과" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("학생명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] { "StudentID", "Name", "Department", "Email", "Contact", "Password" }
		);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 150, 700, 300);
		panel.add(scrollPane);

		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openStudentForm(null);
			}
		});
		btnRegister.setBounds(537, 457, 75, 29);
		panel.add(btnRegister);

		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int studentID = (int) tableModel.getValueAt(selectedRow, 0);
					Student student = studentDAO.getStudentsById(studentID).get(0);
					openStudentForm(student);
				} else {
					JOptionPane.showMessageDialog(panel, "수정할 학생을 선택하세요.", "수정 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModify.setBounds(605, 457, 75, 29);
		panel.add(btnModify);

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int studentID = (int) tableModel.getValueAt(selectedRow, 0);
					studentDAO.deleteStudent(studentID);
					displayStudents(studentDAO.getStudentsByName("")); // 모든 학생을 다시 로드
				} else {
					JOptionPane.showMessageDialog(panel, "삭제할 학생을 선택하세요.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.setBounds(675, 457, 75, 29);
		panel.add(btnDelete);
		// 초기 데이터 로드
		displayStudents(studentDAO.getStudentsByName("")); // 모든 학생을 로드
	}
// 용자가 입력한 기준과 값을 받아서 해당하는 학생들을 검색하고 테이블에 표시
	private void searchStudents(String criteria, String value) {
		List<Student> students;
		if (criteria.equals("학생명")) {
			students = studentDAO.getStudentsByName(value);
		} else if (criteria.equals("학번")) {
			int studentID = Integer.parseInt(value);
			students = studentDAO.getStudentsById(studentID);
		} else {
			students = studentDAO.getStudentsByDepartment(value);
		}
		displayStudents(students);
	}
//주어진 학생 목록을 테이블에 표시
	private void displayStudents(List<Student> students) {
		tableModel.setRowCount(0); // Clear existing rows
		for (Student student : students) {
			tableModel.addRow(new Object[] {
					student.getStudentID(), student.getName(), student.getDepartment(), student.getEmail(),
					student.getContact(), student.getPassword()
			});
		}
	}
//학생 등록/수정 폼을 열기.
	private void openStudentForm(Student student) {
		boolean isEditMode = (student != null);
		StudentForm studentForm = new StudentForm(this, student, isEditMode);
		studentForm.setVisible(true);

		Student result = studentForm.getStudent();
		if (result != null) {
			if (isEditMode) {
				studentDAO.updateStudent(result);
			} else {
				studentDAO.addStudent(result);
			}
			displayStudents(studentDAO.getStudentsByName("")); // 모든 학생을 다시 로드
		}
	}
}
